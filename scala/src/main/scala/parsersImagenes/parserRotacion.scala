package parsersImagenes

import parsers._

object parserRotacion extends parserTransformacion {

  override val transformacion = "rotacion"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).sepBy(string(",")).satisfies(lista => lista.size == 1).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}