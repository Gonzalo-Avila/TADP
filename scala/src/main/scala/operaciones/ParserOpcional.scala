package operaciones

import general._

import scala.util.{Failure, Success, Try}

case class ParserOpcional[T](parserOriginal:Parser[T]) extends Parser[Option[T]]{

  def apply(cadena:String): Try[Resultado[Option[T]]] = parserOriginal.apply(cadena)
      .map{r => Resultado(Option(r.getElementoParseado),r.getCadenaRestante)}
      .recoverWith {_ => Try(Resultado(None,cadena))}

}