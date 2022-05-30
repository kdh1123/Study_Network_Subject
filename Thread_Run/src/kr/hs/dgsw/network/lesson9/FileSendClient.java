package kr.hs.dgsw.network.lesson9;

import java.io.*;
import java.net.Socket;

public class FileSendClient {
    public static void main(String[] args) throws IOException{
        Socket sc = new Socket("10.80.163.89",5000);

        OutputStream os = sc.getOutputStream();
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);

        File f1 = new File("C:/Users/DGSW/Desktop/네트워크 받은 파일/힙한 준성.jpg");
        FileInputStream fis = new FileInputStream(f1);

        dos.writeUTF(f1.getName());

        int readSize = 0;
        byte[] bytes = new byte[1024];

        while((readSize=fis.read(bytes)) != -1){
            dos.write(bytes,0,readSize);
        }
    }
}
