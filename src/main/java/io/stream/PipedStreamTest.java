package io.stream;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**  
 * PipedOutputStream和PipedInputStream分别是管道输出流和管道输入流。
 * 它们的作用是让多线程可以通过管道进行线程间的通讯。在使用管道通信时，必须将PipedOutputStream和PipedInputStream配套使用。
 * 使用管道通信时，大致的流程是：我们在线程A中向PipedOutputStream中写入数据，这些数据会自动的发送到与PipedOutputStream对应的PipedInputStream中，
 * 进而存储在PipedInputStream的缓冲中；此时，线程B通过读取PipedInputStream中的数据。就可以实现，线程A和线程B的通信。
 */  
public class PipedStreamTest {

	public static void main(String[] args) {   
        Sender t1 = new Sender();   
           
        Receiver t2 = new Receiver();   
           
        PipedOutputStream out = t1.getOutputStream();   
 
        PipedInputStream in = t2.getInputStream();   

        try {   
            //管道连接。下面2句话的本质是一样。
            //out.connect(in);   
            in.connect(out);   
               
            /**  
             * Thread类的START方法：  
             * 使该线程开始执行；Java 虚拟机调用该线程的 run 方法。   
             * 结果是两个线程并发地运行；当前线程（从调用返回给 start 方法）和另一个线程（执行其 run 方法）。   
             * 多次启动一个线程是非法的。特别是当线程已经结束执行后，不能再重新启动。   
             */
            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**  
 * 接收者线程  
 */  
class Receiver extends Thread {

	// 管道输入流对象。
	// 它和“管道输出流(PipedOutputStream)”对象绑定，
	// 从而可以接收“管道输出流”的数据，再让用户读取。
	private PipedInputStream in = new PipedInputStream();

	// 获得“管道输入流”对象
	public PipedInputStream getInputStream() {
		return in;
	}

	@Override
	public void run() {
//		readMessageOnce();
		 readMessageContinued() ;
	}

	// 从“管道输入流”中读取1次数据
	public void readMessageOnce() {
		// 虽然buf的大小是2048个字节，但最多只会从“管道输入流”中读取1024个字节。
		// 因为，“管道输入流”的缓冲区大小默认只有1024个字节。
		byte[] buf = new byte[2048];
		try {
			int len = in.read(buf);
			System.out.println(new String(buf, 0, len));
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 从“管道输入流”读取>1024个字节时，就停止读取
	public void readMessageContinued() {
		int total = 0;
		while (true) {
			byte[] buf = new byte[1024];
			try {
				int len = in.read(buf);
				total += len;
				System.out.println(new String(buf, 0, len));
				// 若读取的字节总数>1024，则退出循环。
				if (total > 1024)
					break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**  
 * 发送者线程  
 */  
class Sender extends Thread {

	// 管道输出流对象。
	// 它和“管道输入流(PipedInputStream)”对象绑定，
	// 从而可以将数据发送给“管道输入流”的数据，然后用户可以从“管道输入流”读取数据。
	private PipedOutputStream out = new PipedOutputStream();

	// 获得“管道输出流”对象
	public PipedOutputStream getOutputStream() {
		return out;
	}

	@Override
	public void run() {
//		writeShortMessage();
		 writeLongMessage();
	}

	// 向“管道输出流”中写入一则较简短的消息："this is a short message"
	private void writeShortMessage() {
		String strInfo = "this is a short message";
		try {
			out.write(strInfo.getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 向“管道输出流”中写入一则较长的消息
	private void writeLongMessage() {
		StringBuilder sb = new StringBuilder();
		// 通过for循环写入1020个字节
		for (int i = 0; i < 102; i++)
			sb.append("0123456789");
		// 再写入26个字节。
		sb.append("abcdefghijklmnopqrstuvwxyz");
		// str的总长度是1020+26=1046个字节
		String str = sb.toString();
		try {
			// 将1046个字节写入到“管道输出流”中
			out.write(str.getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}