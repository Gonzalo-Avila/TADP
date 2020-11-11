package procesamiento_refactor
import general._
import parsers.{char, double, integer, string}
import scalafx.scene.paint.Color

import scala.util.{Success, Try}


object ParserTraslacion extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (((string("traslacion") <> char('[')
      .map { char => char.toString })
      .map { tupla => (List(tupla._1), List(tupla._2)) })
      <> ((double <|> integer.map {i => i.toDouble}).sepBy(string(","))
      .satisfies(lista => lista.size == 2)
      .map { lista => lista.map { numero => numero.toString } } <> char(']')
      .map { char => List(char.toString)})).apply(cadena)

    resultado.map { r =>

      val parametros = r.getElementoParseado._2._1.map { p => p.toDouble }

      val abrirParentesis = char('(')(r.getCadenaRestante)

      val contenidoTransformacion = ParserDeImagenes(abrirParentesis.get.getCadenaRestante)

      val cerrarParentesis = char(')')(contenidoTransformacion.get.getCadenaRestante)

      Resultado(Traslacion(parametros(0), parametros(1),contenidoTransformacion.get.getElementoParseado),cerrarParentesis.get.getCadenaRestante)
    }

  }

}