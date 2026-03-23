package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import storage.StorageManager;
import java.util.List;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }

    public static String generateId(String prefix) {
        String filePath = null;
        if (prefix.equals("EMP") || prefix.equals("ADM") || prefix.equals("MGR")) {
            filePath = StorageManager.getFilePath("users");
        } else if (prefix.equals("PROJ")) {
            filePath = StorageManager.getFilePath("projects");
        } else if (prefix.equals("LEV")) {
            filePath = StorageManager.getFilePath("leaves");
        } else if (prefix.equals("PERF")) {
            filePath = StorageManager.getFilePath("performance");
        }

        int count = 1;
        if (filePath != null) {
            List<String> lines = StorageManager.readFile(filePath);
            count = lines.size() + 1;
        }

        return String.format("%s%03d", prefix, count);
    }

    public static boolean isValidDate(String date) {

        if (date == null || date.trim().isEmpty())
            return false;

        String[] parts = date.split("/");
        if (parts.length != 3)
            return false;

        String ddStr = parts[0];
        String mmStr = parts[1];
        String yyStr = parts[2];

        // Check only digits
        if (!isNumber(ddStr) || !isNumber(mmStr) || !isNumber(yyStr))
            return false;

        int day = Integer.parseInt(ddStr);
        int month = Integer.parseInt(mmStr);
        int year = Integer.parseInt(yyStr);

        // Basic range validation
        if (year < 1900 || year > 9999) return false;
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;

        // Months with only 30 days
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
            return false;

        // February check
        if (month == 2) {
            boolean leap = (year % 4 == 0);
            if (leap && day > 29) return false;
            if (!leap && day > 28) return false;
        }

        return true;
    }

    public static boolean isEndDateAfterStart(String start, String end) {
        if (!isValidDate(start) || !isValidDate(end))
            return false;

        String[] s = start.split("-");
        String[] e = end.split("-");

        int sd = Integer.parseInt(s[0]);
        int sm = Integer.parseInt(s[1]);
        int sy = Integer.parseInt(s[2]);

        int ed = Integer.parseInt(e[0]);
        int em = Integer.parseInt(e[1]);
        int ey = Integer.parseInt(e[2]);

        // Compare year → month → day
        if (ey > sy) return true;
        if (ey < sy) return false;

        if (em > sm) return true;
        if (em < sm) return false;

        return ed > sd;
    }

    private static boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
        }
        return true;
    }
}
