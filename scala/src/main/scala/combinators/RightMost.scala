package combinators

import general._

import scala.util.{Failure, Success, Try}

case class RightMost [T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser[X]{

  def apply(cadena:String): Try[Resultado[X]] = parser1.apply(cadena).map {r => parser2(r.getCadenaRestante).get}

}