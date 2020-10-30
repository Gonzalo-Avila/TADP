package parsersImagenes

import parsers.{char, integer, string}

object parserTriangulo extends parserFigura {

  override val figura = "triangulo"
  override def armarParserDeParametros = {
    integer.sepBy(string(" @ ") <|> string(", ")).satisfies(lista => lista.size == 6).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString)}
  }
}

