package org.example.demo4;

import java.io.FileWriter;
import java.io.IOException;

public class docker {
    public static void main(String[] args) throws IOException {
        FileWriter f = new FileWriter("dockerfile");
        f.write("hey,hii");
        f.close();
    }
}