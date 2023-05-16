package org.example;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Disk {
    private File root = new File("./laboratory");

    public Disk() {
        if (!root.exists()) {
            {
                boolean created = root.mkdirs();
                if (created) {
                    System.out.println("Папка успешно создана.");
                } else {
                    System.out.println("Не удалось создать папку.");
                }
            }
        }
    }
    public File getRoot(){
        return root;
    }
}
