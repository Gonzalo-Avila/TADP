class X
  def self.m1
    puts "Normal"
  end
end

instancia1 = X.new
metodo1 = instancia1.singleton_class.method(:m1)

metodo1.singleton_class.define_method (:call) do
  puts ("Le agrego esto")
  
end

metodo1.call