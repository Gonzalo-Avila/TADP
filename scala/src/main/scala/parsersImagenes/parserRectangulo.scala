package parsersImagenes

import general.Resultado
import parsers.{char, integer, string}

import scala.util.Try

object parserRectangulo {
  def apply(cadena: String): Try[Resultado[((List[String],List[String]),(List[String],List[String]))]] = {

    val parserInicio = (string("rectangulo") <> char('[').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }
    val parserParametros = integer.sepBy(string(" @ ") <|> string(", ")).satisfies(lista => lista.size == 4).map { lista => lista.map { numero => numero.toString } } <> char(']').map { char => List(char.toString) }

    return (parserInicio <> parserParametros).apply(cadena)
  }
}