import org.example.calcAvailableCargo
import org.example.formatOutput
import org.example.parseInput
import org.junit.jupiter.api.Test
import java.util.Scanner
import kotlin.test.assertEquals

class RailwayCargoTest {
    @Test
    fun `cycle of 3 that each do nothing`() {
        val input = """
            3 3
            0 0 0
            1 1 1
            2 2 2
            0 1
            1 2
            2 0
            0
        """.trimIndent()
        val expected = """
            0 0 1 2
            1 0 1 2
            2 0 1 2
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }

    @Test
    fun `cycle of 3 where each cargo only lasts 1 rail`() {
        val input = """
            3 3
            0 0 1
            1 1 2
            2 2 0
            0 1
            1 2
            2 0
            0
        """.trimIndent()
        val expected = """
            0 0
            1 1
            2 2
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }

    @Test
    fun `simple fork`() {
        val input = """
            4 4
            0 0 0
            1 1 1
            2 2 2
            3 3 3
            0 1
            0 2
            1 3
            2 3
            0
        """.trimIndent()
        val expected = """
            0
            1 0
            2 0
            3 0 1 2
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }

    @Test
    fun `loop of one`() {
        val input = """
            1 1
            0 0 0
            0 0
            0
        """.trimIndent()
        val expected = """
            0 0
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }

    @Test
    fun `disconnected stations`() {
        val input = """
            2 0
            0 0 0
            1 1 1
            0
        """.trimIndent()
        val expected = """
            0
            1
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }

    @Test
    fun `big test`() {
        val input = """
            12 18
            0 4 2
            1 4 5
            2 1 4
            3 2 3
            4 2 4
            5 1 2
            6 3 1
            7 5 3
            8 3 3
            9 4 1
            10 2 2
            11 1 5
            0 4
            0 5
            1 0
            2 1
            2 6
            3 2
            4 8
            5 1
            5 6
            5 9
            6 7
            6 10
            7 3
            7 11
            8 9
            9 10
            10 11
            11 10
            0
        """.trimIndent()
        val expected = """
            0 2 3 5
            1 2 3 4 5
            2 1 3 4
            3 1 2 3 4
            4 2 3 5
            5 2 3 5
            6 2 3 4 5
            7 1 2 4 5
            8 3 4 5
            9 2 3 4 5
            10 1 2 3 4 5
            11 1 2 3 4 5
           """.trimIndent()
        val obtained = formatOutput(calcAvailableCargo(parseInput(Scanner(input))))
        assertEquals(expected, obtained)
    }
}