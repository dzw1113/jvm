package reflect;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Test {
	public static void printStackTrace(Class cls) {
		
		
		Map<Thread,StackTraceElement[]> map = Thread.currentThread().getAllStackTraces();
		Set<Thread> set = map.keySet();
		for (Thread thread : set) {
//			StackTraceElement[] elements = (new Throwable()).getStackTrace();
			StackTraceElement[] elements = map.get(thread);
			StringBuffer buf = new StringBuffer();
			buf.append("Stack for " + thread.getName() + ":");
			for (int i = 0; i < elements.length; i++) {
				buf.append("\n    " + elements[i].getClassName() + "." + elements[i].getMethodName() + "("
						+ elements[i].getFileName() + ":" + elements[i].getLineNumber() + ")");
			}
			System.out.println(buf.toString());
		}
		
		
	}

	public static void main(String args[]) {
		for (int i = 0; i < 5; i++) {
			MyThread th = new MyThread();
			th.setName("线程" + i);
			th.start();
		}
		// An example to show how to invoke it
		printStackTrace(Test.class);
	}
	
	static class MyThread extends Thread{
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(100);
				new ArrayList();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
