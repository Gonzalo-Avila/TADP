package procesamiento_refactor

import general._
import parsers._
import scalafx.scene.paint.Color

import scala.util.{Failure, Success, Try}

object ProcesadorTexto2 {

  def apply(cadena: String): Nodo = {
    val cadenaLimpia = cadena.filterNot(caracter => caracter.isWhitespace || caracter == '\n' || caracter == '\t')

    ParserDeImagenes(cadenaLimpia) match {
      case Success(raizArbolSintactico) if !raizArbolSintactico.getCadenaRestante.isEmpty => throw new RuntimeException("Cadena invalida")
      case Success(raizArbolSintactico) => raizArbolSintactico.getElementoParseado
      case Failure(exception) => throw exception
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

