package parsersImagenes

import parsers._

object parserEscala extends parserTransformacion {

  override val transformacion = "escala"
  override def armarParserDeParametros = {
    (integer.map {i => i.toDouble} <|> double).sepBy(string(", ")).satisfies(lista => lista.size == 2).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}