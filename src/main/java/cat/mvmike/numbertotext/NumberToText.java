// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext;

import cat.mvmike.numbertotext.magnitude.Cents;
import cat.mvmike.numbertotext.magnitude.Thousands;
import cat.mvmike.numbertotext.magnitude.Units;

import java.security.InvalidParameterException;

public class NumberToText {

    static final int MIN_VALUE = 0;

    static final int MAX_VALUE = 1_000_000;

    static final String MIN_VALUE_ERROR = "Literal out of range. Min value = " + MIN_VALUE;

    static final String MAX_VALUE_ERROR = "Literal out of range. Max value = " + MAX_VALUE;

    /**
     * Converts number to text (Catalan language). Please note that decimals are optional and max precision is set up
     * to 10^-2
     *
     * @param number   (total amount, decimals are optional and are rounded up to 10^-2)
     * @param currency (applies to integers, decimals are always cents. Can be empty)
     * @return string associated value
     */
    public static String get(final double number, final String currency) {

        // initial validations
        checkMinSize(number);
        checkMaxSize(number);

        int intPart = (int) number;
        int decimalPart = (int) Math.round((number - intPart) * 100);

        return Thousands.get(intPart)
                + new Units(intPart, intPart == 0).get()
                + new Units(intPart).getCurrency(currency)
                + new Cents(decimalPart).get()
                + new Cents(decimalPart).getCurrency(currency);
    }

    private static void checkMinSize(final double number) {
        if (number < MIN_VALUE)
            throw new InvalidParameterException(MIN_VALUE_ERROR);
    }

    private static void checkMaxSize(final double number) {
        if (number >= MAX_VALUE)
            throw new InvalidParameterException(MAX_VALUE_ERROR);
    }
}
