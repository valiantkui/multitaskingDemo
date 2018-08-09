package com.kui.fc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;
public class DemoFileChannel {
	/**FileChannel需要通过FileOutputStream或FileInputStream
	 * 来获取。
	 * position(newPosition):设置position的值
	 * position():获取当前position的值
	 * 通过FileOutputStream得到文件通道只能执行写操作，如果在该通道上
	 * 执行读操作，抛出NonReadableChannelException。
	 * @throws Exception
	 */
	@Test
	public void testWrite() throws Exception{
		FileOutputStream out = new FileOutputStream
				(new File("1.txt"));
		//获取文件通道
		FileChannel fc = out.getChannel();
		fc.position(4);
		ByteBuffer buf = ByteBuffer.wrap("1234".getBytes());
		//可以写入内容：
		fc.write(buf);
		System.out.println(fc.position());
		ByteBuffer buf1 = ByteBuffer.wrap("5678".getBytes());
		fc.write(buf1);
		//不可以执行读取操作
		/*ByteBuffer readBuf = ByteBuffer.allocate(4);
		fc.read(readBuf);
		System.out.println(new String(readBuf.array()));*/
		//关闭通道和输出流
		fc.close();
		out.close();
	}
	/**调用read(ByteBuffer buf)时，position的值也会
	 * 做响应的增加。
	 * 在FileInputStream上得到FileChannel对象不能执行写操作，否则
	 * 抛出NonWritableChannelException
	 * @throws Exception
	 */
	@Test
	public void testRead() throws Exception{
		FileInputStream in = new FileInputStream("1.txt");
		FileChannel fc = in.getChannel();
		fc.position(4);
		ByteBuffer buf = ByteBuffer.allocate(8);
		fc.read(buf);
		System.out.println(fc.position());
		fc.write(buf);
		System.out.println(new String(buf.array()));
		fc.close();
		in.close();
	}
	/**RandomAccessFile:即可以读也可以写。
	 * mode:
	 * r:表示只读的
	 * rw:即可以读，也可以写
	 * position的默认值为0
	 * write()：写入时如果对应的位置原来有内容的话，将会
	 * 被覆盖。
	 * 如果执行write()用到的缓冲区时刚刚读取到的，写不进去。
	 * 到底时追加还是覆盖，看在执行write()方法之前的position
	 * 的值。
	 * @throws Exception
	 */
	@Test
	public void testRandomAccess() throws Exception{
		RandomAccessFile raf = new RandomAccessFile(
				new File("1.txt"), "rw");
		FileChannel fc = raf.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(8);
		fc.position(4);
		fc.read(buf);
		System.out.println(new String(buf.array()));
		System.out.println(fc.position());
		fc.write(buf);
		ByteBuffer wtbuf = ByteBuffer.wrap("abcd".getBytes());
		//fc.position(8);
		fc.write(wtbuf);
		fc.close();
		raf.close();
	}
}












