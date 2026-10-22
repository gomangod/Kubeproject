package org.example.demo4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class dockerFileWriter {
     static FileWriter f;
    public static void filewriteropen() throws IOException {
        f = new FileWriter("dockerfile");
    }

    public static void fileWriter(String s) throws IOException {
        f.write(s);
    }

    public static void fileWriterclose() throws IOException {
        f.close();
    }
}