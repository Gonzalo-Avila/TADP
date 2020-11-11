package general

import combinators._
import operaciones._

import scala.util.Try


trait Parser[T]{
  
  def apply(cadena:String): Try[Resultado[T]]
  
  //Combinators
  def <|> (otroParser:Parser[T]) = Or(this,otroParser)
  def <>  [X](otroParser:Parser[X]) = Concat(this,otroParser)
  def ~> [X](otroParser:Parser[X]) = RightMost(this,otroParser)
  def <~ [X](otroParser:Parser[X]) = LeftMost(this,otroParser)
  def sepBy[X](otroParser:Parser[X]) = SeparatedBy(this, otroParser)

  //Operaciones
  def satisfies (condicion: T => Boolean) = ParserCondicional(this, condicion)
  def opt = ParserOpcional(this)
  def * = ClausuraKleene(this)
  def + = ClausuraPKleene(this)
  def map [X](funcion: T => X) = ParserMap(this, funcion)

  //Retorna el resultado del parser original solo si con el termina la cadena
  def isFinal = ParserFinal(this)

}