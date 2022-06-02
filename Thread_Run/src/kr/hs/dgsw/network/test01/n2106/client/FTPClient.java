package kr.hs.dgsw.network.test01.n2106.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    private static String id;
    private static String pass;
    private static final String filefolder = "C:/Users/DGSW/Desktop/네트워크 보낼 파일";
    private static final int PORT = 5000;
    private static final String IP_ADDRESS = "10.80.163.89";
    private static final FTPClient client = new FTPClient();
    private static boolean isLogin = false;
    private static OutputStream os;
    private static InputStream is;
    private static BufferedOutputStream bor;
    private static DataOutputStream dos;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("** 서버에 접속하였습니다 **");
        String fun;
        String result;
        while(true) {
            Socket sc = new Socket(IP_ADDRESS,PORT);
            os = sc.getOutputStream();
            is = sc.getInputStream();
            bor = new BufferedOutputStream(os);
            dos = new DataOutputStream(bor);
            pw = new PrintWriter(os);
            br = new BufferedReader(new InputStreamReader(is));
            if(!isLogin)
            client.login();
            if(isLogin){
                while(true){
                    fun = scanner.next();
                    System.out.printf(fun.substring(0,0));
                    if(fun.substring(0,0).equals("/")){
                        fun = fun.substring(1);
                        pw.println(fun);
                        System.out.println("명령 입력");
                    }
                    result = br.readLine();
                    if(fun.equals("파일목록")){
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
    public void login() throws IOException {
        while(true) {
            System.out.printf("ID : ");
            pw.println(scanner.next());
            System.out.printf("PASS : ");
            pw.println(scanner.next());
            String success = br.readLine();
            if (success.equals("성공")){
                System.out.println("** FTP 서버에 접속했습니다 **");
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
