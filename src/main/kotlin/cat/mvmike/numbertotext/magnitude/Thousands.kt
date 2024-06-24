// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.Constants.SPACE
import cat.mvmike.numbertotext.language.NumberLiteral.N_0
import cat.mvmike.numbertotext.language.NumberLiteral.N_1
import cat.mvmike.numbertotext.language.NumberLiteral.N_1000
import cat.mvmike.numbertotext.language.isEqualTo

class Thousands(
    private val number: Int
) {

    fun get(): String {
        val thousandPart = number / N_1000.value

        return when {
            thousandPart.isEqualTo(N_0) -> EMPTY
            thousandPart.isEqualTo(N_1) -> N_1000.literal + SPACE
            else -> Units(thousandPart, false).get() + SPACE + N_1000.literal + getTrailingSpaceIfNeeded()
        }
    }

    private fun getTrailingSpaceIfNeeded() = when {
        number.isMultipleOfThousand() -> EMPTY
        else -> SPACE
    }
}

private fun Int.isMultipleOfThousand() = N_0.value == (this % N_1000.value)