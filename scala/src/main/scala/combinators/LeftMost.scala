package combinators

import general._

import scala.util.{Failure, Success, Try}

class LeftMost[T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser[T]{

   //TODO - Reemplazar PM por map - DONE

  def apply(cadena:String): Try[Resultado[T]] =
    parser1.apply(cadena).map {r =>
      Resultado(r.getElementoParseado,parser2.apply(r.getCadenaRestante).get.getCadenaRestante)}

    /*def apply(cadena:String): Try[Resultado[T]] = {
            val resultadoParser1 = parser1.apply(cadena)
            Try(  
            resultadoParser1 match {
                case Failure(errorEnParser1) => throw new Exception()
                case Success(resultado1) => {
                  val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
                  resultadoParser2 match {
                    case Failure(errorEnParser2) => throw new Exception()
                    case Success(resultado2) => 
                       Resultado (resultadoParser1.get.getElementoParseado, resultadoParser2.get.getCadenaRestante)
                  }
                }
            }
            )
    }*/
}