package kr.hs.dgsw.network;

public class Thread_synchronized implements Runnable{

    private int i=0;
    @Override
    public void run() {
        while(i<500) {
            show();
        }
    }
    
    public synchronized void show(){
        for(int j=0; j<2000; j++){
            System.out.printf(Thread.currentThread() +" i : "+ i);
            System.out.println();
            i++;
        }
    }
}
