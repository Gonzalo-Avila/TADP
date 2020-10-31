package parsers

import general._

import scala.util.Try

object integer extends Parser [Int]{
//  def apply(cadena:String): Try[Resultado[Int]] = {
//    Try(
//      cadena match {
//        case cad if cad.head!= '-' && !cad.head.isDigit => throw new Exception();
//        case cad if !cad.tail.matches("^[0-9]+$") => throw new Exception();
//        case cad => new Resultado (cad.toInt,cad);
//      }
//    )
//  }
  
  def apply(cadena:String): Try[Resultado[Int]] = {
    val kleeneConDigit = digit.*
    val combinatoria =  (char('-') <|> digit).apply(cadena)
    
    /*Try(
      combinatoria match{
        case Failure(e) => throw new Exception()
        case Success(res) => {
          val flattenList = kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getElementoParseado.mkString
          new Resultado((combinatoria.get.getElementoParseado+flattenList).toInt,kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getCadenaRestante)
          }
       }
    )*/
    //TODO - CharParser('-').opt <> DigitParser.+ Ver como usar este
    Try({
     val flattenList = kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getElementoParseado.mkString
     Resultado((combinatoria.get.getElementoParseado + flattenList).toInt,kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getCadenaRestante)
    })
  }
}