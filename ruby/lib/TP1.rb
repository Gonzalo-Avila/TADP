class Framework

  attr_accessor :listaDeBefore
  attr_accessor :listaDeAfter

  def initialize
    @listaDeBefore=Array.new
    @listaDeAfter=Array.new
  end

  def before_and_after_each_call(bloque_before,bloque_after)
    @listaDeBefore.push(bloque_before)
    @listaDeAfter.push(bloque_after)

  end

  def send(metodo)
    @listaDeBefore.each { |bloque| bloque.call }
    super(metodo)
    @listaDeAfter.each { |bloque| bloque.call }
  end



end


class TemplateClass < Framework

  def initialize
    super
    before_and_after_each_call(proc {puts "Antes"}, proc {puts "Despues"})
  end

  def m
    puts "A"
  end

  def self.qwe
    puts("QWEQWE")
  end
end


TemplateClass.new.send(:m)
TemplateClass.new.m
d

