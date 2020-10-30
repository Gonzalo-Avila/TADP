package parsersImagenes

import scala.util.{Failure, Success, Try}

object parserTexto {

  def apply(cadena: String): Unit = {

    var cadenaRestante = cadena

    while (!cadenaRestante.isEmpty) {

      val resultado1 = anyTransformation(cadena)
      resultado1 match {
        case Success(res) =>
          {
            //Evaluar cual es la transformación
            cadenaRestante = res.getCadenaRestante
            this.apply(cadenaRestante)
          }
      }


    }


  }

}
