package parsersImagenes_old

import parsers._

object ParserTraslacion extends ParserTransformacion {

  override val transformacion = "traslacion"
  override def armarParserDeParametros =
    (double <|> integer.map {i => i.toDouble}).sepBy(char(',')).satisfies(lista => lista.size == 2)
      .map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
}