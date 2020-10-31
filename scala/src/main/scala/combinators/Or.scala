package combinators

import general._

import scala.util.{Failure, Success, Try}

class Or [T](parser1:Parser[T], parser2:Parser[T]) extends Parser[T] {
 
  def apply (cadena:String): Try[Resultado[T]] = {
      
      val resultadoParser1 = parser1(cadena)
      /*resultadoParser1 match {
        case Success(resultado) => resultadoParser1
        case Failure(error) => parser2.apply(cadena)
      }*/
      resultadoParser1.recoverWith {_ => parser2(cadena)}
   }
}