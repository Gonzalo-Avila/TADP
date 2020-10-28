package operaciones

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._

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