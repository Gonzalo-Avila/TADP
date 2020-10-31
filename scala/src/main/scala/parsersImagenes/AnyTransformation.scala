package parsersImagenes

import general.Resultado

import scala.util.Try

object AnyTransformation {

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {
    (ParserColor <|> ParserEscala <|> ParserRotacion <|> ParserTraslacion).apply(cadena)
  }

}
