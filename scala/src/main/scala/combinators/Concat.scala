package combinators

import general._

import scala.util.Try

class Concat [T,X](parser1:Parser[T], parser2:Parser[X]) extends Parser [Tuple2[T,X]]{
  
  def apply(cadena:String): Try[Resultado[Tuple2[T,X]]] = {
//      val resultadoParser1 = parser1.apply(cadena)
//      resultadoParser1 match {
//        case Failure(errorEnParser1) => throw new Exception();
//        case Success(resultado1) => {
//          val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
//          resultadoParser2 match {
//            case Failure(errorEnParser2) => throw new Exception()
//            case Success(resultado2) => 
//              Try(new Resultado((resultadoParser1.get.getElementoParseado,
//                  resultadoParser2.get.getElementoParseado),
//                  resultadoParser2.get.getCadenaRestante))
//          }
//        }
//    }
    
    Try({
      val resultadoParser1 = parser1.apply(cadena)
      val resultadoParser2 = parser2.apply(resultadoParser1.get.getCadenaRestante)
      
      Resultado((resultadoParser1.get.getElementoParseado,
                    resultadoParser2.get.getElementoParseado),
                    resultadoParser2.get.getCadenaRestante)
    })
  }
}