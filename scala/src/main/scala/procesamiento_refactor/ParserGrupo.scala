package procesamiento_refactor
import general._
import parsers.{char, double, integer, string}
import scalafx.scene.paint.Color

import scala.util.{Failure, Success, Try}


object ParserGrupo extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (string("grupo") <> char('(').map { char => char.toString }).apply(cadena)

    /*Try(
      resultado match {
        case Success(r) => {
          val parametros = r.getElementoParseado._2._1.map { p => p.toDouble }
          val contenidoTransformacion = ParserDeImagenes(r.getCadenaRestante)
          Resultado(Colores(Color.rgb(parametros(0).toInt, parametros(1).toInt, parametros(2).toInt),contenidoTransformacion.get.getElementoParseado),contenidoTransformacion.get.getCadenaRestante)

        }
      }
    )*/

    resultado.map { r =>
      val contenidoGrupo = (ParserDeImagenes.sepBy(char(',')) <|> ParserDeImagenes.map{elem => List(elem)})(r.getCadenaRestante)
      char(')')(contenidoGrupo.get.getCadenaRestante) match {
        case Success(res) => Resultado(Grupo(contenidoGrupo.get.getElementoParseado),res.getCadenaRestante)
        case Failure(_) => throw new RuntimeException("Falta un parentesis para cerrar el grupo")
      }

    }

    /*Try({
      val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
      Resultado(Colores(Color.rgb(parametros(0).toInt, parametros(1).toInt, parametros(2).toInt)), resultado.get.getCadenaRestante)
    })*/
  }

}
