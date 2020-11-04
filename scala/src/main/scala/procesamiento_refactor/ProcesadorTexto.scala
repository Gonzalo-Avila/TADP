package procesamiento_refactor

import general._
import parsers._
import scalafx.scene.paint.Color

import scala.util.{Failure, Success, Try}

object ProcesadorTexto {

  def apply(cadena: String): Nodo = {
    val cadenaLimpia = cadena.filterNot(caracter => caracter.isWhitespace || caracter == '\n' || caracter == '\t')

    ParserDeImagenes.isFinal(cadenaLimpia) match {
      case Success(raizArbolSintactico) => raizArbolSintactico.getElementoParseado
      case Failure(_) => throw new RuntimeException("Cadena invalida")
    }
  }

}

object ParserDeImagenes extends Parser[Nodo]{
  def apply(cadena: String): Try[Resultado[Nodo]] = {
    (ParserFigura <|> ParserTransformacion <|> ParserGrupo).apply(cadena)
  }
}

object ParserFigura extends Parser[Nodo]{
  def apply(cadena: String): Try[Resultado[Nodo]] = (ParserRectangulo <|> ParserCirculo <|> ParserTriangulo).apply(cadena)
}


object ParserTransformacion extends Parser[Nodo]{
  def apply(cadena: String): Try[Resultado[Nodo]] = (ParserColor <|> ParserEscala <|> ParserTraslacion <|> ParserRotacion).apply(cadena)

}

