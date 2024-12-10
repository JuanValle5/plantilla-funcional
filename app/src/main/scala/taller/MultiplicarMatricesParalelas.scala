package taller
import scala.collection.parallel.CollectionConverters._
import util.Random

class MultiplicarMatricesParalelas {
    private type Matriz = Vector[Vector[Int]]

    // Función para obtener una matriz al azar
    def matrizAlAzar(long: Int, vals: Int): Matriz = {
        Vector.fill(long, long)(Random.nextInt(vals))
    }

    // Función de la operación producto punto
    def prodPunto(v1: Vector[Int], v2: Vector[Int]): Int = {
      (v1 zip v2).map { case (i, j) => i * j }.sum
    }

    // Función de la Matriz Traspuesta
    def traspuesta(m: Matriz): Matriz = {
        val l = m.length
        Vector.tabulate(l, l)((i, j) => m(j)(i))
    }

    // Función para la multiplicación de matrices (Paralelizada)
    def multMatriz(m1: Matriz, m2: Matriz): Matriz = {
        //vamos a multiplicar 2 matrices al azar de forma paralelam(paralelizable)
        val m2t = traspuesta(m2)
        /*m1.par.map(fila => m2t.par.map(columna => prodPunto(fila,columna)).toVector).toVector
    */
        val resultado = m1.par.map { fila =>
            m2t.par.map { columna =>
                prodPunto(fila, columna)
            }.toVector
        }.toVector

        resultado


    }


        /*

    */
}

