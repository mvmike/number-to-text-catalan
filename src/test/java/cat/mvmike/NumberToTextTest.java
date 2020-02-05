// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike;

import java.util.stream.Stream;
import org.junit.Test;

import java.security.InvalidParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static cat.mvmike.NumberToText.MAX_VALUE;
import static cat.mvmike.NumberToText.MAX_VALUE_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NumberToTextTest {

    @Test
    public void getShouldCheckMaxValue() {

        NumberToText.get(MAX_VALUE - 1, "");

        try {
            NumberToText.get(MAX_VALUE, "");
            fail("should have exploded because of invalid length");
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
                Arguments.of("zero euros amb cinquanta cèntims", 0.5, "euro"),
                Arguments.of("un euro", 1, "euro"),
                Arguments.of("un euro amb trenta-set cèntims", 1.37, "euro"),
                Arguments.of("vint-i-cinc euros amb noranta-dos cèntims", 25.92, "euro"),
                Arguments.of("seixanta-vuit euros amb seixanta-dos cèntims", 68.62, "euro"),
                Arguments.of("cent trenta-tres euros amb cinquanta cèntims", 133.50, "euro"),
                Arguments.of("set-cents cinquanta-cinc amb tretze", 755.13, ""),
                Arguments.of("mil cent quinze euros amb seixanta-un cèntims", 1115.61, "euro"),
                Arguments.of("mil set-cents catorze euros", 1714, "euro"),
                Arguments.of("cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis", 55891.75513, ""),
                Arguments.of("set-cents un mil seixanta euros amb deu cèntims", 701060.1, "euro")
        );
    }
}
