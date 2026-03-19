import ui.MainMenu;
import storage.StorageManager;

public class Main {
    public static void main(String[] args) {
        StorageManager.initialize();
        MainMenu.start();
    }
}
