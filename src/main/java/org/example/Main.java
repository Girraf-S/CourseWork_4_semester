package org.example;

import org.example.Concol.Console;
import org.example.Exception.FailCommandException;
import org.example.Exception.FailFlagException;
import org.example.Exception.MyException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean isOk = false;
        String command;
        String[] commandArguments;
        File file;
        Scanner scanner = new Scanner(System.in);
        Console.menu();
        command = scanner.nextLine();
        commandArguments = command.split(" ");
        file = new File("./laboratory");
        FileWork fileWork = new FileWork(file);
        while (!commandArguments[0].equals("break")) {
            while (true) {
                Console console = new Console(commandArguments);
                try {
                    commandArguments = console.checkCommand();
                    isOk = true;
                } catch (FailCommandException e) {
                    System.out.println(e.getMessage());
                } catch (FailFlagException e) {
                    System.out.println(e.getMessage());
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
                if (isOk) break;
                command = scanner.nextLine();
                commandArguments = command.split(" ");
            }
            if (commandArguments.length > 1) fileWork.setFlag(commandArguments[1]);
            switch (commandArguments[0]) {
                case "break":
                    return;
                case "show":
                    fileWork.show();
                    break;
                case "back":
                    fileWork.back();
                    break;
                case "choose":
                    fileWork.choose(commandArguments[1]);
                    break;
                case "read":
                    fileWork.read();
                    break;
                case "clear":
                    if (commandArguments.length == 2) {
                        String[] temp = new String[3];
                        temp[0] = commandArguments[0];
                        temp[1] = commandArguments[1];
                        temp[2] = "";
                        commandArguments = temp;
                    }
                    try {
                        fileWork.clear(commandArguments[2]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "create":
                    fileWork.create();
                    break;
            }
            command = scanner.nextLine();
            commandArguments = command.split(" ");
        }
    }
}