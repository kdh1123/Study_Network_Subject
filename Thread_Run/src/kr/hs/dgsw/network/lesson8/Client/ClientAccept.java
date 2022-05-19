package kr.hs.dgsw.network.lesson8.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAccept {
	public Socket sc = null;
	
	private OutputStream os = null;
	private PrintWriter pw = null;
	private BufferedReader br = null;
	
	// ����
	public ClientAccept(Socket sc) {
		this.sc = sc;
		
		// �޽��� ������ ���� ��ü ����
		try {
			this.os = sc.getOutputStream();
			this.pw = new PrintWriter(this.os, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// �޽��� �ޱ� ���� ��ü ����
		InputStream is = null;
		try {
			is = sc.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		// Ŭ���̾�Ʈ �޽��� �Է� ����
		this.br = new BufferedReader(new InputStreamReader(is));
	}
	
	// �޽��� ������ �κ�
	public void sendMessage(String msg) {
		pw.println(msg);
	}
	
	
	// �޽��� �ޱ� �κ�
	public String readMessage() throws IOException {
		return br.readLine();
	}
}
