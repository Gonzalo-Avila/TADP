package general

//import procesamiento._
import procesamiento_refactor._
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

    val imagen1 = ProcesadorTexto.apply("grupo(\n\tcolor[200, 200, 200](rectangulo[100 @ 100, 200 @ 200]),\n\tcolor[200, 200, 200](circulo[100 @ 300, 150])\n)")
    val imagen2 = ProcesadorTexto.apply("rotacion[300](\n\trotacion[10](\n\t\trectangulo[100 @ 200, 300 @ 400]\n\t)\n)")
    val imagen3 = ProcesadorTexto.apply("escala[2, 3](\n      escala[3, 5](\n\t     circulo[0 @ 5, 10]\n      )\n)")
    val imagen4 = ProcesadorTexto.apply("traslacion[100, 5](\n\ttraslacion[20, 10](\n\t\tcirculo[0 @ 5, 10]\n)\n)")

    println ("Cadena original:")
    imagen1.imprimir
    println
    println("Cadena simplificada:")
    imagen1.simplificar.imprimir
    println
    println
    println ("Cadena original:")
    imagen2.imprimir
    println
    println("Cadena simplificada:")
    imagen2.simplificar.imprimir
    println
    println
    println ("Cadena original:")
    imagen3.imprimir
    println
    println("Cadena simplificada:")
    imagen3.simplificar.imprimir
    println
    println
    println ("Cadena original:")
    imagen4.imprimir
    println
    println("Cadena simplificada:")
    imagen4.simplificar.imprimir


    //cosa.simplificar.imprimir
    //TADPDrawingAdapter.forScreen {a => cosa.aplicar(a)}


    //val cosaRancia = SimplificadorAST.simplificarArbol(cosa)
    //cosaRancia.imprimir()
    TADPDrawingAdapter.forInteractiveScreen {(imageDescription, adapter) =>  ProcesadorTexto.apply(imageDescription).simplificar.aplicar(adapter)}

    /*TADPDrawingAdapter.forScreen { adapter =>
      adapter.rectangle((200, 200), (300, 400))
        .rectangle((200, 300), (500, 700))
        .circle((100, 200), 30)
    }*/

    /*
    val lines = scala.io.Source.fromFile("C:\\mes.txt").mkString
    val carpinchoBostero = ProcesadorTexto.apply(lines)
    */

    //TADPDrawingAdapter.forScreen {a => carpinchoBostero.preOrden(a)}


  }
}

