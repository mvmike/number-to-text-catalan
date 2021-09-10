package cat.mvmike.numbertotext.language;

import java.util.Arrays;
import java.util.Optional;

import static java.lang.String.format;

public enum Number {

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

    private final int value;

    private final String literal;

    Number(final int value, final String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public static String getStringLiteral(final int value, final Number minValue, final Number maxValue) {
        return getLiteralOpt(value, minValue, maxValue)
                .orElseThrow(() -> new IllegalArgumentException(
                        format("No Literal found for value %s between %s and %s", value, minValue, maxValue)
                )).getLiteral();
    }

    public static Optional<Number> getLiteralOpt(final int value, final Number minValue, final Number maxValue) {
        return Arrays.stream(Number.values())
                .filter(number -> value == number.value)
                .filter(number -> minValue.value <= number.value)
                .filter(number -> maxValue.value >= number.value)
                .findFirst();
    }
}
