package operaciones

import general._

import scala.util.{Failure, Success, Try}

class ParserCondicional[T](parserOriginal:Parser[T], condicion:T => Boolean) extends Parser[T]{
   def apply(cadena:String): Try[Resultado[T]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
     //TODO - filtrar try -> resultadoOriginal.filter(!condicion(resultado.getElementoParseado))
    Try(
      resultadoOriginal match{
        case Failure(error) => throw new Exception()
        case Success(resultado) if !condicion(resultado.getElementoParseado) => throw new Exception()
        case Success(resultado) => resultado
      }
    )
  }
}