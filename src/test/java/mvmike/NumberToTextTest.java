// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package mvmike;

import org.junit.Assert;
import org.junit.Test;

public class NumberToTextTest {

    @Test
    public void testInvalidLength() throws Exception {

        NumberToText.numberToText(999999, "");

        try {
            NumberToText.numberToText(1000000, "");
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandomNumbers() throws Exception {

        Assert.assertEquals(NumberToText.numberToText(0.5, "euro"), "zero euros amb cinquanta cèntims");

        Assert.assertEquals(NumberToText.numberToText(1, "euro"), "un euro");

        Assert.assertEquals(NumberToText.numberToText(1.37, "euro"), "un euro amb trenta-set cèntims");

        Assert.assertEquals(NumberToText.numberToText(25.92, "euro"), "vint-i-cinc euros amb noranta-dos cèntims");

        Assert.assertEquals(NumberToText.numberToText(68.62, "euro"), "seixanta-vuit euros amb seixanta-dos cèntims");

        Assert.assertEquals(NumberToText.numberToText(133.50, "euro"), "cent trenta-tres euros amb cinquanta cèntims");

        Assert.assertEquals(NumberToText.numberToText(755.13, ""), "set-cents cinquanta-cinc amb tretze");

        Assert.assertEquals(NumberToText.numberToText(1115.61, "euro"), "mil cent quinze euros amb seixanta-un cèntims");

        Assert.assertEquals(NumberToText.numberToText(1714, "euro"), "mil set-cents catorze euros");

        Assert.assertEquals(NumberToText.numberToText(55891.75513, ""), "cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis");

        Assert.assertEquals(NumberToText.numberToText(701060.1, "euro"), "set-cents un mil seixanta euros amb deu cèntims");
    }
}
