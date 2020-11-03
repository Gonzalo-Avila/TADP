package tadp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SimplificacionSpec extends AnyFlatSpec with should.Matchers {

  /* TODO// Nico | No me deja comparar Unit con String. Hace falta que imprimir devuelva Unit?
                 | ¿Es correcto que la simplificación no tenga .end al final?

  it should "simplificar traslacion redundante" in {
    val traslacionRedundante = ProcesadorTexto.apply("traslacion[0, 0](\n\t\tcirculo[0 @ 5, 10]\n)\n")
    val traslacionSimplificada = SimplificadorAST.simplificarArbol(traslacionRedundante)
    traslacionSimplificada.imprimir() shouldEqual "adapter.circle((0.0,5.0),10.0)"
  }
   */
}
