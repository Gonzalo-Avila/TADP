package parsersImagenes

import parsers.{char, double, integer, string}

object parserRectangulo extends parserFigura {

  override val figura = "rectangulo"
  override def armarParserDeParametros = {
    (double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(",")).satisfies(lista => lista.size == 4).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}


