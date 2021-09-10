package cat.mvmike.numbertotext.magnitude;

import cat.mvmike.numbertotext.language.Number;

import static cat.mvmike.numbertotext.language.Constants.DASH;
import static cat.mvmike.numbertotext.language.Constants.EMPTY;
import static cat.mvmike.numbertotext.language.Number.N_0;
import static cat.mvmike.numbertotext.language.Number.N_1;
import static cat.mvmike.numbertotext.language.Number.N_100;
import static cat.mvmike.numbertotext.language.Number.N_9;
import static cat.mvmike.numbertotext.language.Constants.PLURAL;
import static cat.mvmike.numbertotext.language.Number.getLiteralOpt;

public final class Hundreds extends BaseMagnitude {

    public Hundreds(int number) {
        super(number);
    }

    @Override
    public String get() {
        return getLiteralOpt(number, N_1, N_9)
                .map(Hundreds::toString)
                .orElse(EMPTY);
    }

    private static String toString(final Number literal) {
        switch (literal) {
            case N_1 -> { return N_100.getLiteral(); }
            case N_2, N_3, N_4, N_5, N_6, N_7, N_8, N_9 -> { return literal.getLiteral() + DASH + N_100.getLiteral() + PLURAL; }
            default -> throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isMultipleOf() {
        return N_0.getValue() == (number % N_100.getValue());
    }
}
