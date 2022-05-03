package kr.hs.dgsw.network.lesson3;

public class CakeMaker extends Thread {
	private CakePlate cake;
	
	public CakeMaker(CakePlate cake) {
		this.cake=cake;
	}
	
	public void run() {
		for(int i=0;i<50;i++) {
			cake.makeBread();
		}
	}
}
