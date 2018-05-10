// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike;

import java.security.InvalidParameterException;

import static cat.mvmike.Number.*;

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
     * @param number   (total amount, decimals are optional and are rounded up to 10^-2)
     * @param currency (applies to integers, decimals are always cents. Can be empty)
     * @return string associated value
     */
    public static String get(final double number, final String currency) {

        checkMaxSize((int) number);
        checkThousandFlag((int) number);
        int decimals = (int) (Math.round(number % 1 * 100.0));

        boolean hasCurrency = currency != null && !isEmptyString(currency);

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
            return unit.getLiteral();

        return THOUSAND_FLAG ? EMPTY : N_0.getLiteral();
    }

    private static String getBetweenTenAndTwenty(final int number) {

        Number unit = getNumber(number, 10, 19);
        return unit == null ? null : unit.getLiteral();
    }

    private static String tens(final int number) {

        if (number < N_10.getNumber())
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

        if (number >= current.getNumber() && number < current.getNumber() + N_10.getNumber()) {

            if (current == N_10) {
                NUM_LETTER = getBetweenTenAndTwenty(number);
                return;
            }

            NUM_LETTER = current.getLiteral();
            if (number > current.getNumber()) {

                if (current == N_20)
                    NUM_LETTER = (current.getLiteral() + DASH + AND + DASH).concat(units(number - current.getNumber()));
                else
                    NUM_LETTER = NUM_LETTER.concat(DASH).concat(units(number - current.getNumber()));
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

        int currentHundred = current.getNumber() * N_100.getNumber();

        if (number >= currentHundred && number < currentHundred + N_100.getNumber()) {

            if (current == N_1) {

                if (number == N_100.getNumber())
                    NUM_LETTER = N_100.getLiteral();
                else
                    NUM_LETTER = N_100.getLiteral().concat(SPACE).concat(tens(number - 100));

                return;
            }

            NUM_LETTER = (current.getLiteral() + DASH + N_100.getLiteral() + PLURAL);
            if (number > currentHundred)
                NUM_LETTER += (SPACE).concat(tens(number - currentHundred));
        }
    }

    private static String thousands(final int number) {

        if (number == N_1000.getNumber())
            return N_1000.getLiteral();

        if (number % N_1000.getNumber() == 0 && number < tenPow(4))
            return units(number / N_1000.getNumber()).concat(SPACE + N_1000.getLiteral());

        if (number >= N_1000.getNumber() && number < 2 * N_1000.getNumber())
            return (N_1000.getLiteral() + SPACE).concat(hundreds(number % N_1000.getNumber()));

        if (number >= 2 * N_1000.getNumber() && number < tenPow(4))
            return units(number / N_1000.getNumber()).concat(SPACE).concat(N_1000.getLiteral() + SPACE).concat(hundreds(number % N_1000.getNumber()));

        if (number < N_1000.getNumber())
            return hundreds(number);

        return EMPTY;
    }

    private static String tensOfThousands(final int number) {

        if (number == 0)
            NUM_LETTER_DM = tens(number / N_1000.getNumber());

        else if (number % tenPow(4) == 0)
            NUM_LETTER_DM = tens(number / N_1000.getNumber()).concat(SPACE + N_1000.getLiteral());

        else if (number > tenPow(4) && number < tenPow(5))
            NUM_LETTER_DM = tens(number / N_1000.getNumber())
                    .concat(SPACE + N_1000.getLiteral() + (isEmptyString(hundreds(number % N_1000.getNumber())) ? EMPTY : SPACE))
                    .concat(hundreds(number % N_1000.getNumber()));

        else if (number < tenPow(4))
            NUM_LETTER_DM = thousands(number);

        return NUM_LETTER_DM;
    }

    private static String hundredsOfThousands(final int number) {

        if (number == tenPow(5))
            NUM_LETTER_CM = N_100.getLiteral() + SPACE + N_1000.getLiteral();

        else if (number >= tenPow(5) && number < tenPow(6))
            NUM_LETTER_CM = hundreds(number / N_1000.getNumber())
                    .concat(SPACE + N_1000.getLiteral() + (isEmptyString(hundreds(number % N_1000.getNumber())) ? EMPTY : SPACE))
                    .concat(hundreds(number % N_1000.getNumber()));

        else if (number < tenPow(5))
            NUM_LETTER_CM = tensOfThousands(number);

        return NUM_LETTER_CM;
    }

    private static void checkMaxSize(final int number) {

        if (number >= MAX_VALUE)
            throw new InvalidParameterException(MAX_VALUE_ERROR);
    }

    private static void checkThousandFlag(final int number) {
        THOUSAND_FLAG = (number > N_1000.getNumber());
    }

    private static String addDecimals(String number, final int decimals, final boolean hasCurrency) {

        String num_decimals = tens(decimals);
        number += (SPACE).concat(DEC_SEPARATOR).concat(SPACE).concat(num_decimals);

        if (hasCurrency)
            number += (SPACE + DEC_CURRENCY);

        return number;
    }

    private static double tenPow(final int value) {
        return Math.pow(N_10.getNumber(), value);
    }

    private static boolean isEmptyString(final String str) {
        return str == null || "".equals(str);
    }
}
