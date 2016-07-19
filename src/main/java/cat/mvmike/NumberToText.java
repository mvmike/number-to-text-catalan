// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike;

import org.apache.commons.lang3.StringUtils;

public class NumberToText {

    private static final String ERROR_NUM = "Number out of range. Max order of magnitude = 10^5";

    private static final String AND = "i";
    private static final String PLURAL = "s";
    private static final String DASH = "-";
    private static final String SPACE = " ";
    private static final String DEC_SEPARATOR = "amb";
    private static final String DEC_CURRENCY = "cèntims";

    private static final String N_0 = "zero";
    private static final String N_1 = "un";
    private static final String N_2 = "dos";
    private static final String N_3 = "tres";
    private static final String N_4 = "quatre";
    private static final String N_5 = "cinc";
    private static final String N_6 = "sis";
    private static final String N_7 = "set";
    private static final String N_8 = "vuit";
    private static final String N_9 = "nou";

    private static final String N_10 = "deu";
    private static final String N_11 = "onze";
    private static final String N_12 = "dotze";
    private static final String N_13 = "tretze";
    private static final String N_14 = "catorze";
    private static final String N_15 = "quinze";
    private static final String N_16 = "setze";
    private static final String N_17 = "disset";
    private static final String N_18 = "divuit";
    private static final String N_19 = "dinou";
    private static final String N_20 = "vint";
    private static final String N_30 = "trenta";
    private static final String N_40 = "quaranta";
    private static final String N_50 = "cinquanta";
    private static final String N_60 = "seixanta";
    private static final String N_70 = "setanta";
    private static final String N_80 = "vuitanta";
    private static final String N_90 = "noranta";

    private static final String N_100 = "cent";
    private static final String N_1000 = "mil";

    private static String NUM_LETTER;
    private static String NUM_LETTER_DM;
    private static String NUM_LETTER_CM;

    private static boolean THOUSAND_FLAG;

    /**
     * Converts number to text (Catalan language). Please note that decimals are optional and rounded up to 10^-2
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

        String result = centMilers((int) number);

        if (hasCurrency) {
            result += (SPACE + currency);

            if ((int) number != 1)
                result += PLURAL;
        }

        if (decimals > 0)
            result = addDecimals(result, decimals, hasCurrency);

        return result;
    }

    private static String unitat(final int number) {

        switch (number) {

            case 9:
                return N_9;
            case 8:
                return N_8;
            case 7:
                return N_7;
            case 6:
                return N_6;
            case 5:
                return N_5;
            case 4:
                return N_4;
            case 3:
                return N_3;
            case 2:
                return N_2;
            case 1:
                return N_1;

            default:

                if (THOUSAND_FLAG)
                    return "";

                return N_0;
        }
    }

    private static String getBetweenTenAndTwenty(final int number) {

        switch (number) {

            case 10:
                return N_10;
            case 11:
                return N_11;
            case 12:
                return N_12;
            case 13:
                return N_13;
            case 14:
                return N_14;
            case 15:
                return N_15;
            case 16:
                return N_16;
            case 17:
                return N_17;
            case 18:
                return N_18;
            case 19:
                return N_19;
        }

        return null;
    }

    private static String desena(final int number) {

        if (number >= 90 && number <= 99) {
            NUM_LETTER = N_90;
            if (number > 90)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 90));

        } else if (number >= 80 && number <= 89) {
            NUM_LETTER = N_80;
            if (number > 80)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 80));

        } else if (number >= 70 && number <= 79) {
            NUM_LETTER = N_70;
            if (number > 70)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 70));

        } else if (number >= 60 && number <= 69) {
            NUM_LETTER = N_60;
            if (number > 60)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 60));

        } else if (number >= 50 && number <= 59) {
            NUM_LETTER = N_50;
            if (number > 50)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 50));

        } else if (number >= 40 && number <= 49) {
            NUM_LETTER = N_40;
            if (number > 40)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 40));

        } else if (number >= 30 && number <= 39) {
            NUM_LETTER = N_30;
            if (number > 30)
                NUM_LETTER = NUM_LETTER.concat(DASH).concat(unitat(number - 30));

        } else if (number >= 20 && number <= 29) {
            if (number == 20)
                NUM_LETTER = N_20;
            else
                NUM_LETTER = (N_20 + DASH + AND + DASH).concat(unitat(number - 20));

        } else if (number >= 10 && number <= 19) {

            NUM_LETTER = getBetweenTenAndTwenty(number);

        } else {
            NUM_LETTER = unitat(number);
        }

        return NUM_LETTER;
    }

    private static String centenars(final int number) {

        if (number >= 100) {

            if (number >= 900 && number <= 999) {

                NUM_LETTER = (N_9 + DASH + N_100 + PLURAL);
                if (number > 900)
                    NUM_LETTER += (SPACE).concat(desena(number - 900));

            } else if (number >= 800 && number <= 899) {

                NUM_LETTER = (N_8 + DASH + N_100 + PLURAL);
                if (number > 800)
                    NUM_LETTER += (SPACE).concat(desena(number - 800));

            } else if (number >= 700 && number <= 799) {

                NUM_LETTER = (N_7 + DASH + N_100 + PLURAL);
                if (number > 700)
                    NUM_LETTER += (SPACE).concat(desena(number - 700));

            } else if (number >= 600 && number <= 699) {

                NUM_LETTER = (N_6 + DASH + N_100 + PLURAL);
                if (number > 600)
                    NUM_LETTER += (SPACE).concat(desena(number - 600));

            } else if (number >= 500 && number <= 599) {

                NUM_LETTER = (N_5 + DASH + N_100 + PLURAL);
                if (number > 500)
                    NUM_LETTER += (SPACE).concat(desena(number - 500));

            } else if (number >= 400 && number <= 499) {

                NUM_LETTER = (N_4 + DASH + N_100 + PLURAL);
                if (number > 400)
                    NUM_LETTER += (SPACE).concat(desena(number - 400));

            } else if (number >= 300 && number <= 399) {

                NUM_LETTER = (N_3 + DASH + N_100 + PLURAL);
                if (number > 300)
                    NUM_LETTER += (SPACE).concat(desena(number - 300));

            } else if (number >= 200 && number <= 299) {

                NUM_LETTER = (N_2 + DASH + N_100 + PLURAL);
                if (number > 200)
                    NUM_LETTER += (SPACE).concat(desena(number - 200));

            } else if (number >= 100 && number <= 199) {

                if (number == 100)
                    NUM_LETTER = N_100;
                else
                    NUM_LETTER = N_100.concat(SPACE).concat(desena(number - 100));
            }
        } else {
            NUM_LETTER = desena(number);
        }

        return NUM_LETTER;
    }

    private static String milers(final int number) {

        if (number == 1000)
            return N_1000;

        if (number % 1000 == 0 && number < 10000)
            return unitat(number / 1000).concat(SPACE + N_1000);

        if (number >= 1000 && number < 2000)
            return (N_1000 + SPACE).concat(centenars(number % 1000));

        if (number >= 2000 && number < 10000)
            return unitat(number / 1000).concat(SPACE).concat(N_1000 + SPACE).concat(centenars(number % 1000));

        if (number < 1000)
            return centenars(number);

        return "";
    }

    private static String decMilers(final int number) {

        if (number == 0)
            NUM_LETTER_DM = desena(number / 1000);

        else if (number % 10000 == 0)
            NUM_LETTER_DM = desena(number / 1000).concat(SPACE + N_1000);

        else if (number > 10000 && number < 100000)
            NUM_LETTER_DM = desena(number / 1000).concat(SPACE + N_1000 + (StringUtils.isEmpty(centenars(number % 1000)) ? "" : SPACE))
                .concat(centenars(number % 1000));

        else if (number < 10000)
            NUM_LETTER_DM = milers(number);

        return NUM_LETTER_DM;
    }

    private static String centMilers(final int number) {

        if (number == 100000)
            NUM_LETTER_CM = N_100 + SPACE + N_1000;

        else if (number >= 100000 && number < 1000000)
            NUM_LETTER_CM = centenars(number / 1000).concat(SPACE + N_1000 + (StringUtils.isEmpty(centenars(number % 1000)) ? "" : SPACE))
                .concat(centenars(number % 1000));

        else if (number < 100000)
            NUM_LETTER_CM = decMilers(number);


        return NUM_LETTER_CM;
    }

    private static void checkMaxSize(final int number) {

        if (number >= 1000000)
            throw new RuntimeException(ERROR_NUM);
    }

    private static void checkThousandFlag(final int number) {
        THOUSAND_FLAG = (number > 1000);
    }

    private static String addDecimals(String number, final int decimals, final boolean hasCurrency) {

        String num_decimals = desena(decimals);
        number += (SPACE).concat(DEC_SEPARATOR).concat(SPACE).concat(num_decimals);

        if (hasCurrency)
            number += (SPACE + DEC_CURRENCY);

        return number;
    }
}
