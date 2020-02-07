package cat.mvmike.numbertotext.language;

import java.util.Arrays;
import java.util.Optional;

public enum Literal {

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

    public static final String AND = "i";

    public static final String PLURAL = "s";

    public static final String DASH = "-";

    public static final String SPACE = " ";

    public static final String EMPTY = "";

    public static final String DEC_SEPARATOR = "amb";

    public static final String DEC_CURRENCY = "cèntim";

    private int number;

    private String literal;

    Literal(final int number, final String literal) {

        this.number = number;
        this.literal = literal;
    }

    public static Optional<Literal> getLiteral(final int value, final Literal minValue, final Literal maxValue) {
        return Arrays.stream(Literal.values())
                .filter(number -> value == number.number)
                .filter(number -> minValue.number <= number.number)
                .filter(number -> maxValue.number >= number.number)
                .findFirst();
    }

    public int getNumber() {
        return number;
    }

    public String getLiteral() {
        return literal;
    }
}
