// Copyright (c) 2016, Miquel Martí <miquelmarti111@gmail.com>
// See LICENSE for licensing information
package cat.mvmike.numbertotext

import cat.mvmike.numbertotext.magnitude.Cents
import cat.mvmike.numbertotext.magnitude.Thousands
import cat.mvmike.numbertotext.magnitude.Units
import java.security.InvalidParameterException
import kotlin.math.roundToInt

object NumberToText {
    const val MIN_VALUE = 0
    const val MAX_VALUE = 1000000
    const val MIN_VALUE_ERROR = "Literal out of range. Min value = $MIN_VALUE"
    const val MAX_VALUE_ERROR = "Literal out of range. Max value = $MAX_VALUE"

    /**
     * Converts number to text (Catalan language). Please note that decimals are optional and max precision is set up
     * to 10^-2
     *
     * @param number   (total amount, decimals are optional and are rounded up to 10^-2)
     * @param currency (applies to integers, decimals are always cents. Can be empty)
     * @return string associated value
     */
    fun get(number: Double, currency: String?): String {

        // initial validations
        number.checkMinSize()
        number.checkMaxSize()
        val intPart = number.toInt()
        val decimalPart = ((number - intPart) * 100).roundToInt()
        return (Thousands(intPart).get()
                + Units(intPart, intPart == 0).get()
                + Units(intPart).getCurrency(currency)
                + Cents(decimalPart).get()
                + Cents(decimalPart).getCurrency(currency))
    }

    private fun Double.checkMinSize() {
        if (this < MIN_VALUE) throw InvalidParameterException(MIN_VALUE_ERROR)
    }

    private fun Double.checkMaxSize() {
        if (this >= MAX_VALUE) throw InvalidParameterException(MAX_VALUE_ERROR)
    }
}
