package taller
import scala.util.Random
import common._
import org.scalameter.withWarmer
import org.scalameter.Warmer
import org.scalameter.measure
import org.scalameter.Warmer.Default

import scala.annotation.tailrec
import scala.collection.parallel.immutable.ParVector
import scala.collection.parallel.CollectionConverters._

class Solucion() {

    type Tablon = (Int, Int, Int) //(Tiempo Supervivencia, Tiempo de regado, Prioridad del tablon)
    type Finca = Vector[Tablon] //Un Vector de Tablones
    type Distancia = Vector[Vector[Int]] //Distancia entre 2 tablones
    type RiegoVector = Vector[Int] //Programacion de riego
    type TiempoInicioRiego = Vector[Int] //Asocia el tiempo de supervivencia con el momento del tiempo en que se riega
    val random = new Random()

    def fincaAlAzar(long: Int): Finca = {
        val v = Vector.fill(long)(
            (random.nextInt(long * 2) + 1,
                random.nextInt(long) + 1,
                random.nextInt(4) + 1)
        )
        v
    }

    def distanciaAlAzar(long: Int): Distancia = {
        val v = Vector.fill(long, long)(random.nextInt(long * 3) + 1)
        (0 until long).toVector.map(i =>
            (0 until long).toVector.map(j =>
                if (i < j) v(i)(j)
                else if (i == j) 0
                else v(j)(i)
            )
        )
    }

    def generarProgRiegoAlAzar(n: Int): RiegoVector = {
        random.shuffle((0 until n).toVector)
    }

    def next(f: Finca, i: Int): Int = f(i)._1

    def next2(f: Finca, i: Int): Int = f(i)._2

    def next3(f: Finca, i: Int): Int = f(i)._3

    //CALCULANDO EL TIEMPO DE INICIO DE RIEGO
    def tiempo_riesgo_tablon(f: Finca, pi: RiegoVector): TiempoInicioRiego = {
        @tailrec
        def aux(j: Int, tiempos: Vector[Int]): Vector[Int] = {
            if (j >= pi.length) tiempos
            else {
                val prevTablon = pi(j-1)
                val currTablon = pi(j)
                val updatedTiempos = tiempos.updated(currTablon, tiempos(prevTablon) + next2(f, prevTablon))
                aux(j + 1, updatedTiempos)
            }
        }
        aux(1, Vector.fill(f.length)(0))
    }

    //CALCULANDO COSTOS
    def costoRiegoTablon(i: Int, f: Finca, pi: RiegoVector): Int = {
        def aux(tiempoInicio: Int, tiempoFinal: Int): Int = {
            if (next(f, i) - next2(f, i) >= tiempoInicio) {
                next(f, i) - tiempoFinal
            } else {
                next3(f, i) * (tiempoFinal - next(f, i))
            }
        }
        val tiempoInicio = tiempo_riesgo_tablon(f, pi)(i)
        val tiempoFinal = tiempoInicio + next2(f, i)
        aux(tiempoInicio, tiempoFinal)
    }

    def costoRiegoFinca(f: Finca, pi: RiegoVector): Int = {
        def length(f: Finca): Int ={
            if (f.isEmpty) 0
            else 1 + length(f.tail)
        }
        @tailrec
        def aux(i: Int, acc: Int): Int = {
            if (i >= length(f)) acc
            else aux(i + 1, acc + costoRiegoTablon(i, f, pi))
        }
        aux(0, 0)
    }

    def costoMovilidad(f:Finca, pi:RiegoVector, d:Distancia) : Int = {
        @tailrec
        def aux(j: Int, acc: Int): Int = {
            if (j >= pi.length - 1) acc
            else aux(j + 1, acc + d(pi(j))(pi(j + 1)))
        }
        aux(0, 0)
    }

    //
    def generarProgramacionesRiego(f:Finca) : Vector[RiegoVector] = {
        def permutaciones[T](xs: Vector[T]): Vector[Vector[T]] = {
            if (xs.isEmpty) Vector(Vector())
            else {
                for {
                    i <- xs.indices.toVector
                    rest = xs.take(i) ++ xs.drop(i + 1)
                    perm <- permutaciones(rest)
                } yield xs(i) +: perm
            }
        }
        permutaciones(f.indices.toVector)
    }


    def ProgramacionRiegoOptimo(f:Finca, d:Distancia) : (RiegoVector, Int) = {
        @tailrec
        def aux(programaciones: Vector[RiegoVector], minProg: RiegoVector, minCost: Int): (RiegoVector, Int) = {
            if (programaciones.isEmpty) (minProg, minCost)
            else {
                val pi = programaciones.head
                val cost = costoRiegoFinca(f, pi) + costoMovilidad(f, pi, d)
                if (cost < minCost) aux(programaciones.tail, pi, cost)
                else aux(programaciones.tail, minProg, minCost)
            }
        }
        val programaciones = generarProgramacionesRiego(f)
        aux(programaciones.tail, programaciones.head, costoRiegoFinca(f, programaciones.head) + costoMovilidad(f, programaciones.head, d))
    }
    def costoRiegoFincaPar(f: Finca, pi: RiegoVector): Int = {
        f.par.map(tablon => costoRiegoTablon(f.indexOf(tablon), f, pi)).sum
    }

    def costoMovilidadPar(f: Finca, pi: RiegoVector, d: Distancia): Int = {
        pi.indices.init.par.map(j => d(pi(j))(pi(j + 1))).sum
    }

    def generarProgramacionesRiegoPar(f: Finca): Vector[RiegoVector] = {
        def permutaciones[T](xs: Vector[T]): Vector[Vector[T]] = {
            if (xs.isEmpty) Vector(Vector())
            else {
                xs.indices.toVector.par.flatMap { i =>
                    val rest = xs.take(i) ++ xs.drop(i + 1)
                    permutaciones(rest).map(xs(i) +: _)
                }.toVector
            }
        }
        permutaciones(f.indices.toVector)
    }


    def ProgramacionRiegoOptimoPar(f:Finca, d:Distancia) : (RiegoVector, Int) = {
        @tailrec
        def aux(programaciones: Vector[RiegoVector], minProg: RiegoVector, minCost: Int): (RiegoVector, Int) = {
            if (programaciones.isEmpty) (minProg, minCost)
            else {
                val pi = programaciones.head
                val cost = costoRiegoFincaPar(f, pi) + costoMovilidadPar(f, pi, d)
                if (cost < minCost) aux(programaciones.tail, pi, cost)
                else aux(programaciones.tail, minProg, minCost)
            }
        }
        val programaciones = generarProgramacionesRiegoPar(f)
        aux(programaciones.tail, programaciones.head, costoRiegoFincaPar(f, programaciones.head) + costoMovilidadPar(f, programaciones.head, d))
    }
}