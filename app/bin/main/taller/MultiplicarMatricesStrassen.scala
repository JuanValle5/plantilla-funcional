package taller

import scala.util.Random

class MultiplicarMatricesStrassen {
//Creacion de variables y metodos preliminares
    //Crear el tipo Matriz
    private type Matriz = Vector[Vector[Int]]

    //Funcion para obtener una matriz al azar
    def matrizAlAzar(long :Int, vals :Int): Matriz = {
        val v = Vector.fill(long,long){Random.nextInt(vals)}
        v
    }
    //Funcion de la operacion producto punto
    def prodPunto(v1: Vector[Int], v2: Vector[Int]):Int ={
        (v1 zip v2).map({case (i,j) => (i*j)}).sum
    }
    //Funcion de la Matriz Traspuesta
    def transpuesta(m: Matriz): Matriz ={
        val l = m.length
        Vector.tabulate(l,l)((i,j)=>m(j)(i))
    }
    // Suma de matrices
    def sumaMatriz(m1: Matriz, m2: Matriz): Matriz = {
        (m1 zip m2).map { case (row1, row2) => (row1 zip row2).map { case (a, b) => a + b } }
    }

    // Resta de matrices
    def restaMatriz(m1: Matriz, m2: Matriz): Matriz = {
        (m1 zip m2).map { case (row1, row2) => (row1 zip row2).map { case (a, b) => a - b } }
    }

    // División de una matriz en cuadrantes
    def dividirMatriz(m: Matriz): (Matriz, Matriz, Matriz, Matriz) = {
        val n = m.length / 2
        val (superior, inferior) = m.splitAt(n)
        val (a11, a12) = superior.map(_.splitAt(n)).unzip
        val (a21, a22) = inferior.map(_.splitAt(n)).unzip
        (a11, a12, a21, a22)
    }

    // Unir cuadrantes para formar una matriz
    def unirMatriz(c11: Matriz, c12: Matriz, c21: Matriz, c22: Matriz): Matriz = {
        (c11 zip c12).map { case (row1, row2) => row1 ++ row2 } ++
            (c21 zip c22).map { case (row1, row2) => row1 ++ row2 }
    }

    // Algoritmo de Strassen
    def strassenMetodo(m1: Matriz, m2: Matriz): Matriz = {
        val n = m1.length

        // Caso base para matrices pequeñas
        if (n == 1) {
            Vector(Vector(m1(0)(0) * m2(0)(0)))
        } else {
            // Dividir matrices en cuadrantes
            val (a11, a12, a21, a22) = dividirMatriz(m1)
            val (b11, b12, b21, b22) = dividirMatriz(m2)

            // Calcular los productos intermedios
            val p1 = strassenMetodo(a11, restaMatriz(b12, b22))
            val p2 = strassenMetodo(sumaMatriz(a11, a12), b22)
            val p3 = strassenMetodo(sumaMatriz(a21, a22), b11)
            val p4 = strassenMetodo(a22, restaMatriz(b21, b11))
            val p5 = strassenMetodo(sumaMatriz(a11, a22), sumaMatriz(b11, b22))
            val p6 = strassenMetodo(restaMatriz(a12, a22), sumaMatriz(b21, b22))
            val p7 = strassenMetodo(restaMatriz(a11, a21), sumaMatriz(b11, b12))

            // Combinar resultados
            val c11 = sumaMatriz(restaMatriz(sumaMatriz(p5, p4), p2), p6)
            val c12 = sumaMatriz(p1, p2)
            val c21 = sumaMatriz(p3, p4)
            val c22 = restaMatriz(restaMatriz(sumaMatriz(p1, p5), p3), p7)

            unirMatriz(c11, c12, c21, c22)
        }
    }
}
