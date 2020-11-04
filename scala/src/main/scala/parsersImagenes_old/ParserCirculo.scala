package parsersImagenes_old

import parsers.{char, double, integer, string}

object ParserCirculo extends ParserFigura {

  override val figura = "circulo"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(",")).satisfies(lista => lista.size == 3).map { lista => lista.map { numero => numero.toString } }<> char(']').map { char => List(char.toString)}
  }

}