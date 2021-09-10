package cat.mvmike.numbertotext.magnitude;

import static cat.mvmike.numbertotext.language.Constants.DEC_CURRENCY;
import static cat.mvmike.numbertotext.language.Constants.DEC_SEPARATOR;
import static cat.mvmike.numbertotext.language.Constants.EMPTY;
import static cat.mvmike.numbertotext.language.Number.N_0;
import static cat.mvmike.numbertotext.language.Number.N_1;
import static cat.mvmike.numbertotext.language.Constants.PLURAL;
import static cat.mvmike.numbertotext.language.Constants.SPACE;
import static cat.mvmike.numbertotext.util.StringUtils.isEmpty;

public class Cents extends BaseMagnitude {

    public Cents(int number) {
        super(number);
    }

    @Override
    public String get() {
        String decimalPart = new Tens(number).get();
        return isEmpty(decimalPart) || N_0.getLiteral().equals(decimalPart) ?
                EMPTY : SPACE + DEC_SEPARATOR + SPACE + decimalPart;
    }

    @Override
    public String getCurrency(final String currency) {
        if (number == N_0.getValue() || isEmpty(currency))
            return EMPTY;

        return number == N_1.getValue() ? SPACE + DEC_CURRENCY : SPACE + DEC_CURRENCY + PLURAL;
    }
}
