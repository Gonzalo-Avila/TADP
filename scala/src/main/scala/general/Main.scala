package general

import procesamiento.{ProcesadorTexto, SimplificadorAST}
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

    val cosa = ProcesadorTexto.apply("traslacion[0, 0](\n\t\tcirculo[0 @ 5, 10]\n)\n")
    cosa.imprimir()
    println
    val cosaRancia = SimplificadorAST.simplificarArbol(cosa)
    cosaRancia.imprimir()


    TADPDrawingAdapter.forInteractiveScreen {(imageDescription, adapter) =>  SimplificadorAST.simplificarArbol(ProcesadorTexto.apply(imageDescription)).preOrden(adapter)}

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

