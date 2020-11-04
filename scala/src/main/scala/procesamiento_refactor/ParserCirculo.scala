package procesamiento_refactor

import parsers._
import general._
import combinators._

import scala.util.Try

object ParserCirculo extends Parser[Nodo] {

   def apply(cadena: String) = {
     val resultado = (((string("circulo") <> char('[').map { char => char.toString }).map { tupla => Tuple2(List(tupla._1), List(tupla._2)) }) <> ((double <|> integer.map {i => i.toDouble}).sepBy(string("@") <|> string(",")).satisfies(lista => lista.size == 3).map { lista => lista.map { numero => numero.toString } }<> char(']').map { char => List(char.toString)})).apply(cadena)

     Try({
       val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
       Resultado(Circulo((parametros(0), parametros(1)), parametros(2)), resultado.get.getCadenaRestante)
     })
  }

}