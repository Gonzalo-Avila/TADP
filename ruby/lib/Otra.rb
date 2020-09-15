module A
  def self.method_added(m)
    puts "Se esta agregando un metodo"
  end
end

class B
  include A
  def metodo
    puts "Metodo"
  end
end
