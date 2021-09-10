package cat.mvmike.numbertotext.util;

public abstract class StringUtils {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
