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
    finalPreVal = @@listaDePrecondiciones.size-1
    @@preCount = finalPreVal+1

    initialPostVal = @@postCount
    finalPostVal = @@listaDePostcondiciones.size-1
    @@postCount = finalPostVal+1

    if @@new_method
      @@new_method = false
      old_method = instance_method(name)
      if old_method.arity == 0
        define_method(name) do

          for i in initialPreVal..finalPreVal
            if !@@listaDePrecondiciones.at(i).call
              raise "No se cumplen las precondiciones"
            else
              puts "Se cumplen las precondiciones"
            end
          end

          @@listaDeBefore.each { |bloque| bloque.call}
          old_method.bind(self).call
          @@listaDeAfter.each { |bloque| bloque.call }

          for j in initialPostVal..finalPostVal
            if !@@listaDePostcondiciones.at(j).call
              raise "No se cumplen las postcondiciones"
            else
              puts "Se cumplen las postcondiciones"
            end
          end

        end

      else

        define_method(name) do |args,&block|

          for i in initialPreVal..finalPreVal
            if !@@listaDePrecondiciones.at(i).call
              raise "No se cumplen las precondiciones"
            else
              puts "Se cumplen las precondiciones"
            end
          end

          @@listaDeBefore.each { |bloque| bloque.call}
          old_method.bind(self).call(args,&block)
          @@listaDeAfter.each { |bloque| bloque.call }


          for j in initialPostVal..finalPostVal
            if !@@listaDePostcondiciones.at(j).call
              raise "No se cumplen las postcondiciones"
            else
              puts "Se cumplen las postcondiciones"
            end
          end

        end

      end

      @@new_method = true
    end
  end


  def invariant(&estadoConsistente)
    chequearConsistencia = proc do
      if !estadoConsistente.call
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

  def new
    super
    #@@listaDeInvariantes.each { |check| check.call } Esto rompe, but why
  end
end

module Contratos
  def self.included(clase)
    clase.extend MetodosDeContratos
  end
end

class Prueba

  include Contratos

  before_and_after_each_call( proc {puts "Entrando a un metodo"}, proc {puts "Saliendo de un metodo"} )

  invariant{25==25}

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










