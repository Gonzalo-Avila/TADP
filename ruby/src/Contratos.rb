module MetodosDeContratos
  attr_accessor :listaDeBefore, :listaDeInvariantes, :listaDeAfter, :listaDePrecondiciones, :listaDePostcondiciones,
                :new_method

  def listaDeBefore
    @listaDeBefore ||= []
    @listaDeBefore
  end

  def listaDeAfter
    @listaDeAfter ||= []
    @listaDeAfter
  end

  def listaDeInvariantes
    @listaDeInvariantes ||= []
    @listaDeInvariantes
  end

  def listaDePrecondiciones
    @listaDePrecondiciones ||= []
    @listaDePrecondiciones
  end

  def listaDePostcondiciones
    @listaDePostcondiciones ||= []
    @listaDePostcondiciones
  end

  def before_and_after_each_call(procBefore, procAfter)
      listaDeBefore << procBefore
      listaDeAfter << procAfter
  end

  def obtenerYQuitarUltimasPrecondiciones
    precondiciones = listaDePrecondiciones.pop(listaDePrecondiciones.size)
    precondiciones
  end

  def obtenerYQuitarUltimasPostcondiciones
    postcondiciones = listaDePostcondiciones.pop(listaDePostcondiciones.size)
    postcondiciones
  end

  class Contexto
    def setearContexto(parametros)
      parametros.each do |nombre, valor|
        define_singleton_method(nombre) do
          valor
        end
      end
    end
    def setearContextoInstancia(parametros)
      parametros.each do |nombre,valor|
        self.instance_variable_set(nombre,valor)
      end
    end
  end

  def method_added(name)

    if @new_method.nil? || @new_method   #No entendi porque el metodo quedaba en loop, ¿no es mas facil asi?
      #puts (name)

      @new_method = false

      old_method = instance_method(name)

      #Con el nuevo cambio a las variables, ahora cuando se ejecuta el metodo en las instancias de las clases, estas
      #tienen que ir a buscar los procs a las listas que pertenecen a las clases y no a sus listas propias.
      #Por eso self.class
      preCondicionesDelMetodo = obtenerYQuitarUltimasPrecondiciones
      postCondicionesDelMetodo = obtenerYQuitarUltimasPostcondiciones
      define_method(name) do |*args,&block|

        nombreDeParametros = old_method.parameters.map {|_,nombre| nombre }
        nombreDeParametrosInstancia = self.instance_variables

        #Armo la lista con el nombre de cada parametro y su valor.
        parametros = []
        parametrosDeInstancia = []
        i = 0
        while i < nombreDeParametros.length
          parametros << [nombreDeParametros[i], args[i]]
          i = i + 1
        end

        i = 0
        while i < nombreDeParametrosInstancia.length
          parametrosDeInstancia << [nombreDeParametrosInstancia[i], self.instance_variable_get(nombreDeParametrosInstancia[i])]
          i = i + 1
        end

        #Preparo el contexto para Precondiciones y Postcondiciones
        contexto = Contexto.new
        contexto.setearContexto(parametros)
        contexto.setearContextoInstancia(parametrosDeInstancia)

        #EJECUTAR PRECONDICIONES
        preCondicionesDelMetodo.each do |precondicion|
          if !contexto.instance_eval(&precondicion)
            raise "No se cumplen las precondiciones"
          else
            #puts "Se cumplen las precondiciones"
          end
        end

        #EJECUTAR BEFORE
        self.class.listaDeBefore.each { |bloque| self.instance_eval(&bloque)}

        #EJECUTAR COMPORTAMIENTO DEL METODO ORIGINAL
        returnValue = old_method.bind(self).call(*args,&block)

        #EJECUTAR AFTER
        self.class.listaDeAfter.each { |bloque| self.instance_eval(&bloque)}

        #EJECUTAR INVARIANTES
        self.class.listaDeInvariantes.each do|invariante|
          if !self.instance_eval(&invariante)
            raise "El estado del objeto es inconsistente"
          else
            # puts "El estado del objeto es consistente"
          end
        end

        #EJECUTAR POSTCONDICIONES
        postCondicionesDelMetodo.each do |postcondicion|
          if !self.instance_exec(returnValue, &postcondicion)
            raise "No se cumplen las postcondiciones"
          else
            #  puts "Se cumplen las postcondiciones"
          end
        end

        #RETORNAR VALOR DE RETORNO DEL METODO ORIGINAL
        returnValue
      end
      @new_method = true
    end

  end

  def invariant(&estado_consistente)
    listaDeInvariantes << estado_consistente
  end

  def pre(&precondicion)
    listaDePrecondiciones << precondicion
  end

  def post(&postcondicion)
    listaDePostcondiciones << postcondicion
  end

end

module Contratos
  def self.included(clase)
    clase.extend MetodosDeContratos
  end
end




