package net.udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientTest {
	public static void main(String[] args) {
		DatagramSocket ds = null;
		InputStream is = null;
		try {
			ds = new DatagramSocket(9000);
			System.out.println("客户端在 9000 监听");

			is = new FileInputStream(new File("D:\\kidmadeto\\jvm\\src\\main\\java\\net\\udp\\a.txt"));

			byte[] b = new byte[is.available()];
			is.read(b);

			DatagramPacket dp = new DatagramPacket(b, 0, b.length);
			dp.setPort(9001);
			dp.setAddress(InetAddress.getLocalHost());

			ds.send(dp);
			System.out.println("文件发送成功");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				ds.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
