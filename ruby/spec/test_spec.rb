describe Contratos do
  describe '#Before_and_after' do
    class A
      attr_accessor :var1, :var2, :var3, :var4
      include Contratos
      before_and_after_each_call(proc {@var1+=2}, proc{@var2-=1})
      before_and_after_each_call(proc {@var3+=4}, proc{@var4-=3})

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
      expect(instance.var1).to eq(0)
      expect(instance.var2).to eq(10)
      expect(instance.var3).to eq(0)
      expect(instance.var4).to eq(10)
      instance.m
      expect(instance.var1).to eq(2)
      expect(instance.var2).to eq(9)
      expect(instance.var3).to eq(4)
      expect(instance.var4).to eq(7)
    end

  end

  describe '#Invariantes' do
    class B
      attr_accessor :var1, :var2, :var3, :var4
      include Contratos

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
        @var = 6

        def initialize
          @var = 6
        end

        include Contratos

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
      include Contratos

      post{a == 0 }
      def a(a)
        a - a
      end
    end

    it("Le resto a un numero su propio valor y devuelve 0") do
      expect(Ha.new.a(2)).to eq(0)
    end

  end
end



