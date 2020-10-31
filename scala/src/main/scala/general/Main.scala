package general

import parsers.double
import parsersImagenes._
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

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

    //println(anyImage.apply("rectangulo[0 @ 248, 40 @ 400]").get.getElementoParseado)
    //println(anyImage.apply("rectangulo[0 @ 248, 40 @ 400]").get.getCadenaRestante)

    //println(parserTriangulo.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]"))
    //println(anyImage.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getElementoParseado)
    //println(anyImage.apply("triangulo[0 @ 248, 40 @ 400, 40 @ 400]").get.getCadenaRestante)

    //println(anyImage.apply("circulo[0 @ 248, 40]").get.getElementoParseado)
    //println(anyImage.apply("circulo[0 @ 248, 40]").get.getCadenaRestante)

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

    //    val arbol = parserTexto.apply("escala[1, 1](grupo(color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400]),color[200, 70, 0](rectangulo[0 @ 0, 180 @ 150]),color[250, 250, 250](grupo(rectangulo[186 @ 0, 400 @ 150],rectangulo[186 @ 159, 400 @ 240],rectangulo[0 @ 159, 180 @ 240],rectangulo[45 @ 248, 180 @ 400],rectangulo[310 @ 248, 400 @ 400],rectangulo[186 @ 385, 305 @ 400])),color[30, 50, 130](rectangulo[186 @ 248, 305 @ 380]),color[250, 230, 0](rectangulo[0 @ 248, 40 @ 400])))")
    //val arbol = parserTexto.apply("grupo(triangulo[200 @ 50, 101 @ 335, 299 @ 335],circulo[200 @ 350, 100])")
    //TADPDrawingAdapter.forScreen {a => arbol.preOrden(a)}

    //    println('h'.toUpper + "ola".toUpperCase())
    //    TADPDrawingAdapter.forScreen {a => a.triangle((200,50),(101,335),(299,335))}

    //val arteAbstracto = parserTexto.apply("escala[1.5, 1.5](\n grupo(\n   color[0, 0, 0](\n \trectangulo[0 @ 0, 400 @ 400]\n   ),\n   color[200, 70, 0](\n \trectangulo[0 @ 0, 180 @ 150]\n   ),\n   color[250, 250, 250](\n \tgrupo(\n   \trectangulo[186 @ 0, 400 @ 150],\n   \trectangulo[186 @ 159, 400 @ 240],\n   \trectangulo[0 @ 159, 180 @ 240],\n   \trectangulo[45 @ 248, 180 @ 400],\n   \trectangulo[310 @ 248, 400 @ 400],\n   \trectangulo[186 @ 385, 305 @ 400]\n\t)\n   ),\n   color[30, 50, 130](\n   \trectangulo[186 @ 248, 305 @ 380]\n   ),\n   color[250, 230, 0](\n   \trectangulo[0 @ 248, 40 @ 400]\n   )\n )\n)")
    val corazon = parserTexto.apply("color[200, 0, 0](\n\tgrupo(\n\t\tescala[1, 0.8](\n\t\t\tgrupo(\n\t\t\t\tcirculo[210 @ 250, 100],\n\t\t\t\tcirculo[390 @ 250, 100]\n\t\t\t)\n\t\t),\n\t\trectangulo[200 @ 170, 400 @ 300],\n\t\ttriangulo[113 @ 225, 487 @ 225, 300 @ 450]\n\t)\n)")
    //val pepita = parserTexto.apply("grupo(\n\tcolor[0, 0, 80](\n\t\tgrupo(\n\t\t\ttriangulo[50 @ 400, 250 @ 400, 200 @ 420],\n\t\t\ttriangulo[50 @ 440, 250 @ 440, 200 @ 420]\n\t\t)\n\t),\n\tcolor[150, 150, 150](\n\t\ttriangulo[200 @ 420, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[180, 180, 160](\n\t\ttriangulo[330 @ 460, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[200, 200, 180](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[400 @ 430, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[330 @ 460, 250 @ 400, 330 @ 370]\n\t\t)\n\t),\n\tgrupo(\n\t\tcolor[150, 0, 0](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 400 @ 430]\n\t\t\t)\n\t\t),\n\t\tcolor[100, 0, 0](triangulo[420 @ 420, 420 @ 400, 400 @ 430]),\n\t\tcolor[0, 0, 60](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[420 @ 400, 400 @ 400, 400 @ 430],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 300 @ 350]\n\t\t\t)\n\t\t),\n\t\tcolor[150, 150, 0](triangulo[440 @ 410, 440 @ 400, 460 @ 400])\n\t),\n\tcolor[0, 0, 60](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 300, 250 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 300, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[360 @ 280, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 100 @ 220, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 360 @ 280, 330 @ 370]\n\t\t)\n\t)\n)")
    //val red = parserTexto.apply("color[100, 100, 100](\n  grupo(\n    color[0, 0, 0](\n      grupo(\n        color[201, 176, 55](\n          triangulo[0 @ 0, 650 @ 0, 0 @ 750]\n        ),\n        color[215, 215, 215](\n          triangulo[650 @ 750, 650 @ 0, 0 @ 750]\n        ),\n        color[255, 255, 255](\n          grupo(\n            rectangulo[230 @ 150, 350 @ 180],\n            rectangulo[110 @ 150, 470 @ 390]\n          )\n        ),\n        color[255, 0, 0](\n          grupo(\n            rectangulo[170 @ 60, 410 @ 150],\n            rectangulo[350 @ 60, 380 @ 180],\n            rectangulo[200 @ 60, 230 @ 180],\n            rectangulo[260 @ 300, 320 @ 330],\n            rectangulo[170 @ 390, 410 @ 480]\n          )\n        ),\n        rectangulo[200 @ 180, 380 @ 210],\n        rectangulo[230 @ 240, 260 @ 300],\n        rectangulo[320 @ 240, 350 @ 300],\n        rectangulo[200 @ 30, 380 @ 60],\n        rectangulo[170 @ 60, 200 @ 90],\n        rectangulo[380 @ 60, 410 @ 90],\n        rectangulo[140 @ 90, 170 @ 150],\n        rectangulo[410 @ 90, 440 @ 150],\n        rectangulo[110 @ 150, 200 @ 180],\n        rectangulo[110 @ 180, 170 @ 210],\n        rectangulo[140 @ 210, 170 @ 240],\n        rectangulo[80 @ 210, 110 @ 270],\n        rectangulo[110 @ 270, 170 @ 330],\n        rectangulo[110 @ 300, 200 @ 330],\n        rectangulo[80 @ 330, 110 @ 390],\n        rectangulo[110 @ 390, 200 @ 420],\n        rectangulo[140 @ 420, 170 @ 480],\n        rectangulo[200 @ 420, 260 @ 450],\n        rectangulo[320 @ 420, 380 @ 450],\n        rectangulo[260 @ 390, 320 @ 420],\n        rectangulo[170 @ 330, 410 @ 390],\n        rectangulo[170 @ 480, 260 @ 510],\n        rectangulo[260 @ 450, 320 @ 480],\n        rectangulo[320 @ 480, 410 @ 510],\n        rectangulo[410 @ 420, 440 @ 480],\n        rectangulo[380 @ 390, 470 @ 420],\n        rectangulo[470 @ 330, 500 @ 390],\n        rectangulo[380 @ 300, 470 @ 330],\n        rectangulo[410 @ 270, 470 @ 330],\n        rectangulo[470 @ 210, 500 @ 270],\n        rectangulo[410 @ 210, 440 @ 240],\n        rectangulo[410 @ 180, 470 @ 210],\n        rectangulo[380 @ 150, 470 @ 180],\n        rectangulo[380 @ 150, 470 @ 180]\n      )\n    )\n  )\n)")
    corazon.imprimir()
    /*val resultado = double.apply("0.8")
    println(resultado.get.getElementoParseado)*/

    val resultado = parserEscala.apply("escala[1,0.8]")
    println(resultado.get.getElementoParseado)

    TADPDrawingAdapter.forScreen {a => corazon.preOrden(a)}

    /*TADPDrawingAdapter.forScreen {a =>
      a
        .beginColor(Color.rgb(200,0,0))
        .beginScale(1,0.8)
        .circle((210,250),100)
        .circle((390,250),100)
        .end()
        .rectangle((200,170),(400,300))
        .triangle((113,225),(487,225),(300,450))
        .end
    }*/

    //println(parserTexto.limpiarEspacios("\n    \n c   hola"))

    /*val murcielago = parserTexto.apply("grupo(\n\tescala[1.2, 1.2](\n\t\tgrupo(\n\t\t\tcolor[0, 0, 80](rectangulo[0 @ 0, 600 @ 700]),\n\t\t\tcolor[255, 255, 120](circulo[80 @ 80, 50]),\n\t\t\tcolor[0, 0, 80](circulo[95 @ 80, 40])\n\t\t)\n\t),\n\tcolor[50, 50, 50](triangulo[80 @ 270, 520 @ 270, 300 @ 690]),\n\tcolor[80, 80, 80](triangulo[80 @ 270, 170 @ 270, 300 @ 690]),\n\tcolor[100, 100, 100](triangulo[200 @ 200, 400 @ 200, 300 @ 150]),\n\tcolor[100, 100, 100](triangulo[200 @ 200, 400 @ 200, 300 @ 400]),\n\tcolor[150, 150, 150](triangulo[400 @ 200, 300 @ 400, 420 @ 320]),\n\tcolor[150, 150, 150](triangulo[300 @ 400, 200 @ 200, 180 @ 320]),\n\tcolor[100, 100, 100](triangulo[150 @ 280, 200 @ 200, 180 @ 320]),\n\tcolor[100, 100, 100](triangulo[150 @ 280, 200 @ 200, 150 @ 120]),\n\tcolor[100, 100, 100](triangulo[400 @ 200, 450 @ 300, 420 @ 320]),\n\tcolor[100, 100, 100](triangulo[400 @ 200, 450 @ 300, 450 @ 120]),\n\tgrupo(\n\t\tescala[0.4, 1](\n\t\t\tcolor[0, 0, 0](\n\t\t\t\tgrupo(\n\t\t\t\t\tcirculo[970 @ 270, 25],\n\t\t\t\t\tcirculo[530 @ 270, 25]\n\t\t\t\t)\n\t\t\t)\n\t\t)\n\t)\n)")*/

    //TADPDrawingAdapter.forScreen { a => murcielago.preOrden(a) }

  }
}

