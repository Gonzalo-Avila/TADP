package procesamiento_refactor
import general._
import parsers.{char, double, integer, string}
import scalafx.scene.paint.Color

import scala.util.{Success, Try}


object ParserRotacion extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (((string("rotacion") <> char('[')
      .map { char => char.toString })
      .map { tupla => (List(tupla._1), List(tupla._2)) })
      <> ((double <|> integer.map {i => i.toDouble})
      .map { elem => List(elem.toString)}  <> char(']')
      .map { char => List(char.toString)})).apply(cadena)

    resultado.map { r =>
      val parametros = r.getElementoParseado._2._1.map { p => p.toDouble }

      val abrirParentesis = char('(')(r.getCadenaRestante)

      val contenidoTransformacion = ParserDeImagenes(abrirParentesis.get.getCadenaRestante)

      val cerrarParentesis = char(')')(contenidoTransformacion.get.getCadenaRestante)

      Resultado(Rotacion(parametros(0),contenidoTransformacion.get.getElementoParseado),cerrarParentesis.get.getCadenaRestante)
    }

  }

}