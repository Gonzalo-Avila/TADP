package parsersImagenes_old

import combinators.Concat
import general._
import parsers.{char, string}

import scala.util.Try

abstract class ParserFigura extends Parser[((List[String], List[String]), (List[String], List[String]))] {
  val figura: String

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {

    val parserInicio = (string(figura) <> char('[').map { char => char.toString }).map { tupla => (List(tupla._1), List(tupla._2)) }
    val parserParametros = armarParserDeParametros

    (parserInicio <> parserParametros).apply(cadena)
  }

  def armarParserDeParametros: Concat[List[String],List[String]]

}

