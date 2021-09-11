// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.security.InvalidParameterException
import java.util.stream.Stream

class NumberLiteralToTextTest {

    @Test
    fun shouldCheckMinValue() {
        NumberToText.get(NumberToText.MIN_VALUE.toDouble())
        val exception = assertThrows<InvalidParameterException> {
            NumberToText.get((NumberToText.MIN_VALUE - 1).toDouble())
        }

        assertThat(exception.message).isEqualTo(NumberToText.MIN_VALUE_ERROR)
    }

    @Test
    fun shouldCheckMaxValue() {
        NumberToText.get((NumberToText.MAX_VALUE - 1).toDouble())
        val exception = assertThrows<InvalidParameterException> {
            NumberToText.get(NumberToText.MAX_VALUE.toDouble())
        }
        assertThat(exception.message).isEqualTo(NumberToText.MAX_VALUE_ERROR)
    }

    @ParameterizedTest
    @MethodSource("getAmountsAndCurrenciesWithExpectedOutput")
    fun getShouldReturnCorrectAnswer(testProperties: TestProperties) {
        val result = NumberToText.get(testProperties.amount, testProperties.currency)

        assertThat(result).isEqualTo(testProperties.expectedOutput)
    }

    companion object {

        @JvmStatic
        fun getAmountsAndCurrenciesWithExpectedOutput(): Stream<TestProperties> = Stream.of(
            TestProperties(
                amount = 0.0,
                currency = null,
                expectedOutput = "zero"),
            TestProperties(
                amount = 0.0,
                currency = "euro",
                expectedOutput = "zero euros"
            ),
            TestProperties(
                amount = 0.22,
                currency = "euro",
                expectedOutput = "zero euros amb vint-i-dos cèntims"
            ),
            TestProperties(
                amount = 0.57,
                currency = null,
                expectedOutput = "zero amb cinquanta-set"
            ),
            TestProperties(
                amount = 0.5,
                currency = "euro",
                expectedOutput = "zero euros amb cinquanta cèntims"
            ),
            TestProperties(
                amount = 1.0,
                currency = "euro",
                expectedOutput = "un euro"
            ),
            TestProperties(
                amount = 2.0,
                currency = "euro",
                expectedOutput = "dos euros"
            ),
            TestProperties(
                amount = 2.01,
                currency = "euro",
                expectedOutput = "dos euros amb un cèntim"
            ),
            TestProperties(
                amount = 2.02,
                currency = "euro",
                expectedOutput = "dos euros amb dos cèntims"
            ),
            TestProperties(
                amount = 1.37,
                currency = "euro",
                expectedOutput = "un euro amb trenta-set cèntims"
            ),
            TestProperties(
                amount = 25.92,
                currency = "euro",
                expectedOutput = "vint-i-cinc euros amb noranta-dos cèntims"
            ),
            TestProperties(
                amount = 68.62,
                currency = "euro",
                expectedOutput = "seixanta-vuit euros amb seixanta-dos cèntims"
            ),
            TestProperties(
                amount = 133.50,
                currency = "euro",
                expectedOutput = "cent trenta-tres euros amb cinquanta cèntims"
            ),
            TestProperties(
                amount = 600.0,
                currency = "euro",
                expectedOutput = "sis-cents euros"
            ),
            TestProperties(
                amount = 755.13,
                currency = "",
                expectedOutput = "set-cents cinquanta-cinc amb tretze"
            ),
            TestProperties(
                amount = 1115.61,
                currency = "euro",
                expectedOutput = "mil cent quinze euros amb seixanta-un cèntims"
            ),
            TestProperties(
                amount = 1714.0,
                currency = "euro",
                expectedOutput = "mil set-cents catorze euros"
            ),
            TestProperties(
                amount = 55891.75513,
                currency = null,
                expectedOutput = "cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis"
            ),
            TestProperties(
                amount = 100000.0,
                currency = null,
                expectedOutput = "cent mil"
            ),
            TestProperties(
                amount = 701060.1,
                currency = "euro",
                expectedOutput = "set-cents un mil seixanta euros amb deu cèntims"
            ),
            TestProperties(
                amount = 235369.78,
                currency = "",
                expectedOutput = "dos-cents trenta-cinc mil tres-cents seixanta-nou amb setanta-vuit"
            ),
            TestProperties(
                amount = 500000.0,
                currency = "euro",
                expectedOutput = "cinc-cents mil euros"
            ),
            TestProperties(
                amount = 700000.1,
                currency = "euro",
                expectedOutput = "set-cents mil euros amb deu cèntims"
            ),
            TestProperties(
                amount = 999999.99,
                currency = "euro",
                expectedOutput = "nou-cents noranta-nou mil nou-cents noranta-nou euros amb noranta-nou cèntims"
            )
        )
    }

    data class TestProperties(
        val amount: Double,
        val currency: String?,
        val expectedOutput: String
    )
}
