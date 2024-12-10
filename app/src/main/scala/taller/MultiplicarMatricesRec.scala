package taller
import org.scalameter._
import scala.util.Random

class MultiplicarMatricesRec {
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

    def multMatrizRec(m1: Matriz, m2: Matriz): Matriz = {
        require(m1.head.length == m2.length, "El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.")

        // Transponer m2 para facilitar el cálculo
        val m2Transpuesta = transpuesta(m2)

        // Función recursiva para calcular una fila de la matriz resultante
        def calcularFila(fila: Vector[Int], columnas: Matriz): Vector[Int] = {
            if (columnas.isEmpty) Vector()
            else prodPunto(fila, columnas.head) +: calcularFila(fila, columnas.tail)
        }

        // Función recursiva para calcular toda la matriz resultante
        def calcularMatriz(filas: Matriz, columnas: Matriz): Matriz = {
            if (filas.isEmpty) Vector()
            else calcularFila(filas.head, columnas) +: calcularMatriz(filas.tail, columnas)
        }

        calcularMatriz(m1, m2Transpuesta)
    }



}
