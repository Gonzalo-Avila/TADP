class A
  def self.ejecutarProc(procAEjecutar)
    var=0
    class_eval(&procAEjecutar)
  end
end

var = 5
p = proc do
  puts var
end
p.call

A.ejecutarProc(p)