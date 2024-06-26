// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.DASH
import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.Constants.PLURAL
import cat.mvmike.numbertotext.language.NumberLiteral
import cat.mvmike.numbertotext.language.NumberLiteral.N_0
import cat.mvmike.numbertotext.language.NumberLiteral.N_1
import cat.mvmike.numbertotext.language.NumberLiteral.N_100
import cat.mvmike.numbertotext.language.NumberLiteral.N_2
import cat.mvmike.numbertotext.language.NumberLiteral.N_3
import cat.mvmike.numbertotext.language.NumberLiteral.N_4
import cat.mvmike.numbertotext.language.NumberLiteral.N_5
import cat.mvmike.numbertotext.language.NumberLiteral.N_6
import cat.mvmike.numbertotext.language.NumberLiteral.N_7
import cat.mvmike.numbertotext.language.NumberLiteral.N_8
import cat.mvmike.numbertotext.language.NumberLiteral.N_9
import cat.mvmike.numbertotext.language.getLiteral

class Hundreds(
    private val number: Int
) {

    fun get() = getLiteral(number, N_1, N_9)
        ?.toFormattedString()
        ?: EMPTY

    private fun NumberLiteral.toFormattedString() = when (this) {
        N_1 -> N_100.literal
        N_2,
        N_3,
        N_4,
        N_5,
        N_6,
        N_7,
        N_8,
        N_9 -> literal + DASH + N_100.literal + PLURAL
        else -> throw UnsupportedOperationException()
    }
}

fun Int.modOfHundred() = this % N_100.value

fun Int.isMultipleOfHundreds() = N_0.value == this.modOfHundred()