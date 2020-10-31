package parsersImagenes

import parsers.{char, double, integer, string}

object ParserTriangulo extends ParserFigura {

  override val figura = "triangulo"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(",")).satisfies(lista => lista.size == 6).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}

