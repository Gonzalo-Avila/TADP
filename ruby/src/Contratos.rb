module MetodosDeContratos
  @@listaDeBefore = []
  @@listaDeAfter = []

  def before_and_after_each_call(procBefore, procAfter)
      @@listaDeBefore << procBefore
      @@listaDeAfter << procAfter
  end

  @@new_method = true
  def method_added(name)

    if @@new_method
      @@new_method = false
      old_method = instance_method(name)

      if old_method.arity == 0
        define_method(name) do
          @@listaDeBefore.each { |bloque| bloque.call}
          old_method.bind(self).call
          @@listaDeAfter.each { |bloque| bloque.call }
        end
      else
        define_method(name) do |args,&block|
          @@listaDeBefore.each { |bloque| bloque.call}
          old_method.bind(self).call(args,&block)
          @@listaDeAfter.each { |bloque| bloque.call }
        end
      end

      @@new_method = true
    end
  end

end

module Contratos
  def self.included(clase)
    clase.extend MetodosDeContratos
  end
end

class Prueba
  include Contratos

  before_and_after_each_call( proc {puts "hola"}, proc {puts "chau"} )
  before_and_after_each_call( proc {puts "holahola"}, proc {puts "chauchau"} )

  def m
    puts "1"
  end

  def pruebita(unString)
    puts unString
  end
end










