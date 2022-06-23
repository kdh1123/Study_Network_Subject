package kr.hs.dgsw.network.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        while(true){
            InetAddress inetAddr = InetAddress.getByName("255.255.255.255");
            DatagramSocket ds = new DatagramSocket();
            String j = "좀 그렇긴 하네요. ";
            DatagramPacket sendPacket =
                    new DatagramPacket(j.getBytes(),
                            j.getBytes().length, inetAddr, 9999);

            ds.send(sendPacket);
            if(j.equals(""))
            break;
        }
        System.out.println("전송 끝~~");
    }
}
