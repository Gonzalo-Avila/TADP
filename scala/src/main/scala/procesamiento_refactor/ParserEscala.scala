package procesamiento_refactor
import general._
import parsers.{char, double, integer, string}
import scalafx.scene.paint.Color

import scala.util.{Success, Try}


object ParserEscala extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (((string("escala") <> char('[').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }) <> ((double <|> integer.map {i => i.toDouble}).sepBy(string(",")).satisfies(lista => lista.size == 2).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)})).apply(cadena)

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
      val parametros = r.getElementoParseado._2._1.map { p => p.toDouble }

      val abrirParentesis = char('(')(r.getCadenaRestante)

      val contenidoTransformacion = ParserDeImagenes(abrirParentesis.get.getCadenaRestante)

      val cerrarParentesis = char(')')(contenidoTransformacion.get.getCadenaRestante)

      Resultado(Escala(parametros(0), parametros(1),contenidoTransformacion.get.getElementoParseado),cerrarParentesis.get.getCadenaRestante)
    }

    /*Try({
      val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
      Resultado(Colores(Color.rgb(parametros(0).toInt, parametros(1).toInt, parametros(2).toInt)), resultado.get.getCadenaRestante)
    })*/
  }

}
