// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.language

enum class NumberLiteral(
    val value: Int,
    val literal: String
) {
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
    N_1000(1000, "mil")
}

fun getStringLiteral(value: Int, minValue: NumberLiteral, maxValue: NumberLiteral) =
    getLiteral(value, minValue, maxValue)?.literal ?: throw IllegalArgumentException(
        String.format(
            "No Literal found for value %s between %s and %s",
            value,
            minValue,
            maxValue
        )
    )


fun getLiteral(value: Int, minValue: NumberLiteral, maxValue: NumberLiteral) =
    NumberLiteral.values()
        .filter { value == it.value }
        .filter { minValue.value <= it.value }
        .firstOrNull { maxValue.value >= it.value }

fun Int.isEqualTo(numberLiteral: NumberLiteral) = this == numberLiteral.value

fun String.isEqualTo(numberLiteral: NumberLiteral) = this == numberLiteral.literal
