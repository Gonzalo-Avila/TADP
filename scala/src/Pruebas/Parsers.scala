import scala.io.Source
import scala.util.{Try,Success,Failure}

trait Parsers {
  
   def anyChar(cadena:String): Try[Char] = {
      Try{
          cadena match {
          case "" => throw new Exception();
          case _ => cadena.head;
          }
    }
  }
  
  def char(caracter: Char, cadena:String): Try[Char] = {
    Try{
        cadena match {
        case "" => throw new Exception();
        case c if c.head == caracter => c.head;
        case _ => throw new Exception();
        }
    }
  }
  
   def string(cadenaFiltro:String, cadenaAParsear:String): Try[String] = {
		   Try{
  			   cadenaAParsear match {
  			   case "" => throw new Exception();
  			   case c if c contains cadenaFiltro => cadenaFiltro;
  			   case _ => throw new Exception();
  			   }
		   }
		   
   }
   
   def digit(caracter:Char): Try[Int] = {
		   Try{
		     caracter match {
		       case c if c.isDigit => c.asDigit;
		       case _ => throw new Exception();
		     }
		   }
   }
		   
  def num(numero:Int , lista: List[Int]): Int = {
    lista match {
      case Nil => throw new Exception();
      case numero::tail  => numero;
      case _ =>  throw new Exception();
    }
  }
    
  def num1 = num(1,_:List[Int]);
}