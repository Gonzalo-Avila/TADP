package procesamiento_refactor

import general.{Parser, Resultado}
import parsers.{char, double, integer, string}

import scala.util.Try

object ParserTriangulo extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (((string("triangulo") <> char('[').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }) <> ((double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(",")).satisfies(lista => lista.size == 6).map { lista => lista.map { numero => numero.toString } }<> char(']').map { char => List(char.toString)})).apply(cadena)
    Try({
      val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
      Resultado(Triangulo((parametros(0), parametros(1)), (parametros(2), parametros(3)), (parametros(4), parametros(5))), resultado.get.getCadenaRestante)
    })
  }

}
