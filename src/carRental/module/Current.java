package carRental.module;

import javafx.beans.value.ObservableStringValue;

public class Current {
    private static String a;
    private static int b;
    private static int type;

    public static String getA() {
        return a;
    }

    public static void setA(String  a) {
        Current.a = a;
    }

    public static int getB() {
        return b;
    }

    public static void setB(int b) {
        Current.b = b;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        Current.type = type;
    }
}
