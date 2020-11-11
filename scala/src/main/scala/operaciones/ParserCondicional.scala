package operaciones

import general._

import scala.util.{Failure, Success, Try}

case class ParserCondicional[T](parserOriginal:Parser[T], condicion:T => Boolean) extends Parser[T]{

  def apply(cadena:String): Try[Resultado[T]] = parserOriginal.apply(cadena).filter(r => condicion(r.getElementoParseado))

}