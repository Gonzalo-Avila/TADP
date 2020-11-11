package operaciones

import general._

import scala.util.{Failure, Success, Try}

case class ClausuraPKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{

  def apply(cadena:String): Try[Resultado[List[T]]] = (parserOriginal <> parserOriginal.*).map{r => List(r._1) ++ r._2}.apply(cadena)

}