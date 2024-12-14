package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ProductoPuntoVectoresTest extends AnyFunSuite {
    val objProdPunto = new ProductoPuntoVectores()
    test("Producto punto Vectores - Caso 1") {
        val vector1: Vector[Int] = Vector(1, 2, 3)
        val vector2: Vector[Int] = Vector(4, 5, 6)
        val ValorEsperado: Int = 32
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores - Caso 2") {
        val vector1: Vector[Int] = Vector(0, 0, 0)
        val vector2: Vector[Int] = Vector(1, 2, 3)
        val ValorEsperado: Int = 0
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores - Caso 3") {
        val vector1: Vector[Int] = Vector(1, 0, -1)
        val vector2: Vector[Int] = Vector(-1, 0, 1)
        val ValorEsperado: Int = -2
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores - Caso 4") {
        val vector1: Vector[Int] = Vector(3, 3, 3)
        val vector2: Vector[Int] = Vector(3, 3, 3)
        val ValorEsperado: Int = 27
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores - Caso 5") {
        val vector1: Vector[Int] = Vector(1, 2)
        val vector2: Vector[Int] = Vector(3, 4)
        val ValorEsperado: Int = 11
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores Paralelo - Caso 6") {
        val vector1: Vector[Int] = Vector(5, 1, 3)
        val vector2: Vector[Int] = Vector(2, 3, 4)
        val ValorEsperado: Int = 25
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores Paralelo - Caso 7") {
        val vector1: Vector[Int] = Vector(-2, -3, -4)
        val vector2: Vector[Int] = Vector(-1, -2, -3)
        val ValorEsperado: Int = 20
        assert(objProdPunto.productoPuntoParalelo(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores Paralelo- Caso 8") {
        val vector1: Vector[Int] = Vector(1, 1, 1, 1)
        val vector2: Vector[Int] = Vector(1, 1, 1, 1)
        val ValorEsperado: Int = 4
        assert(objProdPunto.productoPuntoParalelo(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores Paralelo- Caso 9") {
        val vector1: Vector[Int] = Vector(10, 20, 30)
        val vector2: Vector[Int] = Vector(0, 1, 0)
        val ValorEsperado: Int = 20
        assert(objProdPunto.productoPuntoParalelo(vector1, vector2) === ValorEsperado)
    }

    test("Producto punto Vectores Paralelo- Caso 10") {
        val vector1: Vector[Int] = Vector(2, 4, 6, 8)
        val vector2: Vector[Int] = Vector(1, 1, 1, 1)
        val ValorEsperado: Int = 20
        assert(objProdPunto.productoPuntoSecuencial(vector1, vector2) === ValorEsperado)
    }

}
