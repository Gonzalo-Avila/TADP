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
    def set_metodos_instancia(metodos)

      metodos.each do |nombre_metodo,metodo_bindeado|
        if(!nombre_metodo.nil?)
          define_singleton_method(nombre_metodo)do |*args,&bloque|
            metodo_bindeado.call(*args,&bloque)
          end
        end
      end
    end
  end

  def method_added(nombre_metodo)

    if @new_method.nil? || @new_method   #No entendi porque el metodo quedaba en loop, ¿no es mas facil asi?
      #puts (nombre_metodo)

      @new_method = false

      metodo_original = instance_method(nombre_metodo)

      #Con el nuevo cambio a las variables, ahora cuando se ejecuta el metodo en las instancias de las clases, estas
      #tienen que ir a buscar los procs a las listas que pertenecen a las clases y no a sus listas propias.
      #Por eso self.class
      precondiciones_metodo = pop_ultimas_precondiciones
      postcondiciones_metodo = pop_ultimas_postcondiciones

      define_method(nombre_metodo) do |*args,&block|

        @ejecutando_contrato||=false

        nombres_parametros = metodo_original.parameters.map {|_,nombre_parametro| nombre_parametro }
        nombres_variables_instancia = self.instance_variables

        #Armo la lista con el nombre de cada parametro y su valor.

        parametros_metodo = nombres_parametros.zip(args)
        variables_instancia = []

        i = 0
        while i < nombres_variables_instancia.length
          variables_instancia << [nombres_variables_instancia[i], self.instance_variable_get(nombres_variables_instancia[i])]
          i = i + 1
        end


        #Preparo el contexto para Precondiciones
        #puts "ROMPE ACA"
        contexto_precondiciones = Contexto.new
        #puts "ROMPE ACA 2"
        contexto_precondiciones.set_argumentos_metodo(parametros_metodo)
        #puts "ROMPE ACA 3"
        contexto_precondiciones.set_variables_instancia(variables_instancia)
        #puts "ROMPE ACA 4"

        #------------------------------------------------------------------------------------------------------
        nombre_metodos_instancia = self.class.instance_methods
        metodos_instancia = []
        k = 0
        while k < nombre_metodos_instancia.length
          metodos_instancia << [nombre_metodos_instancia[k], self.class.instance_method(nombre_metodos_instancia[k]).bind(self)]
          k = k + 1
        end

        contexto_precondiciones.set_metodos_instancia(metodos_instancia)
        #-------------------------------------------------------------------------------------------------------

        #EJECUTAR PRECONDICIONES

        if !@ejecutando_contrato
          @ejecutando_contrato=true
          precondiciones_metodo.each do |precondicion|
            if !contexto_precondiciones.instance_eval(&precondicion)
              raise "No se cumplen las precondiciones"
            else
              puts "Se cumplen las precondiciones"
            end
          end
          @ejecutando_contrato=false
        end


        #EJECUTAR BEFORE
        if !@ejecutando_contrato
         @ejecutando_contrato=true
          if(!(nombre_metodo==:initialize))
            self.class.lista_before.each { |bloque| self.instance_eval(&bloque)}
          end
         @ejecutando_contrato=false
        end

        #EJECUTAR COMPORTAMIENTO DEL METODO ORIGINAL
        valor_retorno = metodo_original.bind(self).call(*args,&block)

        #EJECUTAR AFTER
        if !@ejecutando_contrato
          @ejecutando_contrato=true
          if(!(nombre_metodo==:initialize))
            self.class.lista_after.each { |bloque| self.instance_eval(&bloque)}
          end
          @ejecutando_contrato=false
        end

        #EJECUTAR INVARIANTES
        if !@ejecutando_contrato
          @ejecutando_contrato=true
          self.class.lista_invariantes.each do|invariante|
            if !self.instance_eval(&invariante)
              raise "El estado del objeto es inconsistente"
            else
               puts "El estado del objeto es consistente"
            end
          end
          @ejecutando_contrato=false
        end

        j = 0
        while j < nombres_variables_instancia.length
          variables_instancia << [nombres_variables_instancia[j], self.instance_variable_get(nombres_variables_instancia[j])]
          j = j + 1
        end

        #Preparo el contexto para Postcondiciones
        contexto_postcondiciones = Contexto.new
        contexto_postcondiciones.set_argumentos_metodo(parametros_metodo)
        contexto_postcondiciones.set_variables_instancia(variables_instancia)

        #------------------------------------------------------------------------------------------------------
        nombre_metodos_instancia = self.class.instance_methods
        metodos_instancia = []
        k = 0
        while k < nombre_metodos_instancia.length
          metodos_instancia << [nombre_metodos_instancia[k], self.class.instance_method(nombre_metodos_instancia[k]).bind(self)]
          k = k + 1
        end
        contexto_postcondiciones.set_metodos_instancia(metodos_instancia)
        #-------------------------------------------------------------------------------------------------------

        #EJECUTAR POSTCONDICIONES
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

        #RETORNAR VALOR DE RETORNO DEL METODO ORIGINAL
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



