package procesamiento_refactor

import parsers._
import general._
import combinators._

import scala.util.Try

object ParserRectangulo extends Parser[Nodo] {

  def apply(cadena: String) = {
    val resultado = (((string("rectangulo") <> char('[')
      .map { char => char.toString }).map { tupla => (List(tupla._1), List(tupla._2)) })
      <> ((double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(","))
      .satisfies(lista => lista.size == 4)
      .map { lista => lista.map { numero => numero.toString } }<> char(']')
      .map { char => List(char.toString)})).apply(cadena)

    Try({
      val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
      Resultado(Rectangulo((parametros(0), parametros(1)), (parametros(2), (parametros(3)))), resultado.get.getCadenaRestante)
    })
  }

}