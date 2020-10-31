package operaciones

import general._

import scala.util.{Failure, Success, Try}

class ParserMap[T,X](parserOriginal: Parser[T], funcion: T => X) extends Parser[X]{
  def apply(cadena:String): Try[Resultado[X]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    //TODO - Usar map
    Try(
        resultadoOriginal match {
          case Failure(error) => throw new Exception()
          case Success(resultado) => Resultado(funcion(resultado.getElementoParseado), resultado.getCadenaRestante)
        }
    )
  }
}