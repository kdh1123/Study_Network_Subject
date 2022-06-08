package kr.hs.dgsw.network.test01.n2106.client;

import kr.hs.dgsw.network.test01.n2106.CommonFun;

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
    private static final String IP_ADDRESS = "10.80.162.101";
    private static final FTPClient client = new FTPClient();
    private static boolean isLogin = false;
    private static Socket sc;
    Scanner scanner = new Scanner(System.in);
    String fun;
    String name, newName;
    private CommonFun commonFun = new CommonFun(sc);

    public static void main(String[] args) throws IOException {
        System.out.println("** 서버에 접속하였습니다 **");
        Scanner scanner = new Scanner(System.in);
        FTPClient client = new FTPClient();
        client.execute();
    }

    public void execute() throws IOException{
        sc = new Socket(IP_ADDRESS,PORT);

        if(commonFun.receiveMSG().equals("로그인"))

            client.login();

        if(isLogin){
            while(true) {
                fun = scanner.next();


                if (fun.startsWith("/")) {

                    fun = fun.substring(1);

                    if (fun.equals("파일목록")) {
                        client.fileList(is, os, fun);
                    }
                    else if (fun.equals("업로드")) {
                        try{
                            String[] nameArray = scanner.nextLine().split(" ");
                            name = nameArray[1];
                            newName = nameArray[nameArray.length-1];
                            pw.println("업로드");
                            client.upload(name, newName);
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("** 잘못된 형식의 명령어입니다 **");
                        }
                    }

                    else if (fun.equals("다운로드")) {
                        name = "";
                        client.download(name);
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

    }
    public void upload(String name,String newName) {
        try{
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner scanner = new Scanner(System.in);
            PrintWriter pw = new PrintWriter(os,true);

            System.out.println(filefolder +"/"+name);
            File file = new File(filefolder +"/"+name);
            FileInputStream fis = new FileInputStream(file);

            pw.println(newName);
            System.out.println(newName);
            String result = br.readLine();



            if(result.equals("성공")){
                client.fileOutPut(file);
                System.out.println("** "+name+"("+newName+") 파일이 성공적으로 업로드 되었습니다 **");
            }
            else if(result.equals("중복")) {
                System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                pw.println(scanner.next());
                client.fileOutPut(file);
                if (br.readLine().equals("성공")) {
                    System.out.println("** " + name + " 파일이 성공적으로 업로드 되었습니다 **");
                }
                else {
                    System.out.println("** 파일 업로드 실패 **");
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("** "+name+" 해당 파일을 찾을 수 없습니다 **");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void download(String name){
        try{
            OutputStream os = sc.getOutputStream();
            InputStream is = sc.getInputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bir);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner scanner = new Scanner(System.in);
            PrintWriter pw = new PrintWriter(os,true);

            System.out.println(filefolder +"/"+name);
            File file = new File(filefolder +"/"+name);

            pw.println(name);
            System.out.println(name);
            String result = br.readLine();



            if(result.equals("성공")){
                client.fileInPut(file);
                System.out.println("** "+name+"() 파일이 성공적으로 업로드 되었습니다 **");
            }
            else if(result.equals("중복")) {
                System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                pw.println(scanner.next());
                client.fileInPut(file);
                if (br.readLine().equals("성공")) {
                    System.out.println("** " + name + " 파일이 성공적으로 업로드 되었습니다 **");
                }
                else {
                    System.out.println("** 파일 업로드 실패 **");
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("** "+name+" 해당 파일을 찾을 수 없습니다 **");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void login() {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os,true);
        try{
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
        }catch (IOException e){
            System.out.println("연결 실패");
        }catch  (RuntimeException e){
            System.out.println("런타임 초과");
        }
    }
    public void fileList(InputStream is, OutputStream os, String fun) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os,true);
        System.out.println(fun);
        pw.println(fun);
        String result;
        System.out.println("** [File List] **");
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
    public void fileInPut(File file){
        try {
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bir);
            FileOutputStream fos = new FileOutputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            do {
                fos.write(bytes, 0, readSize);
            } while ((readSize = dis.read(bytes)) == 1024);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void fileOutPut(File file){
        try{
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            FileInputStream fis = new FileInputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            while ((readSize = fis.read(bytes)) != -1){
                dos.write(bytes, 0, readSize);
            };
            dos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
