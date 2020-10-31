package general

import procesamiento.ProcesadorTexto
import tadp.internal.TADPDrawingAdapter

object Main {


  def main(Args: Array[String]) {

    TADPDrawingAdapter.forInteractiveScreen {(imageDescription, adapter) => ProcesadorTexto.apply(imageDescription).preOrden(adapter)}
    /*
    val lines = scala.io.Source.fromFile("C:\\mes.txt").mkString
    val carpinchoBostero = parserTexto.apply(lines)
    */

    //TADPDrawingAdapter.forScreen {a => carpinchoBostero.preOrden(a)}


  }
}

