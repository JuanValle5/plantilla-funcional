package taller

import scala.util.Random

class MultiplicarMatricesRecParalelas {
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
    import scala.collection.parallel.CollectionConverters._

    def multMatrizRecPar(m1: Matriz, m2: Matriz): Matriz = {
        require(m1.head.length == m2.length, "El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.")

        // Transponer m2 para facilitar el cálculo
        val m2Transpuesta = transpuesta(m2)

        // Función para calcular una fila de la matriz resultante de forma paralela
        def calcularFila(fila: Vector[Int], columnas: Matriz): Vector[Int] = {
            columnas.par.map(columna => prodPunto(fila, columna)).toVector
        }

        // Función para calcular toda la matriz de forma paralela
        def calcularMatriz(filas: Matriz, columnas: Matriz): Matriz = {
            filas.par.map(fila => calcularFila(fila, columnas)).toVector
        }

        calcularMatriz(m1, m2Transpuesta)
    }

}
