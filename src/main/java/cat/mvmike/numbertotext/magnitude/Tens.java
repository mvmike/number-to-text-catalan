package cat.mvmike.numbertotext.magnitude;

import cat.mvmike.numbertotext.language.Number;

import java.util.Arrays;

import static cat.mvmike.numbertotext.language.Constants.AND;
import static cat.mvmike.numbertotext.language.Constants.DASH;
import static cat.mvmike.numbertotext.language.Constants.EMPTY;
import static cat.mvmike.numbertotext.language.Number.N_0;
import static cat.mvmike.numbertotext.language.Number.N_1;
import static cat.mvmike.numbertotext.language.Number.N_10;
import static cat.mvmike.numbertotext.language.Number.N_20;
import static cat.mvmike.numbertotext.language.Number.N_30;
import static cat.mvmike.numbertotext.language.Number.N_40;
import static cat.mvmike.numbertotext.language.Number.N_50;
import static cat.mvmike.numbertotext.language.Number.N_60;
import static cat.mvmike.numbertotext.language.Number.N_70;
import static cat.mvmike.numbertotext.language.Number.N_80;
import static cat.mvmike.numbertotext.language.Number.N_9;
import static cat.mvmike.numbertotext.language.Number.N_90;
import static cat.mvmike.numbertotext.language.Constants.PLURAL;
import static cat.mvmike.numbertotext.language.Constants.SPACE;
import static cat.mvmike.numbertotext.language.Number.getStringLiteral;

public final class Tens extends BaseMagnitude {

    public Tens(int number) {
        super(number);
    }

    @Override
    public String get() {
        if (number <= N_20.getValue())
            return getStringLiteral(number, N_0, N_20);

        if (number <= N_30.getValue())
            return N_20.getLiteral() + DASH + AND + DASH + getStringLiteral(number % N_10.getValue(), N_0, N_9);

        return Arrays.stream(new Number[]{N_90, N_80, N_70, N_60, N_50, N_40, N_30})
                .filter(l -> number >= l.getValue())
                .findFirst()
                .map(l -> isMultipleOf() ? l.getLiteral() : l.getLiteral() + DASH + getStringLiteral(number % N_10.getValue(), N_1, N_9))
                .orElse(EMPTY);
    }

    @Override
    public boolean isMultipleOf() {
        return N_0.getValue() == (number % N_10.getValue());
    }
}
