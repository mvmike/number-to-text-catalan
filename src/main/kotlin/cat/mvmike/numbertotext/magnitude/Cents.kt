// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.DEC_CURRENCY
import cat.mvmike.numbertotext.language.Constants.DEC_SEPARATOR
import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.Constants.PLURAL
import cat.mvmike.numbertotext.language.Constants.SPACE
import cat.mvmike.numbertotext.language.NumberLiteral.N_0
import cat.mvmike.numbertotext.language.NumberLiteral.N_1
import cat.mvmike.numbertotext.language.isEqualTo

class Cents(
    private val number: Int
) {

    fun get(): String {
        val decimalPart = Tens(number).get()
        return when {
            decimalPart.isEmpty() -> EMPTY
            decimalPart.isEqualTo(N_0) -> EMPTY
            else -> SPACE + DEC_SEPARATOR + SPACE + decimalPart
        }
    }

    fun getCurrency(currency: String?) = when {
        currency.isNullOrEmpty() -> EMPTY
        number.isEqualTo(N_0) -> EMPTY
        number.isEqualTo(N_1) -> SPACE + DEC_CURRENCY
        else -> SPACE + DEC_CURRENCY + PLURAL
    }
}