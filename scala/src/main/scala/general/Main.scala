package general

//import procesamiento.{ProcesadorTexto, SimplificadorAST}
import procesamiento_refactor._
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

    val cosa = ProcesadorTexto2.apply("grupo(\n\tcolor[200, 200, 200](rectangulo[100 @ 100, 200 @ 200]),\n\tcolor[200, 200, 200](circulo[100 @ 300, 150])\n)")
    cosa.imprimir
    println
    cosa.simplificar.imprimir
    //TADPDrawingAdapter.forScreen {a => cosa.aplicar(a)}


    //val cosaRancia = SimplificadorAST.simplificarArbol(cosa)
    //cosaRancia.imprimir()
    //TADPDrawingAdapter.forInteractiveScreen {(imageDescription, adapter) =>  ProcesadorTexto2.apply(imageDescription).aplicar(adapter)}

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

