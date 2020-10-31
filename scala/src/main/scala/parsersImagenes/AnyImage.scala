package parsersImagenes

import general._

import scala.util.Try

object AnyImage {

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {
     (ParserCirculo <|> ParserRectangulo <|> ParserTriangulo).apply(cadena)
  }

}
