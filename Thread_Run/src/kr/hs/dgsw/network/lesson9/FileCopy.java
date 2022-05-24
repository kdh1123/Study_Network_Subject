package kr.hs.dgsw.network.lesson9;

import java.io.*;

public class FileCopy {

    public static void main(String[] args) throws IOException {
        File f1 = new File("/C:/Users/DGSW/Desktop/B1nd.png");
        FileInputStream fis = new FileInputStream(f1);
        FileOutputStream fos = new FileOutputStream("/C:/Users/DGSW/Desktop/B1nd.jpg");

        byte[] bytes = new byte[1024];

        while(fis.read(bytes) != -1){
            fos.write(bytes);
        }
    }
}
