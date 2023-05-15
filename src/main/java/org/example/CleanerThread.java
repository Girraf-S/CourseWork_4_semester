package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class CleanerThread implements Runnable {
    private File root;
    private String firstFlag;
    private String secondFlag;
    private Semaphore semaphore = new Semaphore(1);

    public CleanerThread(File file, String flag1, String flag2) {
        this.firstFlag = flag1;
        this.secondFlag = flag2;
        this.root = file;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (firstFlag.equals("-f")) {
            File temp = root;
            root = root.getParentFile();
            try {
                FileUtils.forceDelete(temp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("file is nulled and deleted");
        }
        if (firstFlag.equals("-d")) {
            File temp = root;
            root = root.getParentFile();
            try {
                deleteFolderHierarchy(temp, secondFlag);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        semaphore.release();
    }
    private void deleteFolderHierarchy(File folder, String flag) throws IOException {
        //System.out.println(indent + "+-- " + folder.getName() + "/");

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolderHierarchy(file, flag);
                    if (flag.equals("-a")) {
                        FileUtils.forceDelete(file);
                    }
                } else {
                    FileUtils.forceDelete(file);
                }
            }
        }
    }
}
