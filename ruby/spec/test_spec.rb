describe Contratos do
  describe '#Before_and_after' do
    class A

      #Si incluimos contratos antes de declarar los accessors, los before and after se ejecutaran cada vez que se haga
      #un get o un set de una variable
      include Contratos
      attr_accessor :var1, :var2, :var3, :var4
      before_and_after_each_call(proc {@var1+=1}, proc{@var2-=1})
      before_and_after_each_call(proc {@var3+=1}, proc{@var4-=1})
      def initialize
        @var1 = 0
        @var2 = 10
        @var3 = 0
        @var4 = 10
      end

      def m
      end

    end

    it("Se ejecutan todos los before_and_after") do
      instance = A.new
      expect(instance.var1).to eq(1)
      expect(instance.var2).to eq(9)
      expect(instance.var3).to eq(3)
      expect(instance.var4).to eq(7)
      instance.m
      expect(instance.var1).to eq(6)
      expect(instance.var2).to eq(4)
      expect(instance.var3).to eq(8)
      expect(instance.var4).to eq(2)
    end

  end

  describe '#Invariantes' do
    class B
      include Contratos
      attr_accessor :var1, :var2, :var3, :var4

      invariant {@var1 == 2}
      invariant {@var2 < 2}
      invariant {@var3 < @var1 * @var4}

      def initialize(var1,var2,var3,var4)
        @var1 = var1
        @var2 = var2
        @var3 = var3
        @var4 = var4
      end

      def incrementarYRetornarVar(nombre,valor)
        v = self.instance_variable_get(nombre)
        v+=valor
        ve =self.instance_variable_set(nombre,v)
        ve
      end

    end

    it("No se puede instanciar un objeto inconsistente") do
      expect{B.new(1,0,0,5)}.to raise_error(RuntimeError,"El estado del objeto es inconsistente")
    end

    it("Se instancia correctamente un objeto con valores consistentes") do
      expect{B.new(2,0,0,1)}.not_to raise_error
    end

    it("Un metodo no tira error si deja al objeto en un estado consistente") do
      expect(B.new(2,0,0,2).incrementarYRetornarVar(:@var2,1)).to eq(1)
    end

    it("Un metodo tira error si deja al objeto en un estado inconsistente") do
      i = B.new(2,0,0,4)
      expect do
        i.incrementarYRetornarVar(:@var2,2)
      end.to raise_error(RuntimeError, "El estado del objeto es inconsistente")
    end

    it("Varias invariantes se validan correctamente") do
      i = B.new(2,0,0,4)
      expect(i.incrementarYRetornarVar(:@var2,1)).to eq(1)
      expect(i.incrementarYRetornarVar(:@var3,7)).to eq(7)
      expect{i.incrementarYRetornarVar(:@var3,1)}.to raise_error(RuntimeError,"El estado del objeto es inconsistente")
    end

  end

  describe '#Precondiciones' do
    class Ha
      include Contratos

      @var = 6

      def initialize
        @var = 6
      end


      pre{a < 10}
      def a(a,b)
        a + b
      end

      pre{@var > 5}
      def b(a)
        a
      end
    end

    it("Con a < 10 la precondicion pasa y devuelve a + b") do
      expect(Ha.new.a(1,2)).to eq(3)
    end

    it("Con a > 10 la precondicion no pasa y devuelve exception") do
      expect{Ha.new.a(11,1)}.to raise_error(RuntimeError,"No se cumplen las precondiciones")
    end

    it("Con @var > 5 la precondicion pasa y devuelve @var") do
      expect(Ha.new.b(2)).to eq(2)
    end
  end

  describe '#Postcondiciones' do
    class Hb
      @var = 0

      def initialize
        @var = 0
      end

      include Contratos

      post{|retorno| retorno == 0 }
      def a(a)
        a - a
      end

      post{|retorno| retorno > 0}
      def b(b)
        b.abs
      end

      post{|retorno| retorno > 0}
      def c(c)
        c - 2*c
      end

      post{|retorno| retorno == 1}
      def d(d)
        d ** @var
      end
    end

    it("Le resto a un numero su propio valor y devuelve 0") do
      expect(Hb.new.a(2)).to eq(0)
    end

    it("Calculo el valor absoluto de un número negativo y este es mayor a 0") do
      expect(Hb.new.b(-5)). to eq(5)
    end

    it("Le resto a un numero su doble, pero debe ser mayor a 0. Por lo tanto falla y tira excepción") do
      expect{Hb.new.c(4)}.to raise_error(RuntimeError,"No se cumplen las postcondiciones")
    end

    it("Elevo un numero a 0 y devuelve 1") do
      expect(Hb.new.d(3)). to eq(1)
    end

  end

  describe '#Postcondiciones 2' do
    class C
      include Contratos
      attr_accessor :var1, :var2

      def initialize
        @var1=0
        @var2=0
      end

      post {|res| res>n1 && res>n2}
      def sumarPositivos(n1,n2)
        return n1 + n2
      end


    end

    it("Si las postcondiciones se cumplen se ejecuta el metodo correctamente") do
      expect(C.new.sumarPositivos(1,2)).to eq(3)
    end

    it("Si las postcondiciones no se cumplen se lanza una excepcion") do
      expect{C.new.sumarPositivos(1,-2)}.to raise_error(RuntimeError,"No se cumplen las postcondiciones")
    end

  end

  describe '#Contexto' do
    attr_accessor :contadorBefore, :contadorAfter
    class Y
      def m1
        puts "Ejecutando m1"
      end
    end

    class Z < Y
      include Contratos

      before_and_after_each_call(proc {puts "Antes"}, proc {puts "Despues"})

      def m2
        puts "Ejecutando m2"
      end
    end

    it("Los contratos no se aplican a metodos heredados") do
      i = Z.new
      i.m1
      i.m2
    end

  end

  describe '#Interacción con metodos dentro de los contratos' do
    #before m -> +1
    #before n -> +1
    #after n -> +1
    #m -> +1
    #Retorna 4
    #After m -> +1
    #Before var -> +1
    #Retorna 6
    #After var -> +1
    class F
      include Contratos
      attr_accessor :var
      before_and_after_each_call(proc {m}, proc {m})

      def initialize
        @var=0
      end

      def n
      end

      def m
        n
        @var+=1
      end

    end

    it("No se ejecutan los contratos de un metodo cuando es llamado desde un contrato") do
      i = F.new
      expect(i.m).to eq(4)
      expect(i.var).to eq(6)
    end

  end

  describe '#TP - Caso de uso 1 - Before_and_after' do
    class MiClase

      include Contratos
      before_and_after_each_call(
          # Bloque Before. Se ejecuta antes de cada mensaje
          proc{ puts "Entré a un mensaje" },
          # Bloque After. Se ejecuta después de cada mensaje
          proc{ puts "Salí de un mensaje"}
      )

      def mensaje_1
        puts "mensaje_1"
        return 5
      end

      def mensaje_2
        puts "mensaje_2"
        return 3
      end

    end

    it("El mensaje 1 retorna 5 e imprime dos mensajes") do
      expect(MiClase.new.mensaje_1).to eq(5)
    end

    it("El mensaje 2 retorna 3 e imprime dos mensajes") do
      expect(MiClase.new.mensaje_2).to eq(3)
    end

  end

  describe '#TP - Caso de uso 2 - Invariant' do
    class Guerrero
      include Contratos
      attr_accessor :vida, :fuerza, :nombre

      invariant { vida >= 0 }
      invariant { fuerza > 0 && fuerza < 100 }

      def initialize(vida,fuerza, nombre)
        @vida = vida
        @fuerza = fuerza
        @nombre = nombre
      end

      def atacar(otro)
        puts @nombre
        otro.recibirDanio(fuerza)

      end

      def recibirDanio(cantidad)
        puts @nombre
        @vida-=cantidad   #TODO -Si pongo self.vida en vez de @vida rompe, hay que ver por que
      end
    end

    it("El guerrero no puede tener 0 o menos de fuerza") do
      expect{Guerrero.new(15,0,"G1")}.to raise_error(RuntimeError,"El estado del objeto es inconsistente")
    end

    it("El guerrero no puede tener 100 o mas de fuerza") do
      expect{Guerrero.new(15,100,"G1")}.to raise_error(RuntimeError,"El estado del objeto es inconsistente")
    end

    it("El guerrero puede recibir daño correctamente") do
      guerrero1 = Guerrero.new(20,5,"G1")
      puts "1"
      guerrero2 = Guerrero.new(5,50,"G2")
      puts "2"
      guerrero1.atacar(guerrero2)
      puts "3"
      expect(guerrero2.vida).to eq(0)
      puts "4"
    end

    it("El guerrero no puede tener menos de 0 de vida") do
      guerrero1 = Guerrero.new(20,10,"G1")
      guerrero2 = Guerrero.new(5,50,"G2")
      expect{guerrero1.atacar(guerrero2)}.to raise_error(RuntimeError, "El estado del objeto es inconsistente")
    end
  end

  describe '#TP - Caso de uso 3 - Precondiciones y postcondiciones' do
    class Operaciones
      include Contratos
      #precondición de dividir
      pre { divisor != 0 }
      #postcondición de dividir
      post { |result| result * divisor == dividendo }
      def dividir(dividendo, divisor)
        dividendo / divisor
      end

      # este método no se ve afectado por ninguna pre/post condición
      def restar(minuendo, sustraendo)
        minuendo - sustraendo
      end

    end

    it("Se cumplen todas las postcondiciones si no hay resto") do
      expect(Operaciones.new.dividir(10,5)).to eq(2)
    end

    it("No se cumplen todas las postcondiciones si hay resto ") do
      expect{Operaciones.new.dividir(10,6)}.to raise_error(RuntimeError, "No se cumplen las postcondiciones")
    end

    it("No se puede dividir por 0") do
      expect{Operaciones.new.dividir(10,0)}.to raise_error(RuntimeError, "No se cumplen las precondiciones")
    end

    it("No se aplican las condiciones al metodo restar") do
      expect(Operaciones.new.restar(10,0)).to eq(10)
    end
  end

  describe '#TP - Caso de uso 4 - Ejemplo integrador' do

    class Pila
      include Contratos
      attr_accessor :current_node, :capacity

      invariant { capacity >= 0 }

      post { empty? }
      def initialize(capacity)
        @capacity = capacity
        @current_node = nil
      end

      pre { !full? }
      post { height > 0 }
      def push(element)
        @current_node = Node.new(element, current_node)
      end

      pre { !empty? }
      def pop
        element = top
        @current_node = @current_node.next_node
        element
      end

      pre { !empty? }
      def top
        current_node.element
      end

      def height
        empty? ? 0 : current_node.size
      end

      def empty?
        current_node.nil?
      end

      def full?
        height == capacity
      end

      Node = Struct.new(:element, :next_node) do
        def size
          next_node.nil? ? 1 : 1 + next_node.size
        end
      end
    end

    it("No explota") do
      pila = Pila.new(10)
    end

  end
end



