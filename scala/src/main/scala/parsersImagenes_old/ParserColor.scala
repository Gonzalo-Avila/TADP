package parsersImagenes_old
import general._
import parsers.{char, double, integer, string}

object ParserColor extends ParserTransformacion {

  override val transformacion = "color"
  override def armarParserDeParametros =
    (double <|> integer.map {i => i.toDouble}).sepBy(string(",")).satisfies(lista => lista.size == 3)
      .map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}

}
