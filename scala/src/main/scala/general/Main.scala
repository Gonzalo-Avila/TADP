package general

import procesamiento.ProcesadorTexto
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

    //val cosa = ProcesadorTexto.apply("color[200, 200, 200](\n   grupo(\n\trectangulo[100 @ 100, 200 @ 200]),\n\tcirculo[100 @ 300, 150])\n   )\n)")
    //cosa.imprimir()

    TADPDrawingAdapter.forInteractiveScreen {(imageDescription, adapter) => ProcesadorTexto.apply(imageDescription).preOrden(adapter)}

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

