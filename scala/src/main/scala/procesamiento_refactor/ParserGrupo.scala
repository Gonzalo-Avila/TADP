package procesamiento_refactor
import general._
import parsers.{char, double, integer, string}
import scalafx.scene.paint.Color

import scala.util.{Failure, Success, Try}


object ParserGrupo extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (string("grupo") <> char('(').map { char => char.toString }).apply(cadena)

    resultado.map { r =>
      val contenidoGrupo = (ParserDeImagenes.sepBy(char(',')) <|> ParserDeImagenes.map{elem => List(elem)})(r.getCadenaRestante)
      char(')')(contenidoGrupo.get.getCadenaRestante) match {
        case Success(res) => Resultado(Grupo(contenidoGrupo.get.getElementoParseado),res.getCadenaRestante)
        case Failure(_) => throw new RuntimeException("Falta un parentesis para cerrar el grupo")
      }

    }

  }

}
