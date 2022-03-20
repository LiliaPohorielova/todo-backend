package ua.com.alevel.util;

import java.lang.reflect.Field;
import java.util.Locale;

public class SortAndPage {

    public static final String DEFAULT_SORT_PARAM_VALUE = "id";
    public static final String DEFAULT_ORDER_PARAM_VALUE = "asc";
    public static final Integer DEFAULT_PAGE_PARAM_VALUE = 0;
    public static final Integer DEFAULT_SIZE_PARAM_VALUE = 10;


    public static boolean isSortDirection(String string) {
        return (string != null && string.trim().toLowerCase(Locale.ROOT).equals("asc")) ||
                string != null && string.trim().toLowerCase(Locale.ROOT).equals("desc");
    }

    public static boolean checkSortingColumnIsPresent(Class<?> sortingValuesClass, String string) {
        Field[] fields = sortingValuesClass.getDeclaredFields();
        for (Field field : fields)
            if (field.getName().equals(string))
                return true;

        return false;
    }
}
