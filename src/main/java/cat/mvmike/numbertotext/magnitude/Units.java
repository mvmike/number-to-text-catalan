package cat.mvmike.numbertotext.magnitude;

import static cat.mvmike.numbertotext.language.Constants.EMPTY;
import static cat.mvmike.numbertotext.language.Constants.PLURAL;
import static cat.mvmike.numbertotext.language.Constants.SPACE;
import static cat.mvmike.numbertotext.util.StringUtils.isEmpty;

public final class Units extends BaseMagnitude {

    private final boolean includeZero;

    public Units(int number) {
        super(number);
        this.includeZero = false;
    }

    public Units(int number, boolean includeZero) {
        super(number);
        this.includeZero = includeZero;
    }

    @Override
    public String get() {
        String tens = (new Hundreds(number).isMultipleOf() && !includeZero ? EMPTY : new Tens(number % 100).get());
        String hundreds = new Hundreds((number / 100) % 10).get();

        return isEmpty(hundreds) ? tens :
                isEmpty(tens) ? hundreds : hundreds + SPACE + tens;
    }

    @Override
    public String getCurrency(String currency) {
        return isEmpty(currency) ? EMPTY : SPACE + currency + (number % 10 != 1 ? PLURAL : EMPTY);
    }
}
