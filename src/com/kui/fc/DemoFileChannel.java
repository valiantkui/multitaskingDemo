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
	/**FileChannel��Ҫͨ��FileOutputStream��FileInputStream
	 * ����ȡ��
	 * position(newPosition):����position��ֵ
	 * position():��ȡ��ǰposition��ֵ
	 * ͨ��FileOutputStream�õ��ļ�ͨ��ֻ��ִ��д����������ڸ�ͨ����
	 * ִ�ж��������׳�NonReadableChannelException��
	 * @throws Exception
	 */
	@Test
	public void testWrite() throws Exception{
		FileOutputStream out = new FileOutputStream
				(new File("1.txt"));
		//��ȡ�ļ�ͨ��
		FileChannel fc = out.getChannel();
		fc.position(4);
		ByteBuffer buf = ByteBuffer.wrap("1234".getBytes());
		//����д�����ݣ�
		fc.write(buf);
		System.out.println(fc.position());
		ByteBuffer buf1 = ByteBuffer.wrap("5678".getBytes());
		fc.write(buf1);
		//������ִ�ж�ȡ����
		/*ByteBuffer readBuf = ByteBuffer.allocate(4);
		fc.read(readBuf);
		System.out.println(new String(readBuf.array()));*/
		//�ر�ͨ���������
		fc.close();
		out.close();
	}
	/**����read(ByteBuffer buf)ʱ��position��ֵҲ��
	 * ����Ӧ�����ӡ�
	 * ��FileInputStream�ϵõ�FileChannel������ִ��д����������
	 * �׳�NonWritableChannelException
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
	/**RandomAccessFile:�����Զ�Ҳ����д��
	 * mode:
	 * r:��ʾֻ����
	 * rw:�����Զ���Ҳ����д
	 * position��Ĭ��ֵΪ0
	 * write()��д��ʱ�����Ӧ��λ��ԭ�������ݵĻ�������
	 * �����ǡ�
	 * ���ִ��write()�õ��Ļ�����ʱ�ոն�ȡ���ģ�д����ȥ��
	 * ����ʱ׷�ӻ��Ǹ��ǣ�����ִ��write()����֮ǰ��position
	 * ��ֵ��
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












