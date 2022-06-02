package kr.hs.dgsw.network.test01.n2106.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.util.List;
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
                while(true) {
                    fun = scanner.next();


                    if (fun.startsWith("/")) {

                        fun = fun.substring(1);

                        if (fun.equals("파일목록")) {
                            client.fileList(is, os, fun);
                        }

                        else if (fun.equals("업로드")) {
                            String[] nameArray = scanner.nextLine().split(" ");
                            name = nameArray[nameArray.length-1];
                            System.out.println(fun);
                            client.upload(is, os, name);
                        }

                        else if (fun.equals("다운로드")) {
                            client.download(is, os);
                        }

                        else if (fun.equals("접속종료")) {
                            client.exit(scanner, sc);
                        }

                        else {
                            System.out.println("** 지원하지 않는 명령어입니다 **");
                        }
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
    public void upload(InputStream is, OutputStream os,String name) {
        try{
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner scanner = new Scanner(System.in);
            PrintWriter pw = new PrintWriter(os,true);
            dos.writeUTF(name);
            File f1 = new File(filefolder +"/"+name);
            FileInputStream fis = new FileInputStream(f1);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            while((readSize=fis.read(bytes)) != -1){
                dos.write(bytes,0,readSize);
            }
            String result = br.readLine();
            if(result.equals("성공")){
                System.out.println("** "+name+" 파일이 성공적으로 업로드 되었습니다 **");
            }
            else if(result.equals("중복")){
                System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                String input = scanner.next();
                if(input.equals("YES") || input.equals("NO")){
                    pw.println(input);
                }
                String answer = br.readLine();
                if(answer.equals("성공")){
                    System.out.println("** "+name+" 파일이 성공적으로 업로드 되었습니다 **");
                }
                else if(answer.equals("취소")){
                    System.out.println("** "+name+" 파일 업로드를 취소하였습니다 **");
                }
                else if(answer.equals("실패")){
                    System.out.println("** "+name+" 파일 업로드를 실패하였습니다 **");
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("** "+name+"해당 파일을 찾을 수 없습니다 **");
        } catch (IOException e){
            e.printStackTrace();
        }
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
        }
        System.out.println("** 총 "+fileList.length+"개의 파일 **");
    }
    public void exit(Scanner scanner,Socket sc) throws IOException {
        scanner.close();
        sc.close();
        System.out.println("** 접속이 종료되었습니다 **");
    }
}
