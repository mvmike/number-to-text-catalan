// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext;

import cat.mvmike.numbertotext.language.Literal;
import java.security.InvalidParameterException;
import java.util.Optional;

import static cat.mvmike.numbertotext.language.Literal.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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

        return getThousands(intPart)
                + getUnits(intPart, intPart == 0)
                + getCurrencyUnit(intPart, currency)
                + getDecimals(decimalPart)
                + getCurrencyCents(decimalPart, currency);
    }

    private static String getThousands(final int number) {
        int thousandPart = number / 1000;

        if (thousandPart == N_0.getNumber())
            return EMPTY;

        if (thousandPart == N_1.getNumber())
            return N_1000.getLiteral() + SPACE;

        return getUnits(thousandPart, false) + SPACE + N_1000.getLiteral()
                + (number % 1000 == 0 ? EMPTY : SPACE);
    }

    private static String getUnits(final int number, final boolean includeZero) {
        String tens = (number % 100 == 0 && !includeZero ? EMPTY : getTens(number % 100));
        String hundreds = getHundreds((number / 100) % 10);

        return isEmpty(hundreds) ? tens :
                isEmpty(tens) ? hundreds : hundreds + SPACE + tens;
    }

    private static String getHundreds(final int number) {

        Optional<Literal> optLiteral = getLiteral(number, N_1, N_9);
        if (optLiteral.isEmpty())
            return EMPTY;

        Literal literal = optLiteral.get();
        return literal == N_1 ?
                N_100.getLiteral() : literal.getLiteral() + DASH + N_100.getLiteral() + PLURAL;
    }

    private static String getTens(final int number) {

        if (number <= N_20.getNumber())
            return getLiteral(number, N_0, N_20).map(Literal::getLiteral).get();

        if (number <= N_30.getNumber())
            return N_20.getLiteral() + DASH + AND + DASH + getLiteral(number % 10, N_0, N_9).map(Literal::getLiteral).get();

        for (Literal literal : new Literal[]{N_90, N_80, N_70, N_60, N_50, N_40, N_30}) {
            if (number < literal.getNumber())
                continue;
            return literal.getLiteral() + (getLiteral(number % 10, N_1, N_9).map(l -> DASH + l.getLiteral()).orElse(EMPTY));
        }

        return EMPTY;
    }

    private static String getCurrencyUnit(final int number, final String currency) {
        return isEmpty(currency) ? EMPTY : SPACE + currency + (number % 10 != 1 ? PLURAL : EMPTY);
    }

    private static String getDecimals(final int number) {
        String decimalPart = getTens(number);
        return isEmpty(decimalPart) || N_0.getLiteral().equals(decimalPart) ? EMPTY :
                SPACE + DEC_SEPARATOR + SPACE + decimalPart;
    }

    private static String getCurrencyCents(final int number, final String currency) {
        if (number == 0 || isEmpty(currency))
            return EMPTY;

        return number == 1 ? SPACE + DEC_CURRENCY : SPACE + DEC_CURRENCY + PLURAL;
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
