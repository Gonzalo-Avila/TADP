package operaciones

import general.{Parser, Resultado}

import scala.util.Try

class ParserFinal [T](parserOriginal:Parser[T]) extends Parser[T]{
  def apply(cadena:String): Try[Resultado[T]] = parserOriginal.apply(cadena).filter{r => r.getCadenaRestante.isEmpty}
}
