package parsersImagenes

import general.Resultado

import scala.util.Try

object anyTransformation {

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {
    (parserColor <|> parserEscala <|> parserRotacion <|> parserTraslacion).apply(cadena)
  }

}
