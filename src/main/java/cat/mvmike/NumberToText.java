// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike;

import static cat.mvmike.NumberToText.Number.*;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;

public class NumberToText {

    static final int MAX_VALUE = 1000000;

    static final String MAX_VALUE_ERROR = "Number out of range. Max order of magnitude = " + MAX_VALUE;

    private static final String AND = "i";

    private static final String PLURAL = "s";

    private static final String DASH = "-";

    private static final String SPACE = " ";

    private static final String EMPTY = "";

    private static final String DEC_SEPARATOR = "amb";

    private static final String DEC_CURRENCY = "cèntims";

    private static String NUM_LETTER;

    private static String NUM_LETTER_DM;

    private static String NUM_LETTER_CM;

    private static boolean THOUSAND_FLAG;

    /**
     * Converts number to text (Catalan language). Please note that decimals are optional and max precision is set up to 10^-2
     *
     * @param number (total amount, decimals are optional and are rounded up to 10^-2)
     * @param currency (applies to integers, decimals are always cents. Can be empty)
     * @return string associated value
     */
    public static String get(final double number, final String currency) {

        checkMaxSize((int) number);
        checkThousandFlag((int) number);
        int decimals = (int) (Math.round(number % 1 * 100.0));

        boolean hasCurrency = currency != null && !StringUtils.isEmpty(currency);

        String result = hundredsOfThousands((int) number);

        if (hasCurrency) {
            result += (SPACE + currency);

            if ((int) number != 1)
                result += PLURAL;
        }

        if (decimals > 0)
            result = addDecimals(result, decimals, hasCurrency);

        return result;
    }

    private static String units(final int number) {

        Number unit = getNumber(number, 1, 9);
        if (unit != null)
            return unit.literal;

        return THOUSAND_FLAG ? EMPTY : N_0.literal;
    }

    private static String getBetweenTenAndTwenty(final int number) {

        Number unit = getNumber(number, 10, 19);
        return unit == null ? null : unit.literal;
    }

    private static String tens(final int number) {

        if (number < N_10.number)
            NUM_LETTER = units(number);

        checkTens(N_90, number);
        checkTens(N_80, number);
        checkTens(N_70, number);
        checkTens(N_60, number);
        checkTens(N_50, number);
        checkTens(N_40, number);
        checkTens(N_30, number);
        checkTens(N_20, number);
        checkTens(N_10, number);

        return NUM_LETTER;
    }

    private static void checkTens(final Number current, final int number) {

        if (number >= current.number && number < current.number + N_10.number) {

            if (current == N_10) {
                NUM_LETTER = getBetweenTenAndTwenty(number);
                return;
            }

            NUM_LETTER = current.literal;
            if (number > current.number) {

                if (current == N_20)
                    NUM_LETTER = (current.literal + DASH + AND + DASH).concat(units(number - current.number));
                else
                    NUM_LETTER = NUM_LETTER.concat(DASH).concat(units(number - current.number));
            }

        }
    }

    private static String hundreds(final int number) {

        if (number < 100)
            NUM_LETTER = tens(number);

        checkHundreds(N_9, number);
        checkHundreds(N_8, number);
        checkHundreds(N_7, number);
        checkHundreds(N_6, number);
        checkHundreds(N_5, number);
        checkHundreds(N_4, number);
        checkHundreds(N_3, number);
        checkHundreds(N_2, number);
        checkHundreds(N_1, number);

        return NUM_LETTER;
    }

    private static void checkHundreds(final Number current, final int number) {

        int currentHundred = current.number * N_100.number;

        if (number >= currentHundred && number < currentHundred + N_100.number) {

            if (current == N_1) {

                if (number == N_100.number)
                    NUM_LETTER = N_100.literal;
                else
                    NUM_LETTER = N_100.literal.concat(SPACE).concat(tens(number - 100));

                return;
            }

            NUM_LETTER = (current.literal + DASH + N_100.literal + PLURAL);
            if (number > currentHundred)
                NUM_LETTER += (SPACE).concat(tens(number - currentHundred));
        }
    }

    private static String thousands(final int number) {

        if (number == N_1000.number)
            return N_1000.literal;

        if (number % N_1000.number == 0 && number < tenPow(4))
            return units(number / N_1000.number).concat(SPACE + N_1000.literal);

        if (number >= N_1000.number && number < 2 * N_1000.number)
            return (N_1000.literal + SPACE).concat(hundreds(number % N_1000.number));

        if (number >= 2 * N_1000.number && number < tenPow(4))
            return units(number / N_1000.number).concat(SPACE).concat(N_1000.literal + SPACE).concat(hundreds(number % N_1000.number));

        if (number < N_1000.number)
            return hundreds(number);

        return EMPTY;
    }

    private static String tensOfThousands(final int number) {

        if (number == 0)
            NUM_LETTER_DM = tens(number / N_1000.number);

        else if (number % tenPow(4) == 0)
            NUM_LETTER_DM = tens(number / N_1000.number).concat(SPACE + N_1000.literal);

        else if (number > tenPow(4) && number < tenPow(5))
            NUM_LETTER_DM = tens(number / N_1000.number)
                .concat(SPACE + N_1000.literal + (StringUtils.isEmpty(hundreds(number % N_1000.number)) ? EMPTY : SPACE))
                .concat(hundreds(number % N_1000.number));

        else if (number < tenPow(4))
            NUM_LETTER_DM = thousands(number);

        return NUM_LETTER_DM;
    }

    private static String hundredsOfThousands(final int number) {

        if (number == tenPow(5))
            NUM_LETTER_CM = N_100.literal + SPACE + N_1000.literal;

        else if (number >= tenPow(5) && number < tenPow(6))
            NUM_LETTER_CM = hundreds(number / N_1000.number)
                .concat(SPACE + N_1000.literal + (StringUtils.isEmpty(hundreds(number % N_1000.number)) ? EMPTY : SPACE))
                .concat(hundreds(number % N_1000.number));

        else if (number < tenPow(5))
            NUM_LETTER_CM = tensOfThousands(number);

        return NUM_LETTER_CM;
    }

    private static void checkMaxSize(final int number) {

        if (number >= MAX_VALUE)
            throw new InvalidParameterException(MAX_VALUE_ERROR);
    }

    private static void checkThousandFlag(final int number) {
        THOUSAND_FLAG = (number > N_1000.number);
    }

    private static String addDecimals(String number, final int decimals, final boolean hasCurrency) {

        String num_decimals = tens(decimals);
        number += (SPACE).concat(DEC_SEPARATOR).concat(SPACE).concat(num_decimals);

        if (hasCurrency)
            number += (SPACE + DEC_CURRENCY);

        return number;
    }

    private static double tenPow(final int value) {
        return Math.pow(N_10.number, value);
    }

    protected enum Number {

        N_0(0, "zero"),

        N_1(1, "un"),

        N_2(2, "dos"),

        N_3(3, "tres"),

        N_4(4, "quatre"),

        N_5(5, "cinc"),

        N_6(6, "sis"),

        N_7(7, "set"),

        N_8(8, "vuit"),

        N_9(9, "nou"),

        N_10(10, "deu"),

        N_11(11, "onze"),

        N_12(12, "dotze"),

        N_13(13, "tretze"),

        N_14(14, "catorze"),

        N_15(15, "quinze"),

        N_16(16, "setze"),

        N_17(17, "disset"),

        N_18(18, "divuit"),

        N_19(19, "dinou"),

        N_20(20, "vint"),

        N_30(30, "trenta"),

        N_40(40, "quaranta"),

        N_50(50, "cinquanta"),

        N_60(60, "seixanta"),

        N_70(70, "setanta"),

        N_80(80, "vuitanta"),

        N_90(90, "noranta"),

        N_100(100, "cent"),

        N_1000(1000, "mil");

        private int number;

        private String literal;

        Number(final int number, final String literal) {

            this.number = number;
            this.literal = literal;
        }

        public static Number getNumber(final int value, final int minValue, final int maxValue) {

            for (Number number : Number.values()) {

                if (value == number.number && number.number >= minValue && number.number <= maxValue)
                    return number;
            }

            return null;
        }
    }
}
