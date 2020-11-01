package parsersImagenes

import parsers._

object ParserRotacion extends ParserTransformacion {

  override val transformacion = "rotacion"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).map { elem => List(elem.toString)}  <> char(']').map { char => List(char.toString)}
  }
}