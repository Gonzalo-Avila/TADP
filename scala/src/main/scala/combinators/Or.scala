package combinators

import general._

import scala.util.{Failure, Success, Try}

case class Or [T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T] {

  def apply(cadena: String): Try[Resultado[T]] = parser1(cadena).recoverWith { _ => parser2(cadena) }

}