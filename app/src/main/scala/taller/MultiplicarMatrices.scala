package taller

import util.Random

class MultiplicarMatrices {
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
//Implimentacion del codigo Taller 3
    //Funcion para la multiplicacion de matrices
    def multMatriz(m1: Matriz, m2: Matriz):Matriz = {
       //vamos a multiplicar 2 matrices al azar
        val m2t = transpuesta(m2)
        Vector.tabulate(m1.length,m2t.length)((i,j)=>prodPunto(m1(i),m2t(j)))
        
    }
}
