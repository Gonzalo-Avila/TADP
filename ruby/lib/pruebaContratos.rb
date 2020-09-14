module Contratos
  attr_accessor :listaDeBefore
  attr_accessor :listaDeAfter

  def initialize
    listaDeBefore = []
    listaDeAfter = []
  end

  def self.included(clase)
    clase.class_eval do
      def before_and_after_each_call(procBefore, procAfter)
        listaDeBefore = listaDeBefore.push(procBefore)
        listaDeAfter = listaDeAfter.push(procAfter)

        agregarAntesDeCadaMetodo.call

      end
    end
  end

  def agregarAntesDeCadaMetodo()
    misMetodos = self.instance_methods(false)
    listaDeBefore.each {|bloque| }
  end

  def aplicarBloqueAListaDeMetodos(bloque, misMetodos)
    misMetodos.each {|metodo| agregarBefores(metodo)}
  end

  def agregarBefores(metodo)
    def metodo
      listaDeBefore.each {|bloque| bloque.call}
      super
    end
  end

  def self.method_added

  end
end