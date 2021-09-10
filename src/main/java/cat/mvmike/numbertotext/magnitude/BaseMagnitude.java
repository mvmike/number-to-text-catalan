package cat.mvmike.numbertotext.magnitude;

public abstract class BaseMagnitude {

    protected final int number;

    protected BaseMagnitude(int number) {
        this.number = number;
    }

    public abstract String get();

    public String getCurrency(final String currency) {
        throw new UnsupportedOperationException();
    }

    public boolean isMultipleOf(){
        throw new UnsupportedOperationException();
    }

    public int mod(){
        throw new UnsupportedOperationException();
    }
}
