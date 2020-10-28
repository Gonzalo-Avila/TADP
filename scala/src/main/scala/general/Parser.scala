package general

import scala.util.{Try,Success,Failure}
import combinators._
import operaciones._


trait Parser[T]{
  
  def apply(cadena:String): Try[Resultado[T]]
  
  //Combinators
  def <|> (otroParser:Parser[T]) = new Or(this,otroParser)
  def <>  (otroParser:Parser[T]) = new Concat(this,otroParser)
  def ~> [X](otroParser:Parser[X]) = new RightMost(this,otroParser)
  def <~ [X](otroParser:Parser[X]) = new LeftMost(this,otroParser)
  def sepBy[X](otroParser:Parser[X]) = new SeparatedBy(this, otroParser)

  //Operaciones
  def satisfies (condicion: T => Boolean) = new ParserCondicional(this, condicion)
  def opt = new ParserOpcional(this)
  def * = new ClausuraKleene(this)
  def + = new ClausuraPKleene(this)
  def map [X](funcion: T => X) = new ParserMap(this, funcion)
}