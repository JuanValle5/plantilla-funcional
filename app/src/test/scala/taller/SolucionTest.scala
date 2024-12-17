package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

import scala.math.Ordering.Implicits.seqOrdering
@RunWith(classOf[JUnitRunner])
class SolucionTest extends AnyFunSuite {
    val solucion = new Solucion()
    //TEST PARA TIEMPO RIESGO TABLON
    test("Calcular tiempos de inicio de riego para una finca pequeña sin penalización") {
        val finca: solucion.Finca = Vector((10, 3, 4), (5, 2, 3), (8, 1, 2))
        val programacion: solucion.RiegoVector = Vector(2, 0, 1) // Orden de riego
        val esperado: solucion.TiempoInicioRiego = Vector(1, 4, 0) // Cálculo esperado
        val resultado = solucion.tiempo_riesgo_tablon(finca, programacion)
        assert(resultado == esperado)
    }
    test("Calcular tiempos de inicio para una finca de 4 tablones con riego en orden secuencial") {
        val finca: solucion.Finca = Vector((10, 2, 3), (8, 3, 2), (12, 1, 1), (15, 4, 4))
        val programacion: solucion.RiegoVector = Vector(0, 1, 2, 3) // Riego en orden natural
        val esperado: solucion.TiempoInicioRiego = Vector(0, 2, 5, 6) // Acumulación directa de tiempos
        val resultado = solucion.tiempo_riesgo_tablon(finca, programacion)
        assert(resultado == esperado)
    }
    test("Calcular tiempos de inicio con programación inversa") {
        val finca: solucion.Finca = Vector((9, 2, 3), (10, 3, 4), (11, 1, 2))
        val programacion: solucion.RiegoVector = Vector(2, 1, 0) // Riego en orden inverso
        val esperado: solucion.TiempoInicioRiego = Vector(4, 1, 0) // Empieza desde el último tablón
        val resultado = solucion.tiempo_riesgo_tablon(finca, programacion)
        assert(resultado == esperado)
    }
    test("Calcular tiempos de inicio para finca con tiempos de riego variables") {
        val finca: solucion.Finca = Vector((10, 1, 3), (8, 2, 2), (12, 3, 1), (15, 4, 4), (7, 5, 3))
        val programacion: solucion.RiegoVector = Vector(4, 2, 0, 1, 3)
        val esperado: solucion.TiempoInicioRiego = Vector(8, 9, 5, 11, 0) // Cálculo escalonado
        val resultado = solucion.tiempo_riesgo_tablon(finca, programacion)
        assert(resultado == esperado)
    }
    test("Calcular tiempos de inicio para una finca con un solo tablón") {
        val finca: solucion.Finca = Vector((10, 3, 4)) // Solo un tablón
        val programacion: solucion.RiegoVector = Vector(0) // Solo un riego
        val esperado: solucion.TiempoInicioRiego = Vector(0) // Empieza inmediatamente
        val resultado = solucion.tiempo_riesgo_tablon(finca, programacion)
        assert(resultado == esperado)
    }
    //TEST PARA COSTORIEGOTABLON
    test("Costo de riego sin penalización (riesgo a tiempo)") {
        val finca: solucion.Finca = Vector((10, 3, 2)) // ts = 10, tr = 3, p = 2
        val programacion: solucion.RiegoVector = Vector(0) // Solo un tablón
        // tiempoInicio = 0, tiempoFinal = 0 + 3 = 3
        // ts - tr >= tiempoInicio -> 10 - 3 >= 0 -> costo = ts - tiempoFinal = 10 - 3 = 7
        val esperado = 7
        val resultado = solucion.costoRiegoTablon(0, finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo de riego con penalización (inicio tardío)") {
        val finca: solucion.Finca = Vector((5, 2, 3)) // ts = 5, tr = 2, p = 3
        val programacion: solucion.RiegoVector = Vector(0) // Solo un tablón
        // tiempoInicio = 4, tiempoFinal = 4 + 2 = 6
        // ts - tr < tiempoInicio -> 5 - 2 < 4 -> costo = p * (tiempoFinal - ts) = 3 * (6 - 5) = 3
        val esperado = 3
        val resultado = solucion.costoRiegoTablon(0, finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo de riego para finca sin penalización en múltiple tablones") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 1, 3)) // Tablones con ts, tr, p
        val programacion: solucion.RiegoVector = Vector(2, 0, 1) // Orden de riego
        // Tablón 2: tiempoInicio = 0, tiempoFinal = 1, costo = ts - tiempoFinal = 12 - 1 = 11
        // Tablón 0: tiempoInicio = 1, tiempoFinal = 4, costo = ts - tiempoFinal = 10 - 4 = 6
        // Tablón 1: tiempoInicio = 4, tiempoFinal = 6, costo = ts - tiempoFinal = 8 - 6 = 2
        val esperado = Vector(11, 6, 2)
        val resultado = programacion.map(i => solucion.costoRiegoTablon(i, finca, programacion))
        assert(resultado == esperado)
    }
    test("Costo de riego con penalización mixta en finca de múltiples tablones") {
        val finca: solucion.Finca = Vector((6, 3, 2), (5, 2, 1), (4, 1, 4)) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(1, 2, 0)
        val esperado = Vector(3, 1, 0)
        val resultado = programacion.map(i => solucion.costoRiegoTablon(i, finca, programacion))
        assert(resultado == esperado)
    }
    test("Costo de riego con tiempo de supervivencia igual al tiempo final") {
        val finca: solucion.Finca = Vector((5, 2, 3)) // ts = 5, tr = 2, p = 3
        val programacion: solucion.RiegoVector = Vector(0) // Solo un tablón
        val esperado = 3
        val resultado = solucion.costoRiegoTablon(0, finca, programacion)
        assert(resultado == esperado)
    }
    //TEST COSTO RIEGOFINCA
    test("Costo total de riego en finca pequeña sin penalización") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 1, 3)) // Tablones con ts, tr, p
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Riego en orden natural
        // Cálculo manual:
        // Tablón 0: costo = ts - tiempoFinal = 10 - (0 + 3) = 7
        // Tablón 1: costo = ts - tiempoFinal = 8 - (3 + 2) = 3
        // Tablón 2: costo = ts - tiempoFinal = 12 - (5 + 1) = 6
        val esperado = 7 + 3 + 6
        val resultado = solucion.costoRiegoFinca(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego en finca pequeña con penalización") {
        val finca: solucion.Finca = Vector((6, 3, 2), (5, 2, 3), (4, 1, 4)) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(1, 2, 0) // Orden desordenado
        val esperado = 4
        val resultado = solucion.costoRiegoFinca(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego para una finca con un solo tablón") {
        val finca: solucion.Finca = Vector((8, 3, 2)) // ts = 8, tr = 3, p = 2
        val programacion: solucion.RiegoVector = Vector(0) // Riego del único tablón
        // Cálculo manual:
        // Tablón 0: tiempoInicio = 0, tiempoFinal = 3, costo = ts - tiempoFinal = 8 - 3 = 5
        val esperado = 5
        val resultado = solucion.costoRiegoFinca(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego para finca con tiempos de riego acumulativos grandes") {
        val finca: solucion.Finca = Vector((20, 5, 3), (15, 6, 2), (25, 4, 4)) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Orden natural
        // Cálculo manual:
        // Tablón 0: tiempoInicio = 0, tiempoFinal = 5, costo = ts - tiempoFinal = 20 - 5 = 15
        // Tablón 1: tiempoInicio = 5, tiempoFinal = 11, costo = ts - tiempoFinal = 15 - 11 = 4
        // Tablón 2: tiempoInicio = 11, tiempoFinal = 15, costo = ts - tiempoFinal = 25 - 15 = 10
        val esperado = 15 + 4 + 10
        val resultado = solucion.costoRiegoFinca(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego en finca grande con penalizaciones mixtas") {
        val finca: solucion.Finca = Vector(
            (10, 2, 1), (7, 3, 2), (5, 2, 3), (12, 4, 1), (6, 1, 4)
        ) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(4, 3, 1, 2, 0) // Orden aleatorio
        val esperado = 31
        val resultado = solucion.costoRiegoFinca(finca, programacion)
        assert(resultado == esperado)
    }
    // TEST PARA FUNCION COSTORIEGOFINCAPAR
    test("Costo total de riego en finca pequeña sin penalización PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 1, 3)) // Tablones con ts, tr, p
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Riego en orden natural
        // Cálculo manual:
        // Tablón 0: costo = ts - tiempoFinal = 10 - (0 + 3) = 7
        // Tablón 1: costo = ts - tiempoFinal = 8 - (3 + 2) = 3
        // Tablón 2: costo = ts - tiempoFinal = 12 - (5 + 1) = 6
        val esperado = 7 + 3 + 6
        val resultado = solucion.costoRiegoFincaPar(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego en finca pequeña con penalización PAR") {
        val finca: solucion.Finca = Vector((6, 3, 2), (5, 2, 3), (4, 1, 4)) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(1, 2, 0) // Orden desordenado
        val esperado = 4
        val resultado = solucion.costoRiegoFincaPar(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego para una finca con un solo tablón PAR") {
        val finca: solucion.Finca = Vector((8, 3, 2)) // ts = 8, tr = 3, p = 2
        val programacion: solucion.RiegoVector = Vector(0) // Riego del único tablón
        // Cálculo manual:
        // Tablón 0: tiempoInicio = 0, tiempoFinal = 3, costo = ts - tiempoFinal = 8 - 3 = 5
        val esperado = 5
        val resultado = solucion.costoRiegoFincaPar(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego para finca con tiempos de riego acumulativos grandes PAR") {
        val finca: solucion.Finca = Vector((20, 5, 3), (15, 6, 2), (25, 4, 4)) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Orden natural
        // Cálculo manual:
        // Tablón 0: tiempoInicio = 0, tiempoFinal = 5, costo = ts - tiempoFinal = 20 - 5 = 15
        // Tablón 1: tiempoInicio = 5, tiempoFinal = 11, costo = ts - tiempoFinal = 15 - 11 = 4
        // Tablón 2: tiempoInicio = 11, tiempoFinal = 15, costo = ts - tiempoFinal = 25 - 15 = 10
        val esperado = 15 + 4 + 10
        val resultado = solucion.costoRiegoFincaPar(finca, programacion)
        assert(resultado == esperado)
    }
    test("Costo total de riego en finca grande con penalizaciones mixtas PAR") {
        val finca: solucion.Finca = Vector(
            (10, 2, 1), (7, 3, 2), (5, 2, 3), (12, 4, 1), (6, 1, 4)
        ) // ts, tr, p
        val programacion: solucion.RiegoVector = Vector(4, 3, 1, 2, 0) // Orden aleatorio
        val esperado = 31
        val resultado = solucion.costoRiegoFincaPar(finca, programacion)
        assert(resultado == esperado)
    }
    //TEST PARA FUNCION COSTOMOVILIDAD
    test("Costo de movilidad para finca pequeña con orden natural") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3))
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Orden natural
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 5),
            Vector(3, 0, 4),
            Vector(5, 4, 0)
        )
        // Cálculo manual:
        // Costo = d[0][1] + d[1][2] = 3 + 4 = 7
        val esperado = 7
        val resultado = solucion.costoMovilidad(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca pequeña con orden inverso") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3))
        val programacion: solucion.RiegoVector = Vector(2, 1, 0) // Orden inverso
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 5),
            Vector(3, 0, 4),
            Vector(5, 4, 0)
        )
        // Cálculo manual:
        // Costo = d[2][1] + d[1][0] = 4 + 3 = 7
        val esperado = 7
        val resultado = solucion.costoMovilidad(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca de 4 tablones con orden aleatorio") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3), (6, 1, 4))
        val programacion: solucion.RiegoVector = Vector(0, 2, 3, 1) // Orden aleatorio
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 4, 6),
            Vector(2, 0, 3, 5),
            Vector(4, 3, 0, 1),
            Vector(6, 5, 1, 0)
        )
        // Cálculo manual:
        // Costo = d[0][2] + d[2][3] + d[3][1] = 4 + 1 + 5 = 10
        val esperado = 10
        val resultado = solucion.costoMovilidad(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca con un solo tablón") {
        val finca: solucion.Finca = Vector((10, 3, 1)) // Solo un tablón
        val programacion: solucion.RiegoVector = Vector(0)
        val distancias: solucion.Distancia = Vector(
            Vector(0) // Matriz de 1x1
        )
        // Cálculo manual:
        // No hay movimientos, costo = 0
        val esperado = 0
        val resultado = solucion.costoMovilidad(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca de 5 tablones con distancias no uniformes") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3), (6, 1, 4), (14, 3, 2))
        val programacion: solucion.RiegoVector = Vector(3, 0, 4, 1, 2) // Orden arbitrario
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 5, 7, 3),
            Vector(2, 0, 6, 4, 8),
            Vector(5, 6, 0, 9, 1),
            Vector(7, 4, 9, 0, 2),
            Vector(3, 8, 1, 2, 0)
        )
        // Cálculo manual:
        // Costo = d[3][0] + d[0][4] + d[4][1] + d[1][2] = 7 + 3 + 8 + 6 = 24
        val esperado = 24
        val resultado = solucion.costoMovilidad(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    //TEST PARA FUNCION COSTOMOVILIDAD PAR
    test("Costo de movilidad para finca pequeña con orden natural PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3))
        val programacion: solucion.RiegoVector = Vector(0, 1, 2) // Orden natural
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 5),
            Vector(3, 0, 4),
            Vector(5, 4, 0)
        )
        // Cálculo manual:
        // Costo = d[0][1] + d[1][2] = 3 + 4 = 7
        val esperado = 7
        val resultado = solucion.costoMovilidadPar(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca pequeña con orden inverso PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3))
        val programacion: solucion.RiegoVector = Vector(2, 1, 0) // Orden inverso
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 5),
            Vector(3, 0, 4),
            Vector(5, 4, 0)
        )
        // Cálculo manual:
        // Costo = d[2][1] + d[1][0] = 4 + 3 = 7
        val esperado = 7
        val resultado = solucion.costoMovilidadPar(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca de 4 tablones con orden aleatorio PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3), (6, 1, 4))
        val programacion: solucion.RiegoVector = Vector(0, 2, 3, 1) // Orden aleatorio
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 4, 6),
            Vector(2, 0, 3, 5),
            Vector(4, 3, 0, 1),
            Vector(6, 5, 1, 0)
        )
        // Cálculo manual:
        // Costo = d[0][2] + d[2][3] + d[3][1] = 4 + 1 + 5 = 10
        val esperado = 10
        val resultado = solucion.costoMovilidadPar(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca con un solo tablón PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1)) // Solo un tablón
        val programacion: solucion.RiegoVector = Vector(0)
        val distancias: solucion.Distancia = Vector(
            Vector(0) // Matriz de 1x1
        )
        // Cálculo manual:
        // No hay movimientos, costo = 0
        val esperado = 0
        val resultado = solucion.costoMovilidadPar(finca, programacion, distancias)
        assert(resultado == esperado)
    }
    test("Costo de movilidad para finca de 5 tablones con distancias no uniformes PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2), (12, 4, 3), (6, 1, 4), (14, 3, 2))
        val programacion: solucion.RiegoVector = Vector(3, 0, 4, 1, 2) // Orden arbitrario
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 5, 7, 3),
            Vector(2, 0, 6, 4, 8),
            Vector(5, 6, 0, 9, 1),
            Vector(7, 4, 9, 0, 2),
            Vector(3, 8, 1, 2, 0)
        )
        // Cálculo manual:
        // Costo = d[3][0] + d[0][4] + d[4][1] + d[1][2] = 7 + 3 + 8 + 6 = 24
        val esperado = 24
        val resultado = solucion.costoMovilidadPar(finca, programacion, distancias)
        assert(resultado == esperado)

    }
    //TEST PARA GENERARPROGRAMACIONESRIEGO
    test("Generar programaciones de riego para finca con 1 tablón") {
        val finca: solucion.Finca = Vector((10, 2, 1)) // Solo un tablón
        val esperado: Vector[Vector[Int]] = Vector(Vector(0)) // Única permutación posible
        val resultado = solucion.generarProgramacionesRiego(finca)
        assert(resultado == esperado)
    }
    test("Generar programaciones de riego para finca con 2 tablones") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2)) // Dos tablones
        val esperado: Vector[Vector[Int]] = Vector(
            Vector(0, 1),
            Vector(1, 0)
        )
        val resultado = solucion.generarProgramacionesRiego(finca)
        assert(resultado.sorted == esperado.sorted)
    }
    test("Generar programaciones de riego para finca con 3 tablones") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2), (12, 1, 3)) // Tres tablones
        val esperado: Vector[Vector[Int]] = Vector(
            Vector(0, 1, 2),
            Vector(0, 2, 1),
            Vector(1, 0, 2),
            Vector(1, 2, 0),
            Vector(2, 0, 1),
            Vector(2, 1, 0)
        )
        val resultado = solucion.generarProgramacionesRiego(finca)
        assert(resultado.sorted == esperado.sorted)
    }
    test("Generar programaciones de riego para finca con 4 tablones") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2), (12, 1, 3), (6, 4, 4)) // Cuatro tablones
        val resultado = solucion.generarProgramacionesRiego(finca)
        val esperadoTamano = 24 // 4! = 4 * 3 * 2 * 1 = 24 permutaciones
        assert(resultado.length == esperadoTamano) // Verifica la cantidad de permutaciones
        assert(resultado.distinct.length == esperadoTamano) // Verifica que no haya duplicados
    }
    test("Generar programaciones de riego para finca vacía") {
        val finca: solucion.Finca = Vector() // Finca sin tablones
        val esperado: Vector[Vector[Int]] = Vector(Vector()) // Única permutación: la lista vacía
        val resultado = solucion.generarProgramacionesRiego(finca)
        assert(resultado == esperado)
    }
    //TEST PARA GENERARPROGRAMACIONESRIEGO PAR
    test("Generar programaciones de riego para finca con 1 tablón PAR") {
        val finca: solucion.Finca = Vector((10, 2, 1)) // Solo un tablón
        val esperado: Vector[Vector[Int]] = Vector(Vector(0)) // Única permutación posible
        val resultado = solucion.generarProgramacionesRiegoPar(finca)
        assert(resultado == esperado)
    }
    test("Generar programaciones de riego para finca con 2 tablones PAR") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2)) // Dos tablones
        val esperado: Vector[Vector[Int]] = Vector(
            Vector(0, 1),
            Vector(1, 0)
        )
        val resultado = solucion.generarProgramacionesRiegoPar(finca)
        assert(resultado.sorted == esperado.sorted)
    }
    test("Generar programaciones de riego para finca con 3 tablones PAR") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2), (12, 1, 3)) // Tres tablones
        val esperado: Vector[Vector[Int]] = Vector(
            Vector(0, 1, 2),
            Vector(0, 2, 1),
            Vector(1, 0, 2),
            Vector(1, 2, 0),
            Vector(2, 0, 1),
            Vector(2, 1, 0)
        )
        val resultado = solucion.generarProgramacionesRiegoPar(finca)
        assert(resultado.sorted == esperado.sorted)
    }
    test("Generar programaciones de riego para finca con 4 tablones PAR") {
        val finca: solucion.Finca = Vector((10, 2, 1), (8, 3, 2), (12, 1, 3), (6, 4, 4)) // Cuatro tablones
        val resultado = solucion.generarProgramacionesRiegoPar(finca)
        val esperadoTamano = 24 // 4! = 4 * 3 * 2 * 1 = 24 permutaciones
        assert(resultado.length == esperadoTamano) // Verifica la cantidad de permutaciones
        assert(resultado.distinct.length == esperadoTamano) // Verifica que no haya duplicados
    }
    test("Generar programaciones de riego para finca vacía PAR") {
        val finca: solucion.Finca = Vector() // Finca sin tablones
        val esperado: Vector[Vector[Int]] = Vector(Vector()) // Única permutación: la lista vacía
        val resultado = solucion.generarProgramacionesRiegoPar(finca)
        assert(resultado == esperado)
    }
    //TEST PARA FUNCION PROGRAMACIONRIEGOOPTIMO
    test("Programación óptima de riego para finca con 2 tablones") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2)) // ts, tr, p
        val distancias: solucion.Distancia = Vector(
            Vector(0, 4),
            Vector(4, 0)
        )
        val esperado: (solucion.RiegoVector, Int) = (Vector(0,1), 14) // Riego óptimo y costo total
        val resultado = solucion.ProgramacionRiegoOptimo(finca, distancias)
        assert(resultado == esperado)
    }
    test("Programación óptima de riego para finca con 3 tablones y distancias uniformes") {
        val finca: solucion.Finca = Vector((12, 3, 1), (10, 2, 2), (8, 1, 3))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 2),
            Vector(2, 0, 2),
            Vector(2, 2, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimo(finca, distancias)
        assert(resultado._2 > 0) // Solo verificamos que devuelva un costo válido
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
    }
    test("Programación óptima de riego para finca con 4 tablones con distancias variadas") {
        val finca: solucion.Finca = Vector((15, 4, 1), (12, 3, 2), (10, 2, 3), (8, 1, 4))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 6, 9),
            Vector(3, 0, 2, 4),
            Vector(6, 2, 0, 5),
            Vector(9, 4, 5, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimo(finca, distancias)
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
        assert(resultado._2 > 0) // Verifica que el costo sea positivo
    }
    test("Programación óptima de riego para finca con un solo tablón") {
        val finca: solucion.Finca = Vector((10, 3, 1)) // ts, tr, p
        val distancias: solucion.Distancia = Vector(
            Vector(0) // Solo un tablón
        )
        val esperado: (solucion.RiegoVector, Int) = (Vector(0), 7) // Costo = ts - tr
        val resultado = solucion.ProgramacionRiegoOptimo(finca, distancias)
        assert(resultado == esperado)
    }
    test("Programación óptima con penalizaciones altas debido a tiempos de riego tardíos") {
        val finca: solucion.Finca = Vector((6, 3, 5), (4, 2, 10), (5, 1, 8))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 5, 2),
            Vector(5, 0, 4),
            Vector(2, 4, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimo(finca, distancias)
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
        assert(resultado._2 > 0) // Asegura que el costo no sea negativo
    }
    //TEST PARA FUNCION PROGRAMACIONRIEGOOPTIMO PAR
    test("Programación óptima de riego para finca con 2 tablones PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1), (8, 2, 2)) // ts, tr, p
        val distancias: solucion.Distancia = Vector(
            Vector(0, 4),
            Vector(4, 0)
        )
        val esperado: (solucion.RiegoVector, Int) = (Vector(0,1), 14) // Riego óptimo y costo total
        val resultado = solucion.ProgramacionRiegoOptimoPar(finca, distancias)
        assert(resultado == esperado)
    }
    test("Programación óptima de riego para finca con 3 tablones y distancias uniformes PAR") {
        val finca: solucion.Finca = Vector((12, 3, 1), (10, 2, 2), (8, 1, 3))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 2, 2),
            Vector(2, 0, 2),
            Vector(2, 2, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimoPar(finca, distancias)
        assert(resultado._2 > 0) // Solo verificamos que devuelva un costo válido
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
    }
    test("Programación óptima de riego para finca con 4 tablones con distancias variadas PAR") {
        val finca: solucion.Finca = Vector((15, 4, 1), (12, 3, 2), (10, 2, 3), (8, 1, 4))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 3, 6, 9),
            Vector(3, 0, 2, 4),
            Vector(6, 2, 0, 5),
            Vector(9, 4, 5, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimoPar(finca, distancias)
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
        assert(resultado._2 > 0) // Verifica que el costo sea positivo
    }
    test("Programación óptima de riego para finca con un solo tablón PAR") {
        val finca: solucion.Finca = Vector((10, 3, 1)) // ts, tr, p
        val distancias: solucion.Distancia = Vector(
            Vector(0) // Solo un tablón
        )
        val esperado: (solucion.RiegoVector, Int) = (Vector(0), 7) // Costo = ts - tr
        val resultado = solucion.ProgramacionRiegoOptimoPar(finca, distancias)
        assert(resultado == esperado)
    }
    test("Programación óptima con penalizaciones altas debido a tiempos de riego tardíos PAR") {
        val finca: solucion.Finca = Vector((6, 3, 5), (4, 2, 10), (5, 1, 8))
        val distancias: solucion.Distancia = Vector(
            Vector(0, 5, 2),
            Vector(5, 0, 4),
            Vector(2, 4, 0)
        )
        val resultado = solucion.ProgramacionRiegoOptimoPar(finca, distancias)
        println(s"Programación óptima: ${resultado._1}, Costo: ${resultado._2}")
        assert(resultado._2 > 0) // Asegura que el costo no sea negativo
    }




}
