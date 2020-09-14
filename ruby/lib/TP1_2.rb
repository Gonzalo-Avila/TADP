module Framework

  @@listaDeBefore = []
  @@listaDeAfter = []

  def before_and_after_each_call(bloque_before,bloque_after)
    @@listaDeBefore.push(bloque_before)
    @@listaDeAfter.push(bloque_after)
    #singleton_methods.each { |metodo| redefinirMetodo(metodo) }
  end

  def send(metodo)
    @@listaDeBefore.each { |bloque| bloque.call }
    super(metodo)
    @@listaDeAfter.each { |bloque| bloque.call }
  end

=begin
  def redefinirMetodo(metodo)
    metodo.define_singleton_method(:call) do
      puts "A"
      super
      puts "B"
    end
  end
=end

end


class TemplateClass
  extend Framework

  before_and_after_each_call(proc {puts "Antes"}, proc {puts "Despues"})

  def m
    puts "A"
  end

end


TemplateClass.new.send(:m)
