package taller
import org.scalameter._
object comparacionScalameter {
    def main(args: Array[String]): Unit = {
        val objSolucion = new Solucion()
        val finca10: objSolucion.Finca = Vector(
            (15, 3, 1), // Tablón 0
            (10, 2, 3), // Tablón 1
            (12, 4, 2), // Tablón 2
            (8, 1, 4), // Tablón 3
            (14, 3, 1), // Tablón 4
            (10, 2, 3), // Tablón 5
            (13, 4, 2), // Tablón 6
            (9, 1, 4), // Tablón 7
            (16, 3, 1), // Tablón 8
            (11, 2, 3) // Tablón 9
        )
        val pi10: objSolucion.RiegoVector = Vector(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val distancia10: objSolucion.Distancia = objSolucion.distanciaAlAzar(10)
        val finca20: objSolucion.Finca = Vector(
            (20, 4, 3), (18, 3, 2), (22, 5, 1), (25, 6, 4), (15, 3, 3), // Tablones 0-4
            (19, 2, 1), (21, 4, 2), (16, 3, 3), (23, 5, 1), (17, 4, 4), // Tablones 5-9
            (24, 6, 2), (15, 2, 3), (20, 3, 1), (18, 3, 2), (22, 5, 4), // Tablones 10-14
            (25, 6, 1), (19, 2, 3), (21, 4, 2), (16, 3, 1), (23, 5, 4) // Tablones 15-19
        )
        val pi20: objSolucion.RiegoVector = Vector(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        val distancia20: objSolucion.Distancia = objSolucion.distanciaAlAzar(20)
        val finca30: objSolucion.Finca = Vector(
            (25, 6, 4), (20, 4, 3), (18, 3, 2), (22, 5, 1), (15, 3, 3), // Tablones 0-4
            (19, 2, 1), (21, 4, 2), (16, 3, 3), (23, 5, 1), (17, 4, 4), // Tablones 5-9
            (24, 6, 2), (15, 2, 3), (20, 3, 1), (18, 3, 2), (22, 5, 4), // Tablones 10-14
            (25, 6, 1), (19, 2, 3), (21, 4, 2), (16, 3, 1), (23, 5, 4), // Tablones 15-19
            (20, 3, 2), (22, 4, 4), (25, 5, 3), (19, 6, 1), (21, 2, 4), // Tablones 20-24
            (16, 3, 3), (23, 5, 1), (17, 4, 2), (24, 6, 4), (15, 2, 3) // Tablones 25-29
        )
        val pi30: objSolucion.RiegoVector = Vector(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29)
        val distancia30: objSolucion.Distancia = objSolucion.distanciaAlAzar(30)
        //--------------------------------------------------------------------------------------------------
        /*
        //COMPARACION ENTRE costoRiegoFinca y costoRiegoFincaPar con 10 tablones
        val timeSeq = measure {
            objSolucion.costoRiegoFinca(finca10,pi10)
        }
        val timePar = measure{
            objSolucion.costoRiegoFincaPar(finca10,pi10)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        /*
        //COMPARACION ENTRE costoRiegoFinca y costoRiegoFincaPar con 20 tablones
        val timeSeq = measure {
            objSolucion.costoRiegoFinca(finca20,pi20)
        }
        val timePar = measure{
            objSolucion.costoRiegoFincaPar(finca20,pi20)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        /*
        //COMPARACION ENTRE costoRiegoFinca y costoRiegoFincaPar con 30 tablones
        val timeSeq = measure {
            objSolucion.costoRiegoFinca(finca30,pi30)
        }
        val timePar = measure{
            objSolucion.costoRiegoFincaPar(finca30,pi30)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
        */
        //--------------------------------------------------------------------------------------------------
        /*
                //COMPARACION ENTRE costoMovilidad y costoMovilidadPar 10
                val timeSeq = measure {
                    objSolucion.costoMovilidad(finca10,pi10,distancia10)
                }
                val timePar = measure{
                    objSolucion.costoMovilidadPar(finca10,pi10,distancia10)
                }
                println(s"Secuencial: $timeSeq ms")
                println(s"Paralelo: $timePar ms")


         */

        /*
        //COMPARACION ENTRE costoMovilidad y costoMovilidadPar 20
        val timeSeq = measure {
            objSolucion.costoMovilidad(finca20,pi20,distancia20)
        }
        val timePar = measure{
            objSolucion.costoMovilidadPar(finca20,pi20,distancia20)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        /*
        //COMPARACION ENTRE costoMovilidad y costoMovilidadPar 30
        val timeSeq = measure {
            objSolucion.costoMovilidad(finca30,pi30,distancia30)
        }
        val timePar = measure{
            objSolucion.costoMovilidadPar(finca30,pi30,distancia30)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        //--------------------------------------------------------------------------------------------------
        /*
        //COMPARACION ENTRE generarProgramacionesRiego y generarProgramacionesRiegoPar 10
        val timeSeq = measure {
            objSolucion.generarProgramacionesRiego(finca10)
        }
        val timePar = measure{
            objSolucion.generarProgramacionesRiegoPar(finca10)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        /*
        //COMPARACION ENTRE generarProgramacionesRiego y generarProgramacionesRiegoPar 20
        val timeSeq = measure {
            objSolucion.generarProgramacionesRiego(finca20)
        }
        val timePar = measure{
            objSolucion.generarProgramacionesRiegoPar(finca20)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
         */
        /*
        //COMPARACION ENTRE generarProgramacionesRiego y generarProgramacionesRiegoPar 30
        val timeSeq = measure {
            objSolucion.generarProgramacionesRiego(finca30)
        }
        val timePar = measure{
            objSolucion.generarProgramacionesRiegoPar(finca30)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
        */
        //--------------------------------------------------------------------------------------------------
        /*
        //COMPARACION ENTRE programacionRiegoOptimo y programacionRiegoOptimoPar 10
        val timeSeq = measure {
            objSolucion.ProgramacionRiegoOptimo(finca10,distancia10)
        }
        val timePar = measure{
            objSolucion.ProgramacionRiegoOptimoPar(finca10,distancia10)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")
        */

        //COMPARACION ENTRE programacionRiegoOptimo y programacionRiegoOptimoPar 20

        val timeSeq = measure {
            objSolucion.ProgramacionRiegoOptimo(finca20,distancia20)
        }
        val timePar = measure{
            objSolucion.ProgramacionRiegoOptimoPar(finca20,distancia20)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")


        /*
        //COMPARACION ENTRE programacionRiegoOptimo y programacionRiegoOptimoPar 30
        val timeSeq = measure {
            objSolucion.ProgramacionRiegoOptimo(finca30,distancia30)
        }
        val timePar = measure{
            objSolucion.ProgramacionRiegoOptimoPar(finca30,distancia30)
        }
        println(s"Secuencial: $timeSeq ms")
        println(s"Paralelo: $timePar ms")

    }*/
    }
}