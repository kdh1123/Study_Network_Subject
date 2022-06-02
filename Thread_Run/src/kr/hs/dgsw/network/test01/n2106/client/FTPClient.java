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
        String result;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            Socket sc = new Socket(IP_ADDRESS,PORT);
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            PrintWriter pw = new PrintWriter(os,true);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if(!isLogin)
            client.login(is,os,scanner);
            if(isLogin){
                while(true){
                    System.out.print("명령어 입력 : ");
                    fun = scanner.next();
                    if(fun.substring(0,1).equals("/")) fun = fun.substring(1);
                    System.out.println(fun);
                    if(fun.equals("파일목록")){
                        pw.println(fun);
                        result = br.readLine();
                        System.out.println(result);
                    }
                    else if(fun.equals("업로드")){

                    }
                    else if(fun.equals("다운로드")){

                    }
                    else if(fun.equals("접속종료")){
                        client.exit(scanner);
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
    public void upload(){

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
    public void download(){

    }
    public void fileList(){

    }
    public void exit(Scanner scanner){
        scanner.close();
        System.out.println("** 접속이 종료되었습니다 **");
    }
}
