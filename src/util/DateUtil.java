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
}
