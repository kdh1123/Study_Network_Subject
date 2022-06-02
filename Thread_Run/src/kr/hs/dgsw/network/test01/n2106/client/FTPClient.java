package kr.hs.dgsw.network.test01.n2106.client;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class FTPClient {
    private static String id;
    private static String pass;
    private static final String filefolder = "C:/Users/DGSW/Desktop/네트워크 보낼 파일";
    private static final int PORT = 5000;
    private static final String IP_ADDRESS = "10.80.163.89";
    private static final FTPClient client = new FTPClient();
    private static boolean isLogin = false;

    public static void main(String[] args) throws IOException {
        System.out.println("** 서버에 접속하였습니다 **");
        String fun;
        String name, newName;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            Socket sc = new Socket(IP_ADDRESS,PORT);
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();
            PrintWriter pw = new PrintWriter(os,true);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if(!isLogin)
            client.login(is,os,scanner);
            if(isLogin){
                while(true){
                    fun = scanner.next();
                    if(fun.substring(0,1).equals("/")) fun = fun.substring(1);
                    if(fun.equals("파일목록")){
                        client.fileList(is,os,fun);
                    }
                    else if(fun.equals("업로드")){
                        client.upload(is,os,name,newName);
                    }
                    else if(fun.equals("다운로드")){
                        client.download(is,os);
                    }
                    else if(fun.equals("접속종료")){
                        client.exit(scanner,sc);
                    }
                }
            }


            //int readSize = 0;
            //byte[] bytes = new byte[1024];

            //while ((readSize = fis.read(bytes)) != -1) {
              //  dos.write(bytes, 0, readSize);
            //}

        }
    }
    public void upload(InputStream is, OutputStream os,String name){
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);
    }
    public void upload(InputStream is, OutputStream os,String name,String newName){
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);
    }
    public void download(InputStream is, OutputStream os){
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);
    }
    public void login(InputStream is, OutputStream os, Scanner scanner) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os,true);
        while(true) {
            System.out.print("ID : ");
            pw.println(scanner.next());
            System.out.print("PASS : ");
            pw.println(scanner.next());
            String success = br.readLine();
            System.out.println(success);
            if (success.equals("성공")){
                System.out.println("** FTP 서버에 접속했습니다 **");
                isLogin = true;
                break;
            }
            else if(success.equals("실패")){
                System.out.println("** 비밀번호 또는 아이디가 잘못되었습니다 **");
            }
        }
    }
    public void fileList(InputStream is, OutputStream os, String fun) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os,true);
        pw.println(fun);
        String result;
        System.out.println("** File List **");
        result = br.readLine();
        result = result.substring(1,result.lastIndexOf("]"));
        String[] fileList = result.split(", ");
        for (String f:fileList) {
            System.out.println("** "+f+" **");
        };
        System.out.println("** 총 "+fileList.length+"개의 파일 **");
    }
    public void exit(Scanner scanner,Socket sc) throws IOException {
        scanner.close();
        sc.close();
        System.out.println("** 접속이 종료되었습니다 **");
    }
}
