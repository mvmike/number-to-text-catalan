// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike;

import org.junit.Test;

import java.security.InvalidParameterException;

import static cat.mvmike.NumberToText.MAX_VALUE;
import static cat.mvmike.NumberToText.MAX_VALUE_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NumberToTextTest {

    @Test
    public void testInvalidLength() {

        NumberToText.get(MAX_VALUE - 1, "");

        try {
            NumberToText.get(MAX_VALUE, "");
            fail("should have exploded because of invalid length");

        } catch (InvalidParameterException ipe) {
            assertEquals(MAX_VALUE_ERROR, ipe.getMessage());
        }
    }

    @Test
    public void testRandomNumbers() throws Exception {

        assertEquals("zero euros amb cinquanta cèntims", NumberToText.get(0.5, "euro"));

        assertEquals("un euro", NumberToText.get(1, "euro"));

        assertEquals("un euro amb trenta-set cèntims", NumberToText.get(1.37, "euro"));

        assertEquals("vint-i-cinc euros amb noranta-dos cèntims", NumberToText.get(25.92, "euro"));

        assertEquals("seixanta-vuit euros amb seixanta-dos cèntims", NumberToText.get(68.62, "euro"));

        assertEquals("cent trenta-tres euros amb cinquanta cèntims", NumberToText.get(133.50, "euro"));

        assertEquals("set-cents cinquanta-cinc amb tretze", NumberToText.get(755.13, ""));

        assertEquals("mil cent quinze euros amb seixanta-un cèntims", NumberToText.get(1115.61, "euro"));

        assertEquals("mil set-cents catorze euros", NumberToText.get(1714, "euro"));

        assertEquals("cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis", NumberToText.get(55891.75513, ""));

        assertEquals("set-cents un mil seixanta euros amb deu cèntims", NumberToText.get(701060.1, "euro"));
    }
}
