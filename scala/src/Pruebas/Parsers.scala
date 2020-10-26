import scala.io.Source
import scala.util.{Try,Success,Failure}

trait Parser{
  def apply(cadena:String): Try[Resultado[_]]
  
  def <|> (otroParser:Parser) = new Or(this,otroParser);
  def <>  (otroParser:Parser) = new Concat(this,otroParser);
  
}

class Resultado[T](elementoParseado:T, cadenaRestante:String){
  def getElementoParseado = elementoParseado;
  def getCadenaRestante = cadenaRestante;
}

class Or (parser1:Parser, parser2:Parser) extends Parser {
 
  def apply (cadena:String): Try[Resultado[_]] = {
      
      val resultadoParser1 = parser1.apply(cadena);
      resultadoParser1 match {
      case Success(resultado) => resultadoParser1;
      case Failure(error) => parser2.apply(cadena);
      }
   }
}

class Concat (parser1:Parser, parser2:Parser) extends Parser {
  
  def apply(cadena:String): Try[Resultado[_]] = {
      val resultadoParser1 = parser1.apply(cadena);
      resultadoParser1 match {
        case Failure(errorEnParser1) => throw new Exception();
        case Success(resultado1) => {
          val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante);
          resultadoParser2 match {
            case Failure(errorEnParser2) => throw new Exception();
            case Success(resultado2) => 
              Try(new Resultado((resultadoParser1.get.getElementoParseado,
                  resultadoParser2.get.getElementoParseado),
                  resultadoParser2.get.getCadenaRestante));
          }
        }
    }
  }
}

object anyChar extends Parser {
  
   def apply (cadena:String): Try[Resultado[Char]] = {
      Try(
          cadena match {
          case "" => throw new Exception();
          case cad => new Resultado(cad.head,cad.tail);
          }
      )
  }
}

class char (caracter: Char) extends Parser {
    def apply(cadena:String): Try[Resultado[Char]] = {
        Try (
          cadena match {
          case "" => throw new Exception();
          case c if c.head == caracter => new Resultado(c.head,c.tail);
          case _ => throw new Exception();
          }
      )
    }
}


class string (cadenaFiltro:String) extends Parser{
  
  def apply(cadenaAParsear:String): Try[Resultado[String]] = {
	  Try (
  	    cadenaAParsear match {
  		  case "" => throw new Exception();
  		  case c if c startsWith cadenaFiltro => new Resultado(cadenaFiltro,cadenaAParsear.substring(cadenaFiltro.size));
  		  case _ => throw new Exception();
   	   }
		)   
   }
}

object digit extends Parser {
  
   def apply(cadena:String): Try[Resultado[Char]] = {
		   Try(
		     cadena match {
		       case cad if cad.head.isDigit => new Resultado(cad.head,cad.tail);
		       case _ => throw new Exception();
		     }
		   )
   }
}

object integer extends Parser {
  def apply(cadena:String): Try[Resultado[Int]] = {
    Try(
      cadena match {
        case cad if cad.head!= '-' && !cad.head.isDigit => throw new Exception();
        case cad if !cad.tail.matches("^[0-9]+$") => throw new Exception();
        case cad => new Resultado (cad.toInt,cad);
      }
    )
  }
}

/*  
  def double(cadena:String): Try[Double] = {
    var cadenaSeparada = cadena.split(".")
    Try(
        cadena match {
          case cad if cadenaSeparada.size>2 => throw new Exception();
          case cad if 
          cadenaSeparada.size == 2 &&
          integer(cadenaSeparada.head) == Success(cadenaSeparada.head.toInt) => throw new Exception();
        }
    )
        
  }
  
  def num(numero:Int , lista: List[Int]): Int = {
    lista match {
      case Nil => throw new Exception();
      case numero::tail  => numero;
      case _ =>  throw new Exception();
    }
  }
    
  def num1 = num(1,_:List[Int]);
		  
}*/