package parsersImagenes

import combinators.Concat
import general._
import parsers.{char, string}

import scala.util.Try

abstract class parserTransformacion extends Parser[((List[String], List[String]), (List[String], List[String]))] {
  val transformacion: String

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {

    val parserInicio = (string(transformacion) <> char('[').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }
    val parserParametros = armarParserDeParametros

    (parserInicio <> parserParametros).apply(cadena)
  }

  def armarParserDeParametros: Concat[List[String],List[String]]
}

