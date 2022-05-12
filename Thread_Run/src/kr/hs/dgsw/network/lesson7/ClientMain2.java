package kr.hs.dgsw.network.lesson7;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain2 {
    public static void main(String[] args) {
        try {

            Socket sc = new Socket("10.80.161.228",5000);

            Thread it = new inputThread(sc);
            Thread ot = new outputThread(sc);

            it.start();
            ot.start();

        } catch (UnknownHostException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

