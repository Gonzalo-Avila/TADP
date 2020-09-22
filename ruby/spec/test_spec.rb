
describe Contratos do
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
end

