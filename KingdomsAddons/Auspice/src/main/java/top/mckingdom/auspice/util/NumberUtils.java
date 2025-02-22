package top.mckingdom.auspice.util;

public class NumberUtils {

    public static Long parseLong(Object o) {
        if (o instanceof Number) {
            return  ((Number) o).longValue();
        }
        if (o instanceof String) {
            return Long.parseLong((String) o);
        }

        return null;
    }


    public static Double parseDouble(Object o) {
        if (o instanceof Number) {
            return  ((Number) o).doubleValue();
        }
        if (o instanceof String) {
            return Double.parseDouble((String) o);
        }
        return null;
    }

    public static Integer parseInt(Object o) {
        if (o instanceof Number) {
            return  ((Number) o).intValue();
        }
        if (o instanceof String) {
            return Integer.parseInt((String) o);
        }
        return null;
    }
}
