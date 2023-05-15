package org.example.Concol;

import org.example.Exception.FailCommandException;
import org.example.Exception.FailFlagException;
import org.example.Exception.MyException;

public class Console {
    private String[] command;

    public Console(String[] command) {
        this.command = new String[command.length];
        for (int i = 0; i < command.length; i++) {
            this.command[i] = command[i];
        }
        System.arraycopy(command, 0, this.command, 0, command.length);

    }

    public String[] checkCommand() throws MyException {
        String commandArr[] = new String[command.length];

        if (!(command[0].equals("show") ||
                command[0].equals("read") || command[0].equals("choose") ||
                command[0].equals("clear") || command[0].equals("back") ||
                command[0].equals("break") || command[0].equals("create")))
            throw new FailCommandException("incorrect command. maybe you meant: " +
                    "show,  back, clear, read, choose, break, create");
        switch (command[0]) {
            case "show":
                if (command.length == 1) {
                    String tempString = command[0];
                    command = new String[2];
                    command[0] = tempString;
                    command[1] = "-a";
                }
                if (!(command[1].equals("-f") || command[1].equals("-d") ||
                        command[1].equals("-a"))|| command[1].equals("-t"))
                    throw new FailFlagException("incorrect flag.maybe you mean -f, -d, -a, -t");
                break;
            case "clear":
                break;
            case "read":
                if (!(command[1].equals("-t") || command[1].equals("-b")))
                    throw new FailFlagException("incorrect flag. maybe you mean -t, -b");
            case "break":
                break;
        }
        return command;
    }

    public static void menu(){
        System.out.println("options:\n" +
                "show     (-f show only files, -d show only directories,\n" +
                "          -a or \"\" show files and directories, -t view folder hierarchy)\n" +
                "choose   file_name/directory_name\n" +
                "back     (return to parent directory)\n" +
                "read     (-t read in .txt format, -b read binary code of symbols\n" +
                "clear    (first flags: -f reset the file, -d reset all files in directory,\n" +
                "          second flag: -c delete file or directory)\n" +
                "break     (close redactor)");
    }
}
