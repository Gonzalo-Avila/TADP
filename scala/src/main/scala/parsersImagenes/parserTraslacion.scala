package parsersImagenes

import parsers._

object parserTraslacion extends parserTransformacion {

  override val transformacion = "traslacion"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).sepBy(string(",")).satisfies(lista => lista.size == 2).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}