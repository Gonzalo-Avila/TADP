package general

import parsersImagenes._
import scalafx.scene.paint.Color
import tadp.internal._

object Main{
  
  
  def main(Args: Array[String]){

     // assert(anyChar.apply("Hola")==Success(new Resultado('H',"ola")));
     //assert(new char('c').apply("colombia") == Success (('c',"olombia")));
     //assert(new string("Hola").apply("Holamundo") == Success (("Hola","mundo")));
     //assert(digit.apply("1qwe21wq")==Success(('1',"qwe21wq")));
     //assert(new <|>(anyChar, new char('c')).apply("Hola") == Success (('H',"ola")));
     //assert(new <|>(new char('c'), new char('a')).apply("aloha") == Success (('a',"loha")));
     /*println(anyChar.apply("Hola").get.getElementoParseado + " " + anyChar.apply("Hola").get.getCadenaRestante);
     
     val comp = new char('c') <|> anyChar;
     val comp2 = anyChar <> comp;
     val resultado = comp2("caca").get
     println(resultado.getElementoParseado + " " + resultado.getCadenaRestante);
     
     val cond = (new char('c')).satisfies(a => a == 'c')
     
     println(cond.apply("caca").get.getElementoParseado);
     
     val opcional = (new char('c')).opt
     println(opcional.apply("caca").get.getElementoParseado + " " + opcional.apply("baca").get.getCadenaRestante);
     
     
     val talVezIn = string("in").opt
    // precedencia parsea exitosamente las palabras "infija" y "fija"
     //val precedencia = talVezIn <> string("fija")*/
     
     /*val kleene = char('a').+
     val res = kleene.apply("axqweqwe")
     println(res.get.getElementoParseado, res.get.getCadenaRestante)
     
     val rightMost = char('a') ~> anyChar
     val rightMostAplicado = rightMost.apply("aranderla")
     println("(" + rightMostAplicado.get.getElementoParseado + "," + rightMostAplicado.get.getCadenaRestante + ")") 
      
     val leftMost = char('a') <~ anyChar
     val leftMostAplicado = leftMost.apply("aranderla")
     println("(" + leftMostAplicado.get.getElementoParseado + "," + leftMostAplicado.get.getCadenaRestante + ")")*/
     
     //val map = string("asd").map(c => c.toUpperCase())
     //val mapAplicado = map.apply("asdasdasd");
     //println("(" + mapAplicado.get.getElementoParseado + "," + mapAplicado.get.getCadenaRestante + ")")
     
     //val c = char('-').map(c => List(c)) //=> devuelve una lista con el char en una lista, o error
     //val d = digit.+                     //=> devuelve todos los elementos en una lista, o error 
     //val f = c <> d
     //val p = (c <> d) <|> (digit.* <> digit.+)

     //println(double.apply("1.3").get.getElementoParseado)
    
     /*val sep = anyChar.sepBy(char('-'))
     val sepAplicado = sep.apply("4-");
     println("(" + sepAplicado.get.getElementoParseado + "," + sepAplicado.get.getCadenaRestante + ")")
     
     val sep2 = integer.sepBy(char('-'))
     val sepAplicado2 = sep2.apply("4321-asd");
     println("(" + sepAplicado2.get.getElementoParseado + "," + sepAplicado2.get.getCadenaRestante + ")")
     
     val sep3 = anyChar.sepBy(char('#'))
     val sepAplicado3 = sep3.apply("a#sd#qwe.");
     println("(" + sepAplicado3.get.getElementoParseado + "," + sepAplicado3.get.getCadenaRestante + ")")
     */

     /*val leftMost = char('b') ~> string("ra")
     val leftMostAplicado = leftMost.apply("aranderla")
     println(leftMostAplicado)*/
     //println("(" + leftMostAplicado.get.getElementoParseado + "," + leftMostAplicado.get.getCadenaRestante + ")")

     println(parserRectangulo.apply("rectangulo[0 @ 248, 40 @ 400]").get.getElementoParseado)
     println(parserRectangulo.apply("rectangulo[0 @ 248, 40 @ 400]").get.getCadenaRestante)

     //println(parserTriangulo.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]"))
     println(parserTriangulo.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getElementoParseado)
     println(parserTriangulo.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getCadenaRestante)

     println(parserCirculo.apply("circulo[0 @ 248, 40]").get.getElementoParseado)
     println(parserCirculo.apply("circulo[0 @ 248, 40]").get.getCadenaRestante)

    TADPDrawingAdapter.forScreen {
      adapter =>
      adapter.beginColor(Color.rgb(50, 25, 100))
        .rectangle((200, 200), (300, 400))
        .beginRotate(360)
        .triangle((200, 200), (300, 400), (500, 500))
        .end()
        .end()
        .triangle((100, 100), (0, 150), (200, 300))
    }

  }
}