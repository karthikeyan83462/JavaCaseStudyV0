package storage;

import java.io.*;
import java.util.*;

public class StorageManager {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/users.txt";
    private static final String PROJECTS_FILE = "data/projects.txt";
    private static final String ATTENDANCE_FILE = "data/attendance.txt";
    private static final String LEAVES_FILE = "data/leaves.txt";
    private static final String PERFORMANCE_FILE = "data/performance.txt";

    public static void initialize() {
        new File(DATA_DIR).mkdirs();
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(PROJECTS_FILE);
        createFileIfNotExists(ATTENDANCE_FILE);
        createFileIfNotExists(LEAVES_FILE);
        createFileIfNotExists(PERFORMANCE_FILE);
        
        addDefaultAdmin();
    }

    private static void createFileIfNotExists(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + filename);
        }
    }

    private static void addDefaultAdmin() {
        List<String> lines = readFile(USERS_FILE);
        if (lines.isEmpty()) {
            writeToFile(USERS_FILE, "ADM001|admin|Admin@123|ADMIN");
        }
    }

    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // File may be empty
        }
        return lines;
    }

    public static void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, true);
             BufferedWriter buffer = new BufferedWriter(writer)) {
            buffer.write(content);
            buffer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    public static void overwriteFile(String filename, List<String> lines) {
        try (FileWriter writer = new FileWriter(filename);
             BufferedWriter buffer = new BufferedWriter(writer)) {
            for (String line : lines) {
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error overwriting file: " + filename);
        }
    }

    public static String getFilePath(String fileType) {
        switch(fileType) {
            case "users": return USERS_FILE;
            case "projects": return PROJECTS_FILE;
            case "attendance": return ATTENDANCE_FILE;
            case "leaves": return LEAVES_FILE;
            case "performance": return PERFORMANCE_FILE;
            default: return null;
        }
    }
}
