package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileWork {
    private File root;
    private String flag;

    public FileWork(File file) {
        root = file;
    }

    public void show() {
        File[] files = root.listFiles();
        System.out.printf("%s:\n", root.getName());
        for (File file :
                files) {
            if (flag.equals("-f") && file.isFile()) System.out.printf("+--%s\n", file.getName());
            else if (flag.equals("-d") && file.isDirectory()) System.out.printf("+--%s\n", file.getName());
            else if (flag.equals("-a")) System.out.printf("+--%s\n", file.getName());
        }
        if (flag.equals("-t"))
            printFolderHierarchy(root, "", "-a");

    }

    public void dirTree() {
        printFolderHierarchy(root, "", flag);
    }

    public String choose(String fileName) {
        fileName = "./laboratory" + fileName.substring(1, fileName.length());
        File temp = new File(fileName);
        if (temp.exists()) root = temp;
        else System.out.println("incorrect file name");
        return fileName;
    }

    public void read() {
        if (flag.equals("-t")) {
            try (FileReader reader = new FileReader(root)) {
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (flag.equals("-b")) {
            try (FileInputStream file = new FileInputStream(root)) {
                byte[] data = new byte[1024];
                int bytesRead = file.read(data);

                // выводим прочитанные данные
                System.out.println("Прочитано " + bytesRead + " байт:");
                for (int i = 0; i < bytesRead; i++) {
                    if (i % 16 == 0) System.out.println();
                    System.out.printf("%s ", readBinary(data[i]));//String.format("%02x ", b));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readBinary(byte b) {
        String number = Integer.toBinaryString(b);
        while (number.length() < 8) {
            number = "0" + number;
        }
        return number;
    }

    public void back() {
        if (root.getName().equals("laboratory")) {
            System.out.println("you can't go to the directory \"laboratory\"");
        } else root = root.getParentFile();
    }

    public void clear(String secondFlag) throws IOException {
        if (flag.equals("-f")) {
            File temp = root;
            root = root.getParentFile();
            FileUtils.forceDelete(temp);
            System.out.println("file is nulled and deleted");
        }
        if (flag.equals("-d")) {
            File temp = root;
            root = root.getParentFile();
            deleteFolderHierarchy(temp, secondFlag);
        }
//        int sectorSize = 512; // размер сектора в байтах
//        long sectorNumber = 0; // номер сектора
//        long offset = sectorNumber * sectorSize; // смещение до нужного сектора
//
//        try (RandomAccessFile file = new RandomAccessFile(root, "rw")) {
//            long fileSize = root.length();
//            // переходим к нужному сектору
//            file.seek(offset);
//
//            // читаем данные из сектора
//            byte[] data = new byte[sectorSize];
//            int bytesRead = file.read(data);
//
//            // выводим прочитанные данные
//            System.out.println("Прочитано " + bytesRead + " байт:");
//            for (byte b : data) {
//                System.out.print(String.format("%02X ", b));
//            }
//            System.out.println();
//
//            // записываем новые данные в сектор
//            byte[] newData = new byte[]{0x00, 0x00, 0x00, 0x00};
//            for (int i = 0; i < fileSize; i++) {
//                file.seek(i);
//                file.write(0);
//            }
//
//            // снова читаем данные из сектора, чтобы убедиться, что они изменились
//            file.seek(offset);
//            bytesRead = file.read(data);
//
//            // выводим измененные данные
//            System.out.println("Прочитано " + bytesRead + " байт после записи:");
//            for (byte b : data) {
//                System.out.print(String.format("%02X ", b));
//            }
//            System.out.println();
//        }
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

    private static void printFolderHierarchy(File folder, String indent, String flag) {
        if (!flag.equals("-f")) System.out.println(indent + "+-- " + folder.getName() + "/");
        indent += "   ";

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //return file;
                    printFolderHierarchy(file, indent, flag);
                } else {
                    if (!flag.equals("-d"))
                        System.out.println(indent + "+-- " + file.getName());
                }
            }
        }
    }

    public void create() {
        for (int i = 0; i < 3; i++) {
            File directory = new File(String.format("%s/dir%d", root, i));
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Directory already exists.");
            }
            String path = root + "/" + directory.getName();
            for (int j = 0; j < 3; j++) {
                File file = new File(String.format("%s/file%d.txt", path, j));
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created successfully.");
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            root = new File(path);
        }
    }

    public void setRoot(File file) {
        root = file;
    }

    public File getRoot() {
        return root;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
