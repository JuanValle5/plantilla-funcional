package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MultiplicarMatricesRecTest extends AnyFunSuite {
    val objMultiMatrec = new MultiplicarMatricesRec() // Instancia del objeto que multiplica matrices

    test("Multiplicar matrices secuencial 2x2 - Caso 1") {
        val matriz1: Vector[Vector[Int]] = Vector(Vector(1, 2), Vector(3, 4))
        val matriz2: Vector[Vector[Int]] = Vector(Vector(2, 0), Vector(1, 2))
        val resultadoEsperado: Vector[Vector[Int]] = Vector(Vector(4, 4), Vector(10, 8))
        assert(objMultiMatrec.multMatrizRec(matriz1, matriz2) === resultadoEsperado)
    }

    test("Multiplicar matrices secuencial 2x2 - Caso 2") {
        val matriz1: Vector[Vector[Int]] = Vector(Vector(0, 1), Vector(2, 3))
        val matriz2: Vector[Vector[Int]] = Vector(Vector(1, 0), Vector(0, 1))
        val resultadoEsperado: Vector[Vector[Int]] = Vector(Vector(0, 1), Vector(2, 3))
        assert(objMultiMatrec.multMatrizRec(matriz1, matriz2) === resultadoEsperado)
    }

    test("Multiplicar matrices secuencial 2x2 - Caso 3") {
        val matriz1: Vector[Vector[Int]] = Vector(Vector(1, -1), Vector(-1, 1))
        val matriz2: Vector[Vector[Int]] = Vector(Vector(2, 3), Vector(4, 5))
        val resultadoEsperado: Vector[Vector[Int]] = Vector(Vector(-2, -2), Vector(2, 2))
        assert(objMultiMatrec.multMatrizRec(matriz1, matriz2) === resultadoEsperado)
    } 

    test("Multiplicar matrices secuencial 2x2 - Caso 4") {
        val matriz1: Vector[Vector[Int]] = Vector(Vector(2, 0), Vector(0, 2))
        val matriz2: Vector[Vector[Int]] = Vector(Vector(3, 4), Vector(5, 6))
        val resultadoEsperado: Vector[Vector[Int]] = Vector(Vector(6, 8), Vector(10, 12))
        assert(objMultiMatrec.multMatrizRec(matriz1, matriz2) === resultadoEsperado)
        //print(objMultiMat.multMatriz(matriz1, matriz2))
    }

    test("Multiplicar matrices secuencial 2x2 - Caso 5") {
        val matriz1: Vector[Vector[Int]] = Vector(Vector(1, 2), Vector(3, 4))
        val matriz2: Vector[Vector[Int]] = Vector(Vector(0, 1), Vector(1, 0))
        val resultadoEsperado: Vector[Vector[Int]] = Vector(Vector(2, 1), Vector(4, 3))
        assert(objMultiMatrec.multMatrizRec(matriz1, matriz2) === resultadoEsperado)
    }
}