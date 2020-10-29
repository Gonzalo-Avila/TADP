package parsersImagenes

import general.Resultado
import parsers.{char, string}

import scala.util.Try

object parserGrupo {

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {

    val parserInicio = (string("grupo") <> char('(').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }
    val parserParametros = armarParserDeParametros

    (parserInicio <> parserParametros).apply(cadena)
  }

}

