package taller

import util.Random
import scala.collection.parallel.CollectionConverters._

class ProductoPuntoVectores {
    // Funciones preliminares
    // Crear un vector al azar
    def vectorAlAzar(long: Int, vals: Int): Vector[Int] = {
        Vector.fill(long) { Random.nextInt(vals) }
    }

    // Multiplicación secuencial de dos vectores
    def productoPuntoSecuencial(v1: Vector[Int], v2: Vector[Int]): Int = {
        require(v1.length == v2.length, "Los vectores deben tener la misma longitud.")
        (v1 zip v2).map { case (a, b) => a * b }.sum
    }

    // Multiplicación paralela de dos vectores
    def productoPuntoParalelo(v1: Vector[Int], v2: Vector[Int]): Int = {
        require(v1.length == v2.length, "Los vectores deben tener la misma longitud.")
        (v1 zip v2).par.map { case (a, b) => a * b }.sum
    }
}

