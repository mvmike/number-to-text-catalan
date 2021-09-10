// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.AND
import cat.mvmike.numbertotext.language.Constants.DASH
import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.NumberLiteral
import cat.mvmike.numbertotext.language.NumberLiteral.*
import cat.mvmike.numbertotext.language.getStringLiteral

class Tens(private val number: Int) {

    fun get() = when {
        number <= N_20.value -> getStringLiteral(number, N_0, N_20)
        number <= N_30.value -> N_20.literal + DASH + AND + DASH + getStringLiteral(number % N_10.value, N_0, N_9)
        else -> arrayOf(N_90, N_80, N_70, N_60, N_50, N_40, N_30)
            .firstOrNull { number >= it.value }
            ?.toFormattedString()
            ?: EMPTY
    }

    private fun NumberLiteral.toFormattedString() = when {
        number.isMultipleOfTens() -> this.literal
        else -> this.literal + DASH + getStringLiteral(number % N_10.value, N_1, N_9)
    }
}

fun Int.isMultipleOfTens() = N_0.value == (this % N_10.value)




