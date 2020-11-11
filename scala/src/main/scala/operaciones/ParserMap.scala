package operaciones

import general._

import scala.util.{Failure, Success, Try}

case class ParserMap[T,X](parserOriginal: Parser[T], funcion: T => X) extends Parser[X]{

  def apply(cadena:String): Try[Resultado[X]] = parserOriginal.apply(cadena).map{r => Resultado(funcion(r.getElementoParseado), r.getCadenaRestante)}

}