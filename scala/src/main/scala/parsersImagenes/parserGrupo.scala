package parsersImagenes

import general._
import parsers.{char, string}

import scala.util.Try

object parserGrupo extends Parser[Tuple2[String, String]]{

  def apply(cadena: String): Try[Resultado[Tuple2[String, String]]] = {

    (string("grupo") <> char('(').map { char => char.toString }).apply(cadena)

  }

}

