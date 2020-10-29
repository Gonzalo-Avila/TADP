package parsersImagenes

import parsers.{char, integer, string}

object parserRectangulo extends parserImagen {

  override val figura = "rectangulo"
  override def armarParserDeParametros = {
    integer.sepBy(string(" @ ") <|> string(", ")).satisfies(lista => lista.size == 4).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString) }
  }
}