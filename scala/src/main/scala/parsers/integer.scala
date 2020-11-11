package parsers

import general._

import scala.util.Try

object integer extends Parser [Int]{
  
  def apply(cadena:String): Try[Resultado[Int]] = {
    val kleeneConDigit = digit.*
    val combinatoria =  (char('-') <|> digit).apply(cadena)

    //TODO - CharParser('-').opt <> DigitParser.+ Ver como usar este
    Try({
     val flattenList = kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getElementoParseado.mkString
     Resultado((combinatoria.get.getElementoParseado + flattenList).toInt,kleeneConDigit.apply(combinatoria.get.getCadenaRestante).get.getCadenaRestante)
    })
  }

}