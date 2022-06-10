package kr.hs.dgsw.network.test01.n2106;

import java.io.*;
import java.net.Socket;

public class CommonFun {
    private Socket sc;
    private OutputStream os;
    private InputStream is;
    private BufferedInputStream bir;
    private DataInputStream dis;
    BufferedOutputStream bor;
    DataOutputStream dos;
    public CommonFun(Socket sc){
        this.sc = sc;
        try{
            is = sc.getInputStream();
            os = sc.getOutputStream();
            bir = new BufferedInputStream(is);
            bor = new BufferedOutputStream(os);
            dis = new DataInputStream(bir);
            dos = new DataOutputStream(bor);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void receiveFile(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            System.out.println("받을 파일 : "+file);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            while ((readSize = dis.read(bytes)) != -1){
                fos.write(bytes, 0, readSize);
                System.out.println(bytes);
            }
            System.out.println("파일 받음");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendFile(File file){
        try{
            FileInputStream fis = new FileInputStream(file);
            System.out.println("보낼 파일 : "+file);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            while((readSize = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, readSize);
            }
            System.out.println("파일 보냄");
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public String receiveMSG(){
        try{
        return dis.readUTF();
        }catch (Exception e){
            return "error";
        }
    }
    public void sendMSG(String str){
        try{
            dos.writeUTF(str);
            System.out.println("전송 : " + str);
            dos.flush();
        }catch (IOException e){
            System.out.println(str+" 실패");
        }
    }
}
