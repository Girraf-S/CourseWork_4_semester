package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileWorkTest {
    FileWork fileWork=new FileWork(new File("./laboratory"));

    @Test
    void show() {
        System.out.println(fileWork.getRoot().getName());
    }

    @Test
    void dirTree() {
    }

    @Test
    void choose() {
        System.out.println(fileWork.choose("./file1.txt"));
    }

    @Test
    void read() {
    }
}