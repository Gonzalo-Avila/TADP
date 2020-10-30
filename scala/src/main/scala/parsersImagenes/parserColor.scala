package parsersImagenes
import general._
import parsers.{char, integer, string}

object parserColor extends parserTransformacion {

  override val transformacion = "color"
  override def armarParserDeParametros = {
    integer.sepBy(string(", ")).satisfies(lista => lista.size == 3).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}
