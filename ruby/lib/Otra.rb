module A
  @Variable1
  @Variable2

  def metodo
    @Variable1 ||= 0
    @Variable1 += 1
  end

  def getVariable
    @Variable1
  end

end

class B
  extend A
  include A
  @A

  def obtenerA
    @A ||= 0
    @A
  end
  def sumarAA
    @A ||= 0
    @A+=1
  end

  def self.obtenerA
    @A ||= 0
    @A
  end
  def self.sumarAA
    @A ||= 0
    @A+=1
  end



end

class C
  extend A
  include A

end


bloque = proc {puts "Hola"}
instance_eval(&bloque)