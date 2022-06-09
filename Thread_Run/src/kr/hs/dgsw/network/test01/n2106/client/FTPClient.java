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
    private static final String FILE_FOLDER = "C:/Users/DGSW/Desktop/네트워크 보낼 파일";
    private static final int PORT = 5000;
    private static final String IP_ADDRESS = "10.80.162.101";
    private static FTPClient client;
    private static boolean isLogin = false;
    private static Socket sc;
    Scanner scanner = new Scanner(System.in);
    String fun;
    String name, newName;
    private static CommonFun commonFun;
    public FTPClient(){
        try {
            sc = new Socket(IP_ADDRESS,PORT);
            commonFun = new CommonFun(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("** 서버에 접속하였습니다 **");
        client = new FTPClient();
        client.execute();
    }

    public void execute() throws IOException{

        if(!isLogin)

            client.login();

        if(isLogin){
            while(true) {
                fun = scanner.next();


                if (fun.startsWith("/")) {

                    fun = fun.substring(1);

                    if (fun.equals("파일목록")) {
                        client.fileList(fun);
                    }
                    else if (fun.equals("업로드")) {
                        try{
                            String[] nameArray = scanner.nextLine().split(" ");
                            name = nameArray[1];
                            newName = nameArray[nameArray.length-1];
                            commonFun.sendMSG("업로드");
                            client.upload(name, newName);
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("** 잘못된 형식의 명령어입니다 **");
                        }
                    }

                    else if (fun.equals("다운로드")) {
                        name = scanner.next();
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
            Scanner scanner = new Scanner(System.in);

            System.out.println(FILE_FOLDER +"/"+name);
            File file = new File(FILE_FOLDER +"/"+name);
            FileInputStream fis = new FileInputStream(file);

            commonFun.sendMSG(newName);
            System.out.println(newName);
            String result =
                    commonFun.receiveMSG();



            if(result.equals("성공")){
                commonFun.sendFile(file);
                System.out.println("** "+name+"("+newName+") 파일이 성공적으로 업로드 되었습니다 **");
            }
            else if(result.equals("중복")) {
                System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                commonFun.sendMSG(scanner.next());
                commonFun.sendFile(file);
                if (commonFun.receiveMSG().equals("성공")) {
                    System.out.println("** " + name + " 파일이 성공적으로 업로드 되었습니다 **");
                }
                else {
                    System.out.println("** 파일 업로드 실패 **");
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("** "+name+" 해당 파일을 찾을 수 없습니다 **");
        }
    }
    public void download(String name){
            Scanner scanner = new Scanner(System.in);

            System.out.println(FILE_FOLDER +"/"+name);
            File file = new File(FILE_FOLDER +"/"+name);

            commonFun.sendMSG(name);
            String result = commonFun.receiveMSG();



            if(result.equals("성공")){
                commonFun.receiveFile(file);
                System.out.println("** "+name+"() 파일이 성공적으로 다운로드 되었습니다 **");
            }
            else if(result.equals("실패")) {
                System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                String answer = scanner.next();
                if(answer.equals("YES")){
                    commonFun.receiveFile(file);
                }
                else if(answer.equals("NO")){
                    String[] fileNameEx = name.split("\\.");
                    for(int i=1;; i++) {
                        name = fileNameEx[0] + "("+i+")." + fileNameEx[1];
                        file = new File(FILE_FOLDER + name);
                        if(file.exists()) continue;
                        break;
                    }
                }
                else {
                    System.out.println("** 파일 업로드 실패 **");
                }
            }
    }
    public void login() {
            while(true) {
                System.out.print("ID : ");
                commonFun.sendMSG(scanner.next());

                System.out.print("PASS : ");
                commonFun.sendMSG(scanner.next());

                String success =
                        commonFun.receiveMSG();

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
    public void fileList(String fun) throws IOException {
        commonFun.sendMSG(fun);
        String result;
        System.out.println("** [File List] **");
        result =
                commonFun.receiveMSG();
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
