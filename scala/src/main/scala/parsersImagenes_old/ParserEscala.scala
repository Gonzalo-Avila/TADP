package parsersImagenes_old

import parsers.{integer, _}

object ParserEscala extends ParserTransformacion {

  override val transformacion = "escala"
  override def armarParserDeParametros =
    (double <|> integer.map {i => i.toDouble}).sepBy(char(',')).satisfies(lista => lista.size == 2)
      .map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}

}