module MetodosDeContratos
  @@listaDeBefore = []
  @@listaDeAfter = []
  @@listaDeInvariantes = []
  @@listaDePrecondiciones = []
  @@listaDePostcondiciones = []

  @@preCount = 0
  @@postCount = 0

  def before_and_after_each_call(procBefore, procAfter)
      @@listaDeBefore << procBefore
      @@listaDeAfter << procAfter
  end

  @@new_method = true

  def method_added(name)

    initialPreVal = @@preCount
    finalPreVal = @@listaDePrecondiciones.size
    @@preCount = finalPreVal

    initialPostVal = @@postCount
    finalPostVal = @@listaDePostcondiciones.size
    @@postCount = finalPostVal

    if @@new_method
      @@new_method = false
      old_method = instance_method(name)
      if old_method.arity == 0
        define_method(name) do

          i=initialPreVal
          j=initialPostVal

          while i<finalPreVal do
            if !@@listaDePrecondiciones.at(i).call
              raise "No se cumplen las precondiciones"
            else
              puts "Se cumplen las precondiciones"
            end
            i+=1
          end

          @@listaDeBefore.each { |bloque| bloque.call }
          returnValue = old_method.bind(self).call
          @@listaDeAfter.each { |bloque| bloque.call } #Evaluar contexto

          while j<finalPostVal do
            if !@@listaDePostcondiciones.at(j).call(returnValue)
              raise "No se cumplen las postcondiciones"
            else
              puts "Se cumplen las postcondiciones"
            end
            j+=1
          end

          return returnValue

        end

      else

        define_method(name) do |*args,&block|

          i=initialPreVal
          j=initialPostVal

          while i<finalPreVal do
            if !@@listaDePrecondiciones.at(i).call(*args)
              raise "No se cumplen las precondiciones"
            else
              puts "Se cumplen las precondiciones"
            end
            i+=1
          end

          @@listaDeBefore.each { |bloque| bloque.call}
          returnValue = old_method.bind(self).call(*args,&block)
          @@listaDeAfter.each { |bloque| bloque.call }


          while j<finalPostVal do
            if !@@listaDePostcondiciones.at(j).call(returnValue)
              raise "No se cumplen las postcondiciones"
            else
              puts "Se cumplen las postcondiciones"
            end
            j+=1
          end

          return returnValue

        end

      end

      @@new_method = true
    end
  end

  def invariant(&estadoConsistente)
    chequearConsistencia = proc do |*args|
      if !estadoConsistente.call(*args)
        raise "El estado del objeto es inconsistente"
      else
        puts "El estado del objeto es consistente"
      end
    end
    @@listaDeAfter << chequearConsistencia
    @@listaDeInvariantes << chequearConsistencia
  end

  def pre (&condicion)
    @@listaDePrecondiciones << condicion
  end

  def post (&condicion)
    @@listaDePostcondiciones << condicion
  end


end

module Contratos
  def self.included(clase)
    clase.extend MetodosDeContratos
  end
end




class Prueba

  include Contratos
  attr_accessor :vida
  before_and_after_each_call( proc {puts "Entrando a un metodo"}, proc {puts "Saliendo de un metodo"} )

  invariant{vida>0}

  pre {1<2}
  post {1<2}
  def m1
    puts "Ejecutado metodo 1"
  end

  pre{2==4/2}
  def m2
    puts "Ejecutado metodo 2"
  end

  post{4==2*2}
  def m3
    puts "Ejecutado metodo 3"
  end

  def m4
    puts "Ejecutado metodo 4"
  end
end

prueba = Prueba.new
prueba.m1
prueba.m2
prueba.m3
prueba.m4

class Prueba2

  include Contratos

  attr_accessor :contador

  def initialize
    @contador=0
  end

  #pre{arg1>arg2}
  #
  post{|res| res==2}
  def multiplicar(num1, num2)
    return num1*num2
  end

end

puts Prueba2.new.multiplicar(2,1)












