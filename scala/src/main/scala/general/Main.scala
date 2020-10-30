package general

import parsersImagenes._
import scalafx.scene.paint.Color
import tadp.internal._

import scala.collection.mutable._
import scala.reflect.runtime.universe.{Tree, reify}

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

     println(anyImage.apply("rectangulo[0 @ 248, 40 @ 400]").get.getElementoParseado)
     println(anyImage.apply("rectangulo[0 @ 248, 40 @ 400]").get.getCadenaRestante)

     //println(parserTriangulo.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]"))
     println(anyImage.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getElementoParseado)
     println(anyImage.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getCadenaRestante)

     println(anyImage.apply("circulo[0 @ 248, 40]").get.getElementoParseado)
     println(anyImage.apply("circulo[0 @ 248, 40]").get.getCadenaRestante)

    /*TADPDrawingAdapter.forScreen {
      adapter =>
      adapter.beginColor(Color.rgb(50, 25, 100))
        .rectangle((200, 200), (300, 400))
        .beginRotate(360)
        .triangle((200, 200), (300, 400), (500, 500))
        .end()
        .end()
        .triangle((100, 100), (0, 150), (200, 300))

    }*/

    /*var adapter :TADPDrawingAdapter = ???
    adapter = adapter.rectangle((200, 200), (300, 400))
    adapter = adapter.beginRotate(360)
    adapter = adapter.triangle((200, 200), (300, 400), (500, 500))
    adapter = adapter.end()
    adapter = adapter.triangle((100, 100), (0, 150), (200, 300))*/



    /*var arbol = Nodo(Raiz)
    var nodo1 = Nodo(Colores(Color.rgb(240,3,4)))
    var nodo2 = Nodo(Triangulo((200,200),(300,400),(500,500)))
    var nodo3 = Nodo(Rectangulo((100,100),(200,200)))
    var nodo4 = Nodo(Circulo((500,40),90))
    arbol.agregarHijo(nodo1)
    arbol.agregarHijo(nodo4)
    nodo1.agregarHijo(nodo2)
    nodo1.agregarHijo(nodo3)*/

    val arbol = parserTexto.apply("escala[1, 1](grupo(color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400]),color[200, 70, 0](rectangulo[0 @ 0, 180 @ 150]),color[250, 250, 250](grupo(rectangulo[186 @ 0, 400 @ 150],rectangulo[186 @ 159, 400 @ 240],rectangulo[0 @ 159, 180 @ 240],rectangulo[45 @ 248, 180 @ 400],rectangulo[310 @ 248, 400 @ 400],rectangulo[186 @ 385, 305 @ 400])),color[30, 50, 130](rectangulo[186 @ 248, 305 @ 380]),color[250, 230, 0](rectangulo[0 @ 248, 40 @ 400])))")
    TADPDrawingAdapter.forScreen {a => arbol.preOrden(a)}

    //TADPDrawingAdapter.forScreen {a => a.triangle((200,50),(101,335),(299,335))}



  }
}

