require_relative 'Contratos.rb'

class Prueba
  include Contratos

  @var
  #before_and_after_each_call( proc {puts @var}, proc {puts @var} )
  invariant {@var<10}

  def initialize
    @var = 0
  end

  pre{@var<7}
  post{@var<7}
  def subirVar(unNumero)
    @var+=unNumero
  end

  pre{n1<7}
  def sumar(n1,n2)
    return n1+n2
  end

end

i = Prueba.new
i.subirVar(2)
i.subirVar(2)
i.subirVar(2)
puts i.sumar(2,3)


