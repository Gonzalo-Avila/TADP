module Framework

   @@listaDeBefore = []
   @@listaDeAfter = []

  def included(clase)

    super
    clase.singleton_class.define_method(:before_and_after_each_call) do
      |bloque_before,bloque_after|
      @@listaDeBefore.push(bloque_before)
      @@listaDeAfter.push(bloque_after)
    end

  end

   def method_added(metodo)

     puts "Entrando a method_added"

     if @@metodoNuevo
       @@metodoNuevo = false
       old_method = instance_method(metodo)
       define_method(metodo) do
         puts("Antes")
         old_method.call
         puts("Despues")
       end
       @@metodoNuevo = true
     end

     super

   end

end


class TemplateClass

  extend Framework

  before_and_after_each_call(proc {puts "Antes"}, proc {puts "Despues"})

  def m
    puts "A"
  end

  def asd
  end

end


