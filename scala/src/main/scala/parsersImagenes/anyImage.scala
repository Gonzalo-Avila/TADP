package parsersImagenes

import general._

import scala.util.Try

object anyImage {

  def apply(cadena: String): Try[Resultado[((List[String], List[String]), (List[String], List[String]))]] = {
     (parserCirculo <|> parserRectangulo <|> parserTriangulo).apply(cadena)
  }

}
