import scala.io.Source
import scala.util.{Try,Success,Failure}


object Main{
  
  def <-> (parser1: Parser, parser2: Parser) = parser1;
  def asd (a:Int)(b:Int) = a+b;
  
  def main(Args: Array[String]){
    
     assert(anyChar.apply("Hola")==Success(('H',"ola")));
     assert(new char('c').apply("colombia") == Success (('c',"olombia")));
     assert(new string("Hola").apply("Holamundo") == Success (("Hola","mundo")));
     assert(digit.apply("1qwe21wq")==Success(('1',"qwe21wq")));
     /*assert(new <|>(anyChar, new char('c')).apply("Hola") == Success (('H',"ola")));
     assert(new <|>(new char('c'), new char('a')).apply("aloha") == Success (('a',"loha")));*/
     println("Pasaron las pruebas");
     
     val comp = anyChar.<|>(new char('c'))_;
     println(comp("caca"));

  }
}

