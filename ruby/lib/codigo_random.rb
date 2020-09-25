require_relative 'Contratos.rb'

class CodigoRandom
  include Contratos

  @var
  #before_and_after_each_call( proc {puts @var}, proc {puts @var} )
  #invariant {@var<10}

  def initialize
    @var = 0
  end

  pre{@var<7}
  post{@var<7}
  def subirVar(unNumero)
    @var+=unNumero
  end

  pre{n1<7 && @var==0}
  post{|val| n1+n2==val && @var<5}
  def sumar(n1,n2)
    @var+=4
    return n1+n2
  end


end

i = CodigoRandom.new

puts i.sumar(6,3)


