module MetodosDeContratos
  attr_accessor :lista_before, :lista_invariantes, :lista_after, :lista_precondiciones, :lista_postcondiciones,
                :new_method, :ejecutando_contrato

  def lista_before
    @lista_before ||= []
    @lista_before
  end

  def lista_after
    @lista_after ||= []
    @lista_after
  end

  def lista_invariantes
    @lista_invariantes ||= []
    @lista_invariantes
  end

  def lista_precondiciones
    @lista_precondiciones ||= []
    @lista_precondiciones
  end

  def lista_postcondiciones
    @lista_postcondiciones ||= []
    @lista_postcondiciones
  end

  def before_and_after_each_call(procBefore, procAfter)
      lista_before << procBefore
      lista_after << procAfter
  end

  def pop_ultimas_precondiciones
    precondiciones = lista_precondiciones.pop(lista_precondiciones.size)
    precondiciones
  end

  def pop_ultimas_postcondiciones
    postcondiciones = lista_postcondiciones.pop(lista_postcondiciones.size)
    postcondiciones
  end

  class Contexto
    attr_accessor :instancia

    def initialize(instancia)
      self.instancia = instancia
    end
    def set_argumentos_metodo(parametros)
      parametros.each do |nombre, valor|
        if(!nombre.nil?)
          define_singleton_method(nombre) do
            valor
          end
        end
      end
    end
    def set_variables_instancia(parametros)
      parametros.each do |nombre,valor|
        if(!nombre.nil?)
          self.instance_variable_set(nombre,valor)
        end
      end
    end

    def method_missing(method,*args)
      instancia.send(method,*args)
    end
  end

  def method_added(nombre_metodo)

    #Usamos un booleano de la clase para impedir que al hacerse define_method entre en bucle infinito
    if @new_method.nil? || @new_method

      @new_method = false

      #Nos guardamos una referencia al metodo original para ejecutar su comportamiento en la nueva definicion
      metodo_original = instance_method(nombre_metodo)

      #Obtenemos las ultimas pre/post - condiciones y las quitamos de las listas para que solo apliquen a este metodo
      precondiciones_metodo = pop_ultimas_precondiciones
      postcondiciones_metodo = pop_ultimas_postcondiciones

      #Redefinimos el metodo
      define_method(nombre_metodo) do |*args,&block|

        #Usamos un booleano de la instancia para evitar que se ejecuten otros contratos si el metodo es llamado desde un
        #contrato (bucle infinito)
        @ejecutando_contrato||=false

        #Armamos una lista con el nombre de cada parametro del metodo y su valor.
        nombres_parametros = metodo_original.parameters.map {|_,nombre_parametro| nombre_parametro }
        parametros_metodo = nombres_parametros.zip(args)

        #Armamos una lista con el nombre de cada variable de instancia y su valor actual
        nombres_variables_instancia = self.instance_variables
        valores_variables_instancia = nombres_variables_instancia.map {|nombre_var| self.instance_variable_get(nombre_var)}
        variables_instancia = nombres_variables_instancia.zip(valores_variables_instancia)

        #Seteamos un contexto en el que ejecutar las precondiciones, para que puedan acceder a los metodos, variables y
        #argumentos
        contexto_precondiciones = Contexto.new(self)
        contexto_precondiciones.set_argumentos_metodo(parametros_metodo)
        contexto_precondiciones.set_variables_instancia(variables_instancia)

        #Ejecutamos las precondiciones en el contexto preparado previamente, si no se esta ejecutando un contrato
        if !@ejecutando_contrato
          @ejecutando_contrato=true
          precondiciones_metodo.each do |precondicion|
            if !contexto_precondiciones.instance_eval(&precondicion)
              raise "No se cumplen las precondiciones"
            end
          end
          @ejecutando_contrato=false
        end

        #Ejecutamos el comportamiento de los before en el contexto de la instancia, si no se esta ejecutando un contrato
        #o el metodo initialize
        if !@ejecutando_contrato
         @ejecutando_contrato=true
          if(!(nombre_metodo==:initialize))
            self.class.lista_before.each { |bloque| self.instance_eval(&bloque)}
          end
         @ejecutando_contrato=false
        end

        #Ejecutamos el comportamiento del metodo original y nos guardamos su valor de retorno para retornarlo luego
        valor_retorno = metodo_original.bind(self).call(*args,&block)

        #Ejecutamos el comportamiento de los after en el contexto de la instancia, si no se esta ejecutando un contrato
        #o el metodo initialize
        if !@ejecutando_contrato
          @ejecutando_contrato=true
          if(!(nombre_metodo==:initialize))
            self.class.lista_after.each { |bloque| self.instance_eval(&bloque)}
          end
          @ejecutando_contrato=false
        end

        #Ejecutamos las invariantes en el contexto de la instancia, si no se esta ejecutando un contrato
        if !@ejecutando_contrato
          @ejecutando_contrato=true
          self.class.lista_invariantes.each do|invariante|
            if !self.instance_eval(&invariante)
              raise "El estado del objeto es inconsistente"
            end
          end
          @ejecutando_contrato=false
        end

        #Armamos una lista con el nombre de cada variable de instancia y su valor actual
        nombres_variables_instancia = self.instance_variables
        valores_variables_instancia = nombres_variables_instancia.map {|nombre_var| self.instance_variable_get(nombre_var)}
        variables_instancia = nombres_variables_instancia.zip(valores_variables_instancia)

        #Seteamos un contexto en el que ejecutar las postcondiciones, para que puedan acceder a los metodos, variables y
        #argumentos. Usamos los mismos argumentos que en las precondiciones porque se asume que su valor no cambia.
        contexto_postcondiciones = Contexto.new(self)
        contexto_postcondiciones.set_argumentos_metodo(parametros_metodo)
        contexto_postcondiciones.set_variables_instancia(variables_instancia)

        #Ejecutamos las postcondiciones en el contexto preparado previamente, si no se esta ejecutando un contrato
        if !@ejecutando_contrato
           @ejecutando_contrato=true
           postcondiciones_metodo.each do |postcondicion|
             if !contexto_postcondiciones.instance_exec(valor_retorno, &postcondicion)
               raise "No se cumplen las postcondiciones"
             else
               puts "Se cumplen las postcondiciones"
             end
           end
          @ejecutando_contrato=false
        end

        #Retornamos el valor de retorno del metodo original
        valor_retorno
      end
      @new_method = true
    end
  end

  def invariant(&estado_consistente)
    lista_invariantes << estado_consistente
  end

  def pre(&precondicion)
    lista_precondiciones << precondicion
  end

  def post(&postcondicion)
    lista_postcondiciones << postcondicion
  end

end

module Contratos
  def self.included(clase)
    clase.extend MetodosDeContratos
  end
end



