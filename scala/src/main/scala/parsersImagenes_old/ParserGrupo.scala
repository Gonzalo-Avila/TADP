package parsersImagenes_old

import general._
import parsers.{char, string}

import scala.util.Try

object ParserGrupo extends Parser[(String, String)]{

  def apply(cadena: String): Try[Resultado[(String, String)]] = {

    (string("grupo") <> char('(').map { char => char.toString }).apply(cadena)

  }

}

