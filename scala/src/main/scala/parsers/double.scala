package parsers

import general._

import scala.util.Try

 object double extends Parser [Double]{
/*  def apply2(cadena: String): Try[Resultado[Double]] = {
    val cadenaSeparada: Array[String] = cadena.split('.')

    Try(
      cadenaSeparada match {
        case cad if cad.head.isEmpty => throw new Exception()
        case _ =>
          val resultadoParteEntera = integer.apply(cadenaSeparada.head)
          val resultadoParteDecimal = integer.satisfies((entero => entero >= 0)).apply(cadenaSeparada.tail.head)
          cadenaSeparada.foreach(e => println("elemento: "+e))

          val elementoParseado = (resultadoParteEntera.get.getElementoParseado + "." + resultadoParteDecimal.get.getElementoParseado).toDouble
          var cadenaRestante = resultadoParteEntera.get.getCadenaRestante + resultadoParteDecimal.get.getCadenaRestante
          //Agrego otros elementos de la lista a la cadenaRestante en caso de que haya más de un '.' en la cadena a parsear.
          if (cadenaSeparada.size > 2) {
            cadenaRestante = cadenaRestante + "." + cadenaSeparada.tail.tail.mkString(".")
          }
          println("Double: " + elementoParseado)
          new Resultado(elementoParseado, cadenaRestante)
      }
    )
  }*/

   def apply(cadena: String): Try[Resultado[Double]] = {
     val cadenaSeparada = integer.sepBy(char('.')).apply(cadena)

     /*Try(
       cadenaSeparada.get.getElementoParseado(1) match {
         case parteDecimal if parteDecimal < 0 => throw new Exception()
         case _ => {
           val elementoParseado = (cadenaSeparada.get.getElementoParseado.head.toString + "." + cadenaSeparada.get.getElementoParseado(1).toString).toDouble
           var cadenaRestante = cadenaSeparada.get.getCadenaRestante
           //Agrego otros elementos de la lista a la cadenaRestante en caso de que haya más de un '.' en la cadena a parsear.
           if (cadenaSeparada.get.getElementoParseado.size > 2) {
             cadenaRestante = '.' + cadenaSeparada.get.getElementoParseado.tail.tail.mkString(".")
           }
           new Resultado(elementoParseado, cadenaRestante)
         }
       }
     )*/


       cadenaSeparada.map { resultadoCorrecto =>
         resultadoCorrecto.getElementoParseado(1) match {
           case parteDecimal if parteDecimal < 0 => throw new Exception()
           case _ => {
             //val elementoParseado = (resultadoCorrecto.getElementoParseado.head.toString + "." + resultadoCorrecto.getElementoParseado(1).toString).toDouble
             val cadenaRestante = resultadoCorrecto.getCadenaRestante
             //Agrego otros elementos de la lista a la cadenaRestante en caso de que haya más de un '.' en la cadena a parsear.

             resultadoCorrecto.getElementoParseado match {
               case elemento1 :: elemento2 :: Nil => Resultado(s"$elemento1.$elemento2".toDouble, cadenaRestante)
               case elemento1 :: elemento2 :: resto => Resultado(s"$elemento1.$elemento2".toDouble, "." + resto.mkString(".") + cadenaRestante)
             }
           }
         }
       }


     /*Try(
       cadenaSeparada match {
         case cad if cad.head.isEmpty => throw new Exception()
         case _ =>
           val resultadoParteEntera = integer.apply(cadenaSeparada.head)
           val resultadoParteDecimal = integer.satisfies((entero => entero >= 0)).apply(cadenaSeparada.tail.head)
           cadenaSeparada.foreach(e => println("elemento: "+e))

           val elementoParseado = (resultadoParteEntera.get.getElementoParseado + "." + resultadoParteDecimal.get.getElementoParseado).toDouble
           var cadenaRestante = resultadoParteEntera.get.getCadenaRestante + resultadoParteDecimal.get.getCadenaRestante
           //Agrego otros elementos de la lista a la cadenaRestante en caso de que haya más de un '.' en la cadena a parsear.
           if (cadenaSeparada.size > 2) {
             cadenaRestante = cadenaRestante + "." + cadenaSeparada.tail.tail.mkString(".")
           }
           println("Double: " + elementoParseado)
           new Resultado(elementoParseado, cadenaRestante)
       }
     )*/
   }
}