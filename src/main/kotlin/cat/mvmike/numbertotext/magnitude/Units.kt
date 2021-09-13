// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.Constants.PLURAL
import cat.mvmike.numbertotext.language.Constants.SPACE
import cat.mvmike.numbertotext.language.NumberLiteral.*

class Units(
    private val number: Int,
    private val includeZero: Boolean = false
) {

    fun get(): String {
        val tens = when {
            number.isMultipleOfHundreds() && !includeZero -> EMPTY
            else -> Tens(number % N_100.value).get()
        }
        val hundreds = Hundreds((number / N_100.value) % N_10.value).get()

        return when {
            hundreds.isEmpty() -> tens
            tens.isEmpty() -> hundreds
            else -> hundreds + SPACE + tens
        }
    }

    fun getCurrency(currency: String?) = when {
        currency.isNullOrEmpty() -> EMPTY
        else -> SPACE + currency + addPluralIfNeeded()
    }

    private fun addPluralIfNeeded() = when (N_1.value) {
        number % N_10.value -> EMPTY
        else -> PLURAL
    }
}



