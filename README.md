### FileChannel
���ڶ�ȡ��д�롢ӳ��Ͳ����ļ���ͨ���� 
�ļ�ͨ�������ļ�����һ����ǰ position���ɶ�����в�ѯ���޸ġ�
���ļ��������һ���ɶ�д�ĳ��ȿɱ���ֽ����У����ҿ��Բ�ѯ���ļ��ĵ�ǰ��С��
д����ֽڳ����ļ��ĵ�ǰ��Сʱ���������ļ��Ĵ�С����ȡ ���ļ�ʱ�����С�ļ��Ĵ�С��
�ļ����ܻ���ĳ���������Ԫ���ݣ������Ȩ�ޡ��������ͺ������޸�ʱ�䣻����δ�������Ԫ���ݵķ���


----------

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
	}``

# �����Ĺ��߰�������Լ��䣩

### 1. ��������BlockingQueue
һ�������������֧�ֵ��н��������С��˶��а� FIFO���Ƚ��ȳ���ԭ���Ԫ�ؽ������򡣶��е�ͷ�� ���ڶ����д���ʱ�����Ԫ�ء����е�β�� ���ڶ����д���ʱ����̵�Ԫ�ء���Ԫ�ز��뵽���е�β�������л�ȡ�������ǴӶ���ͷ����ʼ���Ԫ�ء� 

����һ�����͵ġ��н绺���������̶���С�����������б��������߲����Ԫ�غ�ʹ������ȡ��Ԫ�ء�һ�������������Ļ��������Ͳ�������������������ͼ�����������з���Ԫ�ػᵼ�²�������������ͼ�ӿն�������ȡԪ�ؽ��������������� 

����֧�ֶԵȴ����������̺߳�ʹ�����߳̽�������Ŀ�ѡ��ƽ���ԡ�Ĭ������£�����֤����������Ȼ����ͨ������ƽ�� (fairness) ����Ϊ true ������Ķ��������� FIFO ˳������̡߳���ƽ��ͨ���ή������������Ҳ�����˿ɱ��Ժͱ����ˡ���ƽ���ԡ��� 


##### ������֪ʵ���ࣺ 

	ArrayBlockingQueue, DelayQueue, LinkedBlockingDeque, 
	LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue

### ArrayBlockingQueue��LinkedBlockingQueue

	public class BlockingQueueDemo {
		/**ʹ��ArrayBlockingQueue��������ʱ��Ҫָ��������
		 * 10����ʾ����������������ܹ�����10��Ԫ�ء�
		 * ���������������һ��Ԫ��
		 * 1.��������δ��ʱ����ִ�����²�����
		 * boolean add(E e):�ܹ���ӳɹ�������true
		 * void put(E e):�ܹ���ӳɹ�������������
		 * boolean offer(E e):�ܹ���ӳɹ�������true
		 * boolean offer(E e, long timeout, TimeUnit unit):ֱ��ִ����ӣ�����true
		 * timeout:ָ��ʱ���ֵ��unit��ָ���ȴ�ʱ��ĵ�λ��
		 * 2.������������ʱ����ִ�����²�����
		 * boolean add(E e):������ӳɹ����׳��쳣��Queue full
		 * void put(E e):������ӳɹ�����������,ֱ����������ȡ��Ԫ�غ��ͷš�
		 * boolean offer(E e):������ӳɹ�������false
		 * boolean offer(E e, long timeout, TimeUnit unit):��ָ����ʱ���������������
		 * �˲����������������ӳɹ�������ʱ�����������һֱ����״̬�����ʧ�ܡ�
		 * timeout:ָ��ʱ���ֵ��unit��ָ���ȴ�ʱ��ĵ�λ��
		 */
		@Test
		public void testProduce(){
			//������������
			BlockingQueue<Integer> que = 
					new ArrayBlockingQueue<Integer>(10);
			//ѭ�����10��
			for(int i =1;i<=10;i++){
				que.add(i);
			}
			/*boolean result = que.add(10);
			System.out.println(result);*/
			/*try {
				que.put(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			/*boolean result1 = que.offer(10);
			System.out.println(result1);*/
			try {
				boolean result1 = que.offer(10, 3, TimeUnit.SECONDS);
				System.out.println(result1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println();
		}
		/**���������л�ȡԪ��
		 * 1.��ǰ�����л��п�ȡ��Ԫ��ʱ��ִ�����·�����
		 * E remove():��ȡ���ȴ�ŵ�Ԫ�أ����ҽ���Ԫ�شӶ�����ɾ����
		 * boolean remove(Object o):ɾ��ָ����Ԫ�أ�ɾ���ɹ�����true,
		 * ɾ��ʧ�ܷ���false����������ָ����Ԫ�ء�
		 * E take() throws InterruptedException:�Ƴ�������ӵ�Ԫ�أ�������
		 * Ԫ�ط��ء�ʹ��ʱ��Ҫ�����쳣��
		 * E poll():�Ƴ�������ӵ�Ԫ�أ�������Ԫ�ط���
		 * E poll(long timeout, TimeUnit unit)  throws InterruptedException
		 * �Ƴ�������ӵ�Ԫ�أ�������Ԫ�ط���
		 * 2.��ǰ��������Ԫ��ʱ��ִ�����·�����
		 * E remove():�׳��쳣NoSuchElementException
		 * boolean remove(Object o):ɾ��ʧ�ܣ�����false
		 * E take() throws InterruptedException:����������ֱ���������ߴ������
		 * �������ͷſ���
		 * E poll():����null
		 * E poll(long timeout, TimeUnit unit)  throws InterruptedException:
		 * ���Ȳ�����������ָ����ʱ��������������ߴ���Ԫ�أ���ô����ȡ��������ӵ�Ԫ�أ�
		 * �����ָ����ʱ���ڣ���û��Ԫ�ش��룬�򷵻�null.
		 */
		@Test
		public void testConsume(){
			BlockingQueue<Integer> que = 
					new ArrayBlockingQueue<Integer>(10);
			//que.add(1);
			//Integer result = que.remove();
			//boolean result = que.remove(1);
			/*Integer result=null;
			try {
				result = que.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			//Integer result = que.poll();
			Integer result=null;
			try {
				result = que.poll(3, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}
		/**ʹ�÷�����ArrayBlockingQueue��һ����
		 * �ڴ����������ж���ʱ����Ҫָ�����������޽�ġ�
		 * ����ܹ����2147483647��Integer.MAX_VALUE��
		 */
		@Test
		public void testLinkedBQ(){
			BlockingQueue<Integer> que = new LinkedBlockingQueue<>();
			System.out.println();
		}
	}


### 2. PriorityBlockingQueue

	class Student implements Comparable<Student>{
		private String name;
		private double score;
		public Student(String name, double score) {
			this.name = name;
			this.score = score;
		}
		@Override
		public int compareTo(Student stu) {
			if(score-stu.getScore()>0){
				return 1;
			}else if(score-stu.getScore()<0){
				return -1;
			}else{
				return 0;
			}
		}
		@Override
		public String toString() {
			return "Student [name=" + name + ", score=" + score + "]";
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}
	}
	/**�� �� �� �� �� �� �� �� �� ��PriorityBlockingQueue
	 * �ܹ�ʵ�ֶԱ����ڵ�ǰ�����е�Ԫ�ؽ�������ע��Ԫ�ض�Ӧ��
	 * ����Ҫʵ��Comparable�ӿ�
	 * compareTo(E e)�������Ǹ��ݸ÷������㷨�й�
	 * ����������Ҫָ��������Ĭ���ṩһ��11��Ԫ�ص����������Ԫ�س���11��ʱ�Զ����ݡ�
	 * @author jinxf
	 *
	 */
	public class PriorityBlockingQueueDemo {
		public static void main(String[] args) {
			BlockingQueue<Student> que = new PriorityBlockingQueue<Student>();
			que.add(new Student("jack", 80));
			que.add(new Student("rose",85));
			que.add(new Student("tom",70));
			/*while(!que.isEmpty()){
				System.out.println(que.remove());
			}*/
			while(que.size()>0){
				System.out.println(que.poll());
			}
		}
	}



----------

### 3. ����Mapӳ��ConcurrentMap

##### - ConcurrentMap
HashMap���̲߳�����ȫ��Hashtable�̲߳�����ȫ�ġ�
�鿴HashtableԴ�룬�������Ķ�ȡ����ӡ���ѯԪ�صĸ����ȷ����������ͬ��������ȫ�Ĵ��۾���Ч��̫�͡�

- ##### ��ô��α�֤ʵ���̰߳�ȫ��ͬʱ���Ч�ʣ�

	���ֻ����������һ��Ԫ�أ�����Ч�ʻ��ر�ߣ�����ʵ��̫�ѡ�
	
���˼�룺����ֶ������ֶ�Ͱ��
	/**�����˷ֶ������ֶ�Ͱ���ĸ��
	 * ����16�Σ�Ͱ�����������޸�ÿһ��Ԫ�ص�ʱ�򣬻�������Ԫ��
	 * ���ڵ�Ͱ���������ݣ�����Ͱ�����ݲ���Ӱ�졣
	 * ConcurrentMap<K, V> extends Map<K, V>
	 * �ײ��ʵ�ֻ��Ʋ�ͬ��ʹ�÷������ơ�
	 */

���ԣ�

	@Test
	public void testConcurrentMap(){
		ConcurrentMap<Integer,String> map=
				new ConcurrentHashMap<Integer,String>();
		map.put(1, "һ");
		map.put(2, "��");
		map.put(3, "��");
		map.put(4, "��");
		map.put(5, "��");
		map.put(6, "��");
		map.put(7, "");
		map.put(8, "");
		map.put(9, "");
		map.put(10, "");
		map.put(11, "");
		map.put(12, "");
		map.put(13, "");
		map.put(14, "");
		map.put(15, "");
		map.put(16, "");
		map.put(17, "");
		System.out.println(map.get(1));
	}

### 4. ��������ӳ��ConcurrentNavigableMap
����ָ����key��ȡ������������map��headMap()��tailMap()��subMap()

	/**ConcurrentNavigableMap���Խ����ϸ���key��ֵ���л�ȡ��������
	 * ��map��ConcurrentNavigableMap<K,V>��
	 * headMap(K toKey):��ȡkey��ֵС��toKey����Ԫ��
	 * subMap(K fromKey,K toKey):��ȡkey��ֵ����[fromKey,toKey)������Ԫ��
	 * tailMap(K fromKey):��ȡkeyֵ���ڵ���fromKey������Ԫ��
	 * ִ�����ϼ��������󣬶�ԭmap�е����ݱ����ᱻɾ����;�����޸���map��
	 * value��ֵ��ԭmapҲ����Ÿı䣨���仰��˵��ȡ�������ã���
	 */

���ԣ�

	@Test
	public void testConcurrentNavigableMap(){
		ConcurrentNavigableMap<Integer,String> map =
				new ConcurrentSkipListMap<Integer,String>();
		map.put(4, "��");
		map.put(2, "��");
		map.put(3, "��");
		map.put(6, "��");
		map.put(5, "��");
		map.put(1, "һ");
		
		ConcurrentNavigableMap<Integer, String> headMap=
				map.headMap(2);
		headMap.put(1, "������������");
		printMap(headMap);
		ConcurrentNavigableMap<Integer, String> subMap=
				map.subMap(3, 4);
		printMap(subMap);
		ConcurrentNavigableMap<Integer,String> tailMap=
				map.tailMap(5);
		printMap(tailMap);
		//System.out.println(map.get(2));
		printMap(map);
	}
	private void printMap(Map<Integer,String> map){
		System.out.println("===========================");
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"->"+entry.getValue());
		}
	}

### 5. ����CountDownLatch���ص㣩

	/**new CountDownLatch(2):�������̣߳�ʵ����ʱ������
	 * ָ������ֵ���Ǽ���
	 * countDown():ÿ����һ�Σ���������1
	 * await()�������������ֱ����������ֵ��Ϊ0�������ͷš�
	 * @author jinxf
	 *
	 */

���룺

	public class CountDownLatchDemo {
		public static void main(String[] args) {
			//���������߳�
			CountDownLatch cdl = new CountDownLatch(2);
			//��ʼ����//������߳�
			new Thread(new BuyGuo(cdl)).start();
			//��˵��߳�
			new Thread(new BuyCai(cdl)).start();
			try {
				cdl.await();//ʹ�ñ�����������,ָ����������ֵ��Ϊ0ʱ(ye���������̶߳�ִ������)�������ſ�
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("��ʼ����");
		}
	}
	class BuyGuo implements Runnable{
		private CountDownLatch cdl;
		public BuyGuo(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run() {
			System.out.println("���������...");
			//��run���������ִ��countDown()��ÿ����һ�Σ���������1
			cdl.countDown();
		}
	}
	class BuyCai implements Runnable{
		private CountDownLatch cdl;
		public BuyCai(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run(){
			System.out.println("���������...");
			cdl.countDown();
		}
	}



### 6. դ�� CyclicBarrier

	/**դ��CyclicBarrier��ͨ�����ڹ������̡߳�
	 * һ�������߳��м�ĳ��λ�ò����ȴ�
	 * await()����������ֱ����������ֵ����Ϊ0ʱ�������ͷš�
	 * @author jinxf
	 */
	public class CyclicBarrierDemo {
		public static void main(String[] args) {
			CyclicBarrier cb = new CyclicBarrier(2);
			new Thread(new Horse1Runner(cb)).start();
			new Thread(new Horse2Runner(cb)).start();
		}
	}
	class Horse1Runner implements Runnable{
		private CyclicBarrier cb;
		public Horse1Runner(CyclicBarrier cb){
			this.cb = cb;
		}
		public void run(){
			System.out.println("��һƥ��������������...");
			try {
				cb.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("��һƥ��ʼ��...");
		}
	}
	class Horse2Runner implements Runnable{
		private CyclicBarrier cb;
		public Horse2Runner(CyclicBarrier cb){
			this.cb = cb;
		}
		public void run(){
			System.out.println("�ڶ�ƥ��������ing...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}		
			System.out.println("�ڶ�ƥ��������������...");
			try {
				cb.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("�ڶ�ƥ��ʼ��...");
		}
	}



### 7. Exchanger

	/**�����������߳�֮�������Ϣ������
	 * @author jinxf
	 */
	public class ExchangerDemo {
		public static void main(String[] args) {
			Exchanger<String> ex = new Exchanger<String>();
			new Thread(new Spy1Runner(ex)).start();
			new Thread(new Spy2Runner(ex)).start();
		}
	}
	class Spy1Runner implements Runnable{
		private Exchanger<String> ex;
		public Spy1Runner(Exchanger<String> ex){
			this.ex = ex;
		}
		public void run(){
			try {
				String result = ex.exchange("�����ǵػ�");
				System.out.println("���2�������1����Ϣ��"+result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	class Spy2Runner implements Runnable{
		private Exchanger<String> ex;
		public Spy2Runner(Exchanger<String> ex){
			this.ex = ex;
		}
		public void run(){
			try {
				String result = ex.exchange("���������");
				System.out.println("���1�������2����Ϣ��"+result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


### 8. �̳߳أ�����
ʹ���̳߳ص�Ŀ�ģ�����Ƶ�������������̣߳����Ч�ʡ�

	public class PoolDemo {
		/**ThreadPoolExecutor(int corePoolSize,
	                          int maximumPoolSize,
	                          long keepAliveTime,
	                          TimeUnit unit,
	                          BlockingQueue<Runnable> workQueue)
		 * corePoolSize�������߳������̳߳��������������������߳̿�ʼ������һֱ
		 * �����������ﵽ��ֵʱ�������ٴ�����
		 * maximumPoolSize���������������߳���=�����߳���+��ʱ�߳���
		 * keepAliveTime����ʱ�̲߳�ʹ�ú�����ʱ�����ֵ
		 * unit����ʱ�̲߳�ʹ�ú�����ʱ��ĵ�λ
		 * workQueue���������У����ã������̶߳���ʹ��֮���������󱣴浽
		 * ���������С�
		 */
		@Test
		public void testThreadPoolExecutor(){
			ExecutorService es = new ThreadPoolExecutor(5,10,3,TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(5),new RejectedExecutionHandler() {
						//���̳߳������������߳�ȫ����ʹ�ã�����������������ʱ�߳�ȫ����ʹ�ã���
					    //���ø÷�����
						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
							System.out.println("�̳߳�����...");
						}
					});
			for(int i =1;i<=16;i++){
				es.submit(new MyRunner());
			}
	//�ر��̳߳�:���������رգ� ִ�и÷����󣬲��ڽ����µ���
			//�󣬻Ὣ�Ѿ����յ��̴߳�����󣬲Źرա�
			es.shutdown();
		}
	}
	class MyRunner implements Runnable{
		public void run(){
			System.out.println("�̱߳�������.."+Thread.currentThread().getId());
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

ʹ�ó���  

	/**ThreadPoolExecutor(0, Integer.MAX_VALUE,
	                          60L, TimeUnit.SECONDS,
	                          new SynchronousQueue<Runnable>())
		 * �����߳�0��
		 * ��ʱ�߳������Ե�Integer.MAX_VALUE
		 * 60L, TimeUnit.SECONDS:��ʱ�̲߳���ʹ�ö�����ʱ��
		 * SynchronousQueue���������������ֻ�ܱ���һ������
		 * �����С����
		 * �����ڸ߲�����������ĳ����С�
		 */
		@Test
		public void testCachedThreadPool(){
			ExecutorService es = Executors.newCachedThreadPool();
		}
		/**ThreadPoolExecutor(nThreads, nThreads,
	                          0L, TimeUnit.MILLISECONDS,
	                          new LinkedBlockingQueue<Runnable>()
		 * �����߳�����Ҫ�û�ָ����
		 * ��ʱ�߳���0��
		 * 0L, TimeUnit.MILLISECONDS����ʱ�̵߳Ĵ��ʱ�� 0
		 * LinkedBlockingQueue�����������п��Ա���Integer.MAX_VALUE
		 * С���Ӵ����
		 * ���ó����������������ѹ���� 
		 */
		@Test
		public void testFixedThreadPool(){
			ExecutorService es = Executors.newFixedThreadPool(10);
		}
	}

### 9. Callable

	/**Runnable:run()����û�з���ֵ���ͣ�Ҳ�����׳��쳣��
	 * ������ͨ���̳߳�������Ҳ����ͨ��Thread��������
	 * Callable��call()�����з���ֵ���ͣ�Ҳ�����׳��쳣��
	 * ֻ��ͨ���̳߳�������
	 * @author jinxf
	 */

	public class CallableDemo {
		public static void main(String[] args) {
			ExecutorService ex = Executors.newCachedThreadPool();
			Future<String> future = ex.submit(new MyCb());
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	class MyCb implements Callable<String>{
		@Override
		public String call() throws Exception {
			System.out.println("�̱߳�������...");
			return "success";
		}
		
	}
	
	3.9ForkJoinPool���˽����壩
	
	3.10�� Lock
	public class LockDemo1 {
		public static String name="����";
		public static String gender="��";
		public static void main(String[] args) {
			new Thread(new WriteRunner1()).start();
			new Thread(new ReadRunner1()).start();
		}
	}
	class WriteRunner1 implements Runnable{
		public void run(){
			while(true){
				if("����".equals(LockDemo1.name)){
					LockDemo1.name = "��÷÷";
					LockDemo1.gender = "Ů";
				}else{
					LockDemo1.name = "����";
					LockDemo1.gender = "��";
				}
			}
		}
	}
	class ReadRunner1 implements Runnable{
		public void run(){
			while(true){
				System.out.println(LockDemo1.name+","+LockDemo1.gender);
			}
		}
	}


	���г�����֣�
	����:Ů
	��÷÷:��
	��ν����ʹ��֮ǰѧ��ͬ������Lock��
1. synchronized

	public class LockDemo2 {
		public static String name="����";
		public static String gender="��";
		public static void main(String[] args) {
			new Thread(new WriteRunner2()).start();
			new Thread(new ReadRunner2()).start();
		}
	}
	class WriteRunner2 implements Runnable{
		public void run(){
			while(true){
				synchronized(LockDemo2.class){
					if("����".equals(LockDemo2.name)){
						LockDemo2.name = "��÷÷";
						LockDemo2.gender = "Ů";
					}else{
						LockDemo2.name = "����";
						LockDemo2.gender = "��";
					}
				}
			}
		}
	}
	class ReadRunner2 implements Runnable{
		public void run(){
			while(true){
				synchronized (LockDemo2.class) {
					System.out.println(LockDemo2.name+","+LockDemo2.gender);
				}
			}
		}
	}

2. Lock lock = new ReentrantLock();

	public class LockDemo3 {
		public static String name="����";
		public static String gender="��";
		public static void main(String[] args) {
			Lock lock = new ReentrantLock();
			new Thread(new WriteRunner3(lock)).start();
			new Thread(new ReadRunner3(lock)).start();
		}
	}
	class WriteRunner3 implements Runnable{
		private Lock lock;
		public WriteRunner3(Lock lock){
			this.lock = lock;
		}
		public void run(){
			while(true){
				lock.lock();
					if("����".equals(LockDemo3.name)){
						LockDemo3.name = "��÷÷";
						LockDemo3.gender = "Ů";
					}else{
						LockDemo3.name = "����";
						LockDemo3.gender = "��";
					}
				//�ͷ����Ĳ�����������������ܳ����쳣��Ҫ�������Ƿ�ŵ�finally������У�
				lock.unlock();
			}
		}
	}
	class ReadRunner3 implements Runnable{
		private Lock lock;
		public ReadRunner3(Lock lock){
			this.lock = lock;
		}
		public void run(){
			while(true){
				lock.lock();
					System.out.println(LockDemo3.name+","+LockDemo3.gender);
				lock.unlock();
			}
		}
	}

�Աȿ���̨�����

�������ײ�ʹ��ԭ��

3. ��д��ReadWriteLock
	
	public class LockDemo4 {
		public static String name="����";
		public static String gender="��";
		public static void main(String[] args) {
			ReadWriteLock lock = new ReentrantReadWriteLock();
			new Thread(new WriteRunner4(lock)).start();
			new Thread(new ReadRunner4(lock)).start();
		}
	}
	class WriteRunner4 implements Runnable{
		private ReadWriteLock lock;
		public WriteRunner4(ReadWriteLock lock){
			this.lock = lock;
		}
		public void run(){
			while(true){
				lock.writeLock().lock();
					if("����".equals(LockDemo4.name)){
						LockDemo4.name = "��÷÷";
						LockDemo4.gender = "Ů";
					}else{
						LockDemo4.name = "����";
						LockDemo4.gender = "��";
					}
				//�ͷ����Ĳ�����������������ܳ����쳣��Ҫ�������Ƿ�ŵ�finally������У�
				lock.writeLock().unlock();
			}
		}
	}
	class ReadRunner4 implements Runnable{
		private ReadWriteLock lock;
		public ReadRunner4(ReadWriteLock lock){
			this.lock = lock;
		}
		public void run(){
			while(true){
				lock.readLock().lock();
					System.out.println(LockDemo4.name+","+LockDemo4.gender);
				lock.readLock().unlock();
			}
		}
	}

### 10. ԭ����

	public class AtomicDemo5 {
		public static int num = 0;
		public static void main(String[] args) {
			CountDownLatch cdl = new CountDownLatch(2);
			new Thread(new ChangeRunner1(cdl)).start();
			new Thread(new ChangeRunner2(cdl)).start();
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(num);
		}
	}
	class ChangeRunner1 implements Runnable{
		private CountDownLatch cdl;
		public ChangeRunner1(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run(){
			for (int i = 0; i < 100000; i++) {
				AtomicDemo5.num++;
			}
			cdl.countDown();
		}
	}
	class ChangeRunner2 implements Runnable{
		private CountDownLatch cdl;
		public ChangeRunner2(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run(){
			for (int i = 0; i < 100000; i++) {
				AtomicDemo5.num++;
			}
			cdl.countDown();
		}
	}


����num��ֵΪ2000��û�����⡣���ǽ������߳��е�1000��Ϊ100000���ٶ�β���num��ֵ��С��200000������ԭ��

���synchronizedͬ�������������Խ�������⣬���ǻ��б�Ľ��������ԭ����AtomicInteger
public static int num = 0;
��Ϊ��
public static AtomicInteger num = new AtomicInteger(0);

TestDemo7.num++;
��Ϊ����������
TestDemo7.num.addAndGet(1);

