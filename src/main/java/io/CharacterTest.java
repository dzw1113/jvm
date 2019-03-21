package io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 
 * @author dzw
 *
 */
public class CharacterTest {


	public static void main(String[] args) {
		char c = '中';
		System.out.println(getBytesUTF8(c).length);
		System.out.println(getBytesGBK(c).length);
		char n = '1';
		System.out.println(getBytesUTF8(n).length);
		System.out.println(getBytesGBK(n).length);
		char s = 'z';
		System.out.println(getBytesUTF8(s).length);
		System.out.println(getBytesGBK(s).length);
		System.out.println(getBytesGBK(s)[0]);
		System.out.println(getBytesGBK(s)[1]);
		System.out.println(getBytesUTF8(s)[0]);
		
		byte []b = new byte[1];
		b[0] = 122;
		System.out.println(new String(b));
		

		System.out.println(getBytesUTF8(c)[0]);
		System.out.println(getBytesUTF8(c)[1]);
		System.out.println(getBytesUTF8(c)[2]);

		byte []b1 = new byte[3];
		b1[0] = -28;
		b1[1] = -72;
		b1[2] = -83;
		System.out.println(new String(b1));
		
		int num = 42;
		System.out.println(String.valueOf(num).length());
		short so = 42;
		System.out.println(String.valueOf(so).length());
	}

	public static byte[] getBytesUTF8(char c) {
		Charset cs = Charset.forName("utf-8");
		CharBuffer cb = CharBuffer.allocate(1);
		cb.put(c);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}

	public static byte[] getBytesGBK(char c) {
		Charset cs = Charset.forName("GBK");
		CharBuffer cb = CharBuffer.allocate(1);
		cb.put(c);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}
}
