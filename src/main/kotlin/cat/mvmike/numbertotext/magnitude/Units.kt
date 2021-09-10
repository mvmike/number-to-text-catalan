// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext.magnitude

import cat.mvmike.numbertotext.language.Constants.EMPTY
import cat.mvmike.numbertotext.language.Constants.PLURAL
import cat.mvmike.numbertotext.language.Constants.SPACE

class Units(
    private val number: Int,
    private val includeZero: Boolean = false
) {

    fun get(): String {
        val tens = when {
            number.isMultipleOfHundreds() && !includeZero -> EMPTY
            else -> Tens(number % 100).get()
        }
        val hundreds = Hundreds((number / 100) % 10).get()

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

    private fun addPluralIfNeeded() = when {
        number % 10 == 1 -> EMPTY
        else -> PLURAL
    }
}



