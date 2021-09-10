package cat.mvmike.numbertotext.magnitude;

import static cat.mvmike.numbertotext.language.Constants.EMPTY;
import static cat.mvmike.numbertotext.language.Number.N_0;
import static cat.mvmike.numbertotext.language.Number.N_1000;
import static cat.mvmike.numbertotext.language.Constants.SPACE;

public final class Thousands extends BaseMagnitude {

    public Thousands(int number) {
        super(number);
    }

    public static String get(final int number) {
        int thousandPart = number / N_1000.getValue();

        switch (thousandPart){
            case 0 -> { return EMPTY; }
            case 1 -> { return N_1000.getLiteral() + SPACE; }
            default -> { return new Units(thousandPart, false).get() + SPACE + N_1000.getLiteral() + (isMultipleOfThousand(number) ? EMPTY : SPACE); }
        }
    }

    private static boolean isMultipleOfThousand(final int number){
        return N_0.getValue() == (number % N_1000.getValue());
    }

    @Override
    public String get() {
        return null;
    }
}
