import ui.MainMenu;
import storage.StorageManager;

public class Main {
    public static void main(String[] args) {
        try {
            StorageManager.initialize();
            MainMenu.start();
        } catch (Exception e) {
            System.out.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
