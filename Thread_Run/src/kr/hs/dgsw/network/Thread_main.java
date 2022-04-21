package kr.hs.dgsw.network;

import javax.sound.midi.Soundbank;

public class Thread_main {
    public static void main(String[] args){
        Thread1 t1 = new Thread1();
        Thread2 m2 = new Thread2();

        Thread t2 = new Thread(m2);

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i < 500; i++) {
                    System.out.printf("t3 Thread : %d \n", i);
                }
            }
        });


        t1.start();
        System.out.println("t1시작");
        t2.start();
        System.out.println("t2시작");
        t3.start();
        System.out.println("t3시작");

        //Lambda로 쓰레드 생성
        new Thread(() ->{
            for(int i=0; i<500; i++){
                System.out.println("t4 Thread: " + i);
            }
        }).start();

        for(int i=0; i<500; i++) {
            System.out.println("메인 쓰레드 : "+i);
        }
        //join 해주면 밑에 있는 코드는 join이 완료된 후 실행
        /*try{
            t1.join();
            t2.join();
            t3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
        System.out.println("종료");
    }
}
