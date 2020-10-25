

trait Parsers {
  
   def anyChar(cadena:String): Char = {
    cadena match {
      case "" => throw new Exception();
      case _ => cadena.head;
    }
  }
  
  /*def char(caracter: Char, cadena:String): Char = {
    cadena match {
      case "" => throw new Exception();
      case caracter::tail  => cadena.head;
      case _ => throw new Exception();
    }
  }*/
  
  def num(numero:Int , lista: List[Int]): Int = {
    lista match {
      case Nil => throw new Exception();
      case numero::tail  => numero;
      case _ =>  throw new Exception();
    }
  }
  
  def num1 = num(1,_:List[Int]);
}