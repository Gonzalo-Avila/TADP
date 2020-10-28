package combinators

import general._

import scala.util.{Failure, Success, Try}

class RightMost [T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser[X]{
     
    def apply(cadena:String): Try[Resultado[X]] = {
          val resultadoParser1 = parser1.apply(cadena)
          resultadoParser1 match {
            case Failure(errorEnParser1) => Try(throw new Exception())
            case Success(resultado1) => {
              val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
              resultadoParser2 match {
                case Failure(errorEnParser2) => Try(throw new Exception())
                case Success(resultado2) => 
                  resultadoParser2
              }
             }
          }
      }
}