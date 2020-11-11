package combinators

import general._

import scala.util.{Failure, Success, Try}

case class LeftMost[T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser[T]{

  def apply(cadena:String): Try[Resultado[T]] =
    parser1.apply(cadena).map {r =>
      Resultado(r.getElementoParseado,parser2.apply(r.getCadenaRestante).get.getCadenaRestante)}

}