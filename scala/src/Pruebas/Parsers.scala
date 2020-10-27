import scala.io.Source
import scala.util.{Try,Success,Failure}

trait Parser[T]{
  
  def apply(cadena:String): Try[Resultado[T]]
  
  //Combinators
  def <|> (otroParser:Parser[T]) = new Or(this,otroParser)
  def <>  (otroParser:Parser[T]) = new Concat(this,otroParser)
  def ~> (otroParser:Parser[T]) = new RightMost(this,otroParser)
  def <~ (otroParser:Parser[T]) = new LeftMost(this,otroParser)
  //def sepBy(otroParser:Parser[_]) = new SeparatedBy(this, otroParser)

  //Operaciones
  def satisfies (condicion: T => Boolean) = new ParserCondicional(this, condicion)
  def opt = new ParserOpcional(this);
  def * = new ClausuraKleene(this);
  def + = new ClausuraPKleene(this);
  def map (funcion: T => Any) = new ParserMap(this, funcion);
}

/*class SeparatedBy[T,X](parser1: Parser[T], parser2: Parser[X]){
  
    def apply (cadena:String): Try[Resultado[T]] = {
      
    }
}*/

class ParserMap[T,X](parserOriginal: Parser[T], funcion: T => X) extends Parser[X]{
  def apply(cadena:String): Try[Resultado[X]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    Try(
        resultadoOriginal match {
          case Failure(error) => throw new Exception()
          case Success(resultado) => new Resultado(funcion(resultado.getElementoParseado), resultado.getCadenaRestante)
        }
    )
  }
}

class ClausuraKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{
  def apply(cadena:String): Try[Resultado[List[T]]] = {
    
    var listaParcial: List[T] = List()
    var cadenaParcial = cadena
    var seguir = true
    
    while (seguir){
        val resultadoActual = parserOriginal.apply(cadenaParcial)
        resultadoActual match {
          case Failure(error) => seguir = false
          case Success(resultado) => {
            cadenaParcial = resultado.getCadenaRestante
            listaParcial = listaParcial ++ List(resultado.getElementoParseado)
          }
        }
    }
    Try(new Resultado(listaParcial,cadenaParcial))
  }
}

class ClausuraPKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{
  def apply(cadena:String): Try[Resultado[List[T]]] = {
    
    var listaParcial: List[T] = List()
    var cadenaParcial = cadena
    var seguir = true
    
    while (seguir){
        val resultadoActual = parserOriginal.apply(cadenaParcial)
        resultadoActual match {
          case Failure(error) => seguir = false
          case Success(resultado) => {
            cadenaParcial = resultado.getCadenaRestante
            listaParcial = listaParcial ++ List(resultado.getElementoParseado)
          }
        }
    }
    Try(
        listaParcial match {
          case listaVacia if listaVacia.isEmpty => throw new Exception
          case listaNoVacia => new Resultado(listaParcial,cadenaParcial)
        }
       
     )
  }
}


class Resultado[T](elementoParseado:T, cadenaRestante:String){
  def getElementoParseado = elementoParseado
  def getCadenaRestante = cadenaRestante
}

class ParserCondicional[T](parserOriginal:Parser[T], condicion:T => Boolean) extends Parser[T]{
   def apply(cadena:String): Try[Resultado[T]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    Try(
      resultadoOriginal match{
        case Failure(error) => throw new Exception()
        case Success(resultado) if !condicion(resultado.getElementoParseado) => throw new Exception()
        case Success(resultado) => resultado
      }
    )
  }
}

class ParserOpcional[T](parserOriginal:Parser[T]) extends Parser[Option[T]]{
  
  def apply(cadena:String): Try[Resultado[Option[T]]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    Try(
          resultadoOriginal match {
            case Success(resultado) => new Resultado (Some(resultado.getElementoParseado), resultado.getCadenaRestante)
            case Failure(error) => new Resultado(None, cadena)
          } 
    )
  }
}

class Or [T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T] {
 
  def apply (cadena:String): Try[Resultado[T]] = {
      
      val resultadoParser1 = parser1.apply(cadena)
      resultadoParser1 match {
      case Success(resultado) => resultadoParser1
      case Failure(error) => parser2.apply(cadena)
      }
   }
}

class Concat [T](parser1:Parser[T], parser2:Parser[T]) extends Parser [Tuple2[T,T]]{
  
  def apply(cadena:String): Try[Resultado[Tuple2[T,T]]] = {
      val resultadoParser1 = parser1.apply(cadena)
      resultadoParser1 match {
        case Failure(errorEnParser1) => throw new Exception();
        case Success(resultado1) => {
          val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
          resultadoParser2 match {
            case Failure(errorEnParser2) => throw new Exception()
            case Success(resultado2) => 
              Try(new Resultado((resultadoParser1.get.getElementoParseado,
                  resultadoParser2.get.getElementoParseado),
                  resultadoParser2.get.getCadenaRestante))
          }
        }
    }
  }
}

class RightMost [T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T]{
  def apply(cadena:String): Try[Resultado[T]] = {
    val concat = parser1 <> parser2
    val concatAplicado = concat.apply(cadena)
  
    Try(new Resultado(concatAplicado.get.getElementoParseado._2,concatAplicado.get.getCadenaRestante))
  }
}

class LeftMost[T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T]{
   def apply(cadena:String): Try[Resultado[T]] = {
    val concat = parser1 <> parser2
    val concatAplicado = concat.apply(cadena)
  
    Try(new Resultado(concatAplicado.get.getElementoParseado._1,concatAplicado.get.getCadenaRestante))
  }
}

/*
class RightMost [T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T]{
      def apply(cadena:String): Try[Resultado[T]] = {
          val resultadoParser1 = parser1.apply(cadena);
          resultadoParser1 match {
            case Failure(errorEnParser1) => throw new Exception();
            case Success(resultado1) => {
              val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante);
              resultadoParser2 match {
                case Failure(errorEnParser2) => throw new Exception();
                case Success(resultado2) => 
                  resultadoParser2;
              }
             }
          }
      }
}*/
/*
class LeftMost[T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T]{
    def apply(cadena:String): Try[Resultado[T]] = {
            val resultadoParser1 = parser1.apply(cadena);
            resultadoParser1 match {
                case Failure(errorEnParser1) => throw new Exception();
                case Success(resultado1) => {
                  val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante);
                  resultadoParser2 match {
                    case Failure(errorEnParser2) => throw new Exception();
                    case Success(resultado2) => 
                      resultadoParser1;
                  }
                }
            }
    }
}*/

object anyChar extends Parser[Char] {
  
   def apply (cadena:String): Try[Resultado[Char]] = {
      Try(
          cadena match {
          case "" => throw new Exception();
          case cad => new Resultado(cad.head,cad.tail);
          }
      )
  }
}

class char (caracter: Char) extends Parser[Char] {
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
object char {
    def apply(caracter: Char): char = {
        new char(caracter)
    }
}


class string (cadenaFiltro:String) extends Parser[String]{
  
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

object string {
    def apply(cadenaFiltro: String): string = {
        new string(cadenaFiltro)
    }
}

object digit extends Parser[Char]{
  
   def apply(cadena:String): Try[Resultado[Char]] = {
       Try(
         cadena match {
           case cad if cad.head.isDigit => new Resultado(cad.head,cad.tail);
           case _ => throw new Exception();
         }
       )
   }
}

object integer extends Parser [Int]{
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

object double extends Parser [Double]{
  def apply(cadena: String): Try[Resultado[Double]] = {
    if (!(cadena.contains('.'))) throw new Exception()

    val cadenaSeparada: Array[String] = cadena.split('.')
    val parteEnteraParseada = integer.apply(cadenaSeparada(0))
    val parteDecimalParseada = integer.apply(cadenaSeparada(1))

    Try(
      parteEnteraParseada match {
        case Failure(_) => throw new Exception()
        case Success(_) =>
          parteDecimalParseada match {
            case Success(_) => new Resultado(cadena.toDouble, cadena)
            case Failure(_) => throw new Exception()
          }
      }
    )

  }
}
		  
