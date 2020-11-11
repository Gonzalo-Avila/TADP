package parsers

import general._

import scala.util.Try

 object double extends Parser [Double]{

   def apply(cadena: String): Try[Resultado[Double]] =
       integer.sepBy(char('.')).apply(cadena).map { resultadoCorrecto =>
         resultadoCorrecto.getElementoParseado(1) match {
           case parteDecimal if parteDecimal < 0 => throw new RuntimeException("La parte decimal no puede ser negativa");
           case _ => {
             val cadenaRestante = resultadoCorrecto.getCadenaRestante
             resultadoCorrecto.getElementoParseado match {
               case elemento1 :: elemento2 :: Nil => Resultado(s"$elemento1.$elemento2".toDouble, cadenaRestante)
               case elemento1 :: elemento2 :: resto => Resultado(s"$elemento1.$elemento2".toDouble, "." + resto.mkString(".") + cadenaRestante)
             }
           }
         }
       }

}