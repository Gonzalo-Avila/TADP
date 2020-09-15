class X
  attr_accessor :contador

  def initialize
    @contador = 0
  end

  def sumar
    @contador+=1
  end

end

instancia = X.new
puts instancia.contador
sumar_uno = instancia.method(:sumar)
sumar_uno.call
puts instancia.contador

sumar_uno.singleton_class.class_eval do
  def call
    puts "cambia algo"
  end
end

sumar_uno.call
instancia.sumar
puts instancia.contador