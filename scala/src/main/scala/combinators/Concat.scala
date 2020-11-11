package combinators

import general._

import scala.util.Try

case class Concat [T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser [(T,X)]{
  
  def apply(cadena:String): Try[Resultado[(T,X)]] =
    Try({
      val resultadoParser1 = parser1.apply(cadena)
      val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
      Resultado((resultadoParser1.get.getElementoParseado,
                    resultadoParser2.get.getElementoParseado),
                    resultadoParser2.get.getCadenaRestante)
    })

}