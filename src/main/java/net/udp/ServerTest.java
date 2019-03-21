package net.udp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerTest {
	public static void main(String[] args) {
		OutputStream os = null;
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(9001);
			System.out.println("在9001端口监听...");

			byte[] b = new byte[1024];
			DatagramPacket dp = new DatagramPacket(b, 0, b.length);

			ds.receive(dp);
			byte[] data = dp.getData();
			os = new FileOutputStream(new File("b.txt"));
			os.write(data, 0, dp.getLength());
			System.out.println("文件接收成功");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				ds.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
