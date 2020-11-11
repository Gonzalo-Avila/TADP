package combinators

import general._

import scala.util.{Failure, Success, Try}


case class SeparatedBy[T,X](parserContenido: Parser[T], parserSeparador: Parser[X]) extends Parser[List[T]]{

  //Primero intenta matchear con un patron CADENA-SEPARADOR-CADENA, si no puede intenta machear con CADENA-FIN
  def apply (cadena:String): Try[Resultado[List[T]]] =
    ((parserContenido.map(r => List(r)) <> (parserSeparador ~> parserContenido).+).map{r => r._1 ++ r._2}
      <|> parserContenido.map{r => List(r)}.isFinal).apply(cadena)

  }