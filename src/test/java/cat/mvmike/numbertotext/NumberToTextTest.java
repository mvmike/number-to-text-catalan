// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext;

import java.util.stream.Stream;
import org.junit.Test;

import java.security.InvalidParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static cat.mvmike.numbertotext.NumberToText.MAX_VALUE;
import static cat.mvmike.numbertotext.NumberToText.MIN_VALUE;
import static cat.mvmike.numbertotext.NumberToText.MIN_VALUE_ERROR;
import static cat.mvmike.numbertotext.NumberToText.MAX_VALUE_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NumberToTextTest {

    @Test
    public void getShouldCheckMinValue() {

        NumberToText.get(MIN_VALUE, null);

        try {
            NumberToText.get(MIN_VALUE - 1, null);
            fail("should have exploded because of invalid value");
        } catch (InvalidParameterException ipe) {
            assertEquals(MIN_VALUE_ERROR, ipe.getMessage());
        }
    }

    @Test
    public void getShouldCheckMaxValue() {

        NumberToText.get(MAX_VALUE - 1, "");

        try {
            NumberToText.get(MAX_VALUE, "");
            fail("should have exploded because of invalid value");
        } catch (InvalidParameterException ipe) {
            assertEquals(MAX_VALUE_ERROR, ipe.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("expectedOutputForNumberAndCurrency")
    public void getShouldReturnCorrectAnswer(String expectedOutput, double number, String currency) {
        assertEquals(expectedOutput, NumberToText.get(number, currency));
    }

    private static Stream<Arguments> expectedOutputForNumberAndCurrency() {
        return Stream.of(
                Arguments.of("zero", 0, null),
                Arguments.of("zero euros", 0, "euro"),
                Arguments.of("zero euros amb vint-i-dos cèntims", 0.22, "euro"),
                Arguments.of("zero euros amb cinquanta cèntims", 0.5, "euro"),
                Arguments.of("un euro", 1, "euro"),
                Arguments.of("dos euros", 2, "euro"),
                Arguments.of("dos euros amb un cèntim", 2.01, "euro"),
                Arguments.of("dos euros amb dos cèntims", 2.02, "euro"),
                Arguments.of("un euro amb trenta-set cèntims", 1.37, "euro"),
                Arguments.of("vint-i-cinc euros amb noranta-dos cèntims", 25.92, "euro"),
                Arguments.of("seixanta-vuit euros amb seixanta-dos cèntims", 68.62, "euro"),
                Arguments.of("cent trenta-tres euros amb cinquanta cèntims", 133.50, "euro"),
                Arguments.of("sis-cents euros", 600, "euro"),
                Arguments.of("set-cents cinquanta-cinc amb tretze", 755.13, ""),
                Arguments.of("mil cent quinze euros amb seixanta-un cèntims", 1115.61, "euro"),
                Arguments.of("mil set-cents catorze euros", 1714, "euro"),
                Arguments.of("cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis", 55891.75513, null),
                Arguments.of("cent mil", 100000, null),
                Arguments.of("set-cents un mil seixanta euros amb deu cèntims", 701060.1, "euro"),
                Arguments.of("dos-cents trenta-cinc mil tres-cents seixanta-nou amb setanta-vuit", 235369.78, ""),
                Arguments.of("cinc-cents mil euros", 500000, "euro"),
                Arguments.of("set-cents mil euros amb deu cèntims", 700000.1, "euro"),
                Arguments.of("nou-cents noranta-nou mil nou-cents noranta-nou euros amb noranta-nou cèntims", 999999.99, "euro")
        );
    }
}
