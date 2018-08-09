### FileChannel
用于读取、写入、映射和操作文件的通道。 
文件通道在其文件中有一个当前 position，可对其进行查询和修改。
该文件本身包含一个可读写的长度可变的字节序列，并且可以查询该文件的当前大小。
写入的字节超出文件的当前大小时，则增加文件的大小；截取 该文件时，则减小文件的大小。
文件可能还有某个相关联的元数据，如访问权限、内容类型和最后的修改时间；此类未定义访问元数据的方法


----------

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
		 * 做相应的增加。
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
	}``

# 并发的工具包（理解性记忆）

### 1. 阻塞队列BlockingQueue
一个由数组或链表支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。队列的头部 是在队列中存在时间最长的元素。队列的尾部 是在队列中存在时间最短的元素。新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。 

这是一个典型的“有界缓存区”，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。 

此类支持对等待的生产者线程和使用者线程进行排序的可选公平策略。默认情况下，不保证是这种排序。然而，通过将公平性 (fairness) 设置为 true 而构造的队列允许按照 FIFO 顺序访问线程。公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”。 


##### 所有已知实现类： 

	ArrayBlockingQueue, DelayQueue, LinkedBlockingDeque, 
	LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue

### ArrayBlockingQueue和LinkedBlockingQueue

	public class BlockingQueueDemo {
		/**使用ArrayBlockingQueue创建对象时需要指定容量。
		 * 10：表示该阻塞队列中最多能够保存10个元素。
		 * 向阻塞队列中添加一个元素
		 * 1.阻塞队列未满时，再执行以下操作：
		 * boolean add(E e):能够添加成功，返回true
		 * void put(E e):能够添加成功，并产生阻塞
		 * boolean offer(E e):能够添加成功，返回true
		 * boolean offer(E e, long timeout, TimeUnit unit):直接执行添加，返回true
		 * timeout:指定时间的值，unit：指定等待时间的单位。
		 * 2.阻塞队列已满时，再执行以下操作：
		 * boolean add(E e):不能添加成功，抛出异常：Queue full
		 * void put(E e):不能添加成功，产生阻塞,直到有消费者取走元素后释放。
		 * boolean offer(E e):不能添加成功，返回false
		 * boolean offer(E e, long timeout, TimeUnit unit):在指定的时间内如果阻塞出现
		 * 了不满的情况，可以添加成功；到达时间后，阻塞队列一直满的状态，添加失败。
		 * timeout:指定时间的值，unit：指定等待时间的单位。
		 */
		@Test
		public void testProduce(){
			//创建阻塞队列
			BlockingQueue<Integer> que = 
					new ArrayBlockingQueue<Integer>(10);
			//循环添加10个
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
		/**从阻塞队列获取元素
		 * 1.当前队列中还有可取的元素时，执行以下方法：
		 * E remove():获取最先存放的元素，并且将该元素从队列中删除。
		 * boolean remove(Object o):删除指定的元素，删除成功返回true,
		 * 删除失败返回false。并不返回指定的元素。
		 * E take() throws InterruptedException:移除最先添加的元素，并将该
		 * 元素返回。使用时需要捕获异常。
		 * E poll():移除最先添加的元素，并将该元素返回
		 * E poll(long timeout, TimeUnit unit)  throws InterruptedException
		 * 移除最先添加的元素，并将该元素返回
		 * 2.当前队列中无元素时，执行以下方法：
		 * E remove():抛出异常NoSuchElementException
		 * boolean remove(Object o):删除失败，返回false
		 * E take() throws InterruptedException:产生阻塞，直到有生产者存入对象，
		 * 阻塞才释放开。
		 * E poll():返回null
		 * E poll(long timeout, TimeUnit unit)  throws InterruptedException:
		 * 首先产生阻塞，在指定的时间内如果有生产者存入元素，那么将获取到最先添加的元素；
		 * 如果在指定的时间内，并没有元素存入，则返回null.
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
		/**使用方法和ArrayBlockingQueue是一样。
		 * 在创建阻塞队列对象时不需要指定容量，“无界的”
		 * 最多能够存放2147483647（Integer.MAX_VALUE）
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
	/**具 有 优 先 级 的 阻 塞 队 列PriorityBlockingQueue
	 * 能够实现对保存在当前队列中的元素进行排序；注意元素对应的
	 * 类需要实现Comparable接口
	 * compareTo(E e)：排序是根据该方法的算法有关
	 * 创建对象不需要指定容量，默认提供一个11个元素的容量，添加元素超过11个时自动扩容。
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

### 3. 并发Map映射ConcurrentMap

##### - ConcurrentMap
HashMap非线程并发安全，Hashtable线程并发安全的。
查看Hashtable源码，发现它的读取、添加、查询元素的个数等方法都添加了同步锁，安全的代价就是效率太低。

- ##### 那么如何保证实现线程安全的同时提高效率？

	如果只锁被操作的一个元素，并发效率会特别高，但是实现太难。
	
解决思想：引入分段锁（分段桶）
	/**引入了分段锁（分段桶）的概念。
	 * 分了16段（桶），当将来修改每一个元素的时候，会锁定该元素
	 * 所在的桶的所有数据，其他桶的数据不受影响。
	 * ConcurrentMap<K, V> extends Map<K, V>
	 * 底层的实现机制不同，使用方法相似。
	 */

测试：

	@Test
	public void testConcurrentMap(){
		ConcurrentMap<Integer,String> map=
				new ConcurrentHashMap<Integer,String>();
		map.put(1, "一");
		map.put(2, "二");
		map.put(3, "三");
		map.put(4, "四");
		map.put(5, "五");
		map.put(6, "六");
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

### 4. 并发导航映射ConcurrentNavigableMap
根据指定的key获取符合条件的子map，headMap()、tailMap()、subMap()

	/**ConcurrentNavigableMap可以将集合根据key的值进行获取符合条件
	 * 子map（ConcurrentNavigableMap<K,V>）
	 * headMap(K toKey):获取key的值小于toKey所有元素
	 * subMap(K fromKey,K toKey):获取key的值介于[fromKey,toKey)的所有元素
	 * tailMap(K fromKey):获取key值大于等于fromKey的所有元素
	 * 执行以上几个方法后，对原map中的数据被不会被删除掉;但是修改子map中
	 * value的值后，原map也会跟着改变（换句话来说获取的是引用）。
	 */

测试：

	@Test
	public void testConcurrentNavigableMap(){
		ConcurrentNavigableMap<Integer,String> map =
				new ConcurrentSkipListMap<Integer,String>();
		map.put(4, "四");
		map.put(2, "二");
		map.put(3, "三");
		map.put(6, "六");
		map.put(5, "五");
		map.put(1, "一");
		
		ConcurrentNavigableMap<Integer, String> headMap=
				map.headMap(2);
		headMap.put(1, "当怪物来敲门");
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

### 5. 闭锁CountDownLatch（重点）

	/**new CountDownLatch(2):管理几个线程，实例化时构造中
	 * 指定的数值就是几。
	 * countDown():每调用一次，计数器减1
	 * await()：会产生阻塞，直到计算器的值变为0，阻塞释放。
	 * @author jinxf
	 *
	 */

代码：

	public class CountDownLatchDemo {
		public static void main(String[] args) {
			//管理两个线程
			CountDownLatch cdl = new CountDownLatch(2);
			//开始做饭//买锅的线程
			new Thread(new BuyGuo(cdl)).start();
			//买菜的线程
			new Thread(new BuyCai(cdl)).start();
			try {
				cdl.await();//使用闭锁产生阻塞,指定计数器的值变为0时(ye就是两个线程都执行完了)，阻塞放开
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("开始做饭");
		}
	}
	class BuyGuo implements Runnable{
		private CountDownLatch cdl;
		public BuyGuo(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run() {
			System.out.println("锅买回来了...");
			//在run方法的最后，执行countDown()，每调用一次，计数器减1
			cdl.countDown();
		}
	}
	class BuyCai implements Runnable{
		private CountDownLatch cdl;
		public BuyCai(CountDownLatch cdl){
			this.cdl = cdl;
		}
		public void run(){
			System.out.println("菜买回来了...");
			cdl.countDown();
		}
	}



### 6. 栅栏 CyclicBarrier

	/**栅栏CyclicBarrier，通常用在管理多个线程。
	 * 一般用在线程中间某个位置产生等待
	 * await()产生阻塞，直到计算器的值被改为0时，阻塞释放。
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
			System.out.println("第一匹马来到了起跑线...");
			try {
				cb.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("第一匹马开始跑...");
		}
	}
	class Horse2Runner implements Runnable{
		private CyclicBarrier cb;
		public Horse2Runner(CyclicBarrier cb){
			this.cb = cb;
		}
		public void run(){
			System.out.println("第二匹马来肚子ing...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}		
			System.out.println("第二匹马来到了起跑线...");
			try {
				cb.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("第二匹马开始跑...");
		}
	}



### 7. Exchanger

	/**适用在两个线程之间进行信息交互。
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
				String result = ex.exchange("天王盖地虎");
				System.out.println("间谍2传给间谍1的信息："+result);
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
				String result = ex.exchange("宝塔镇河妖");
				System.out.println("间谍1传给间谍2的信息："+result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


### 8. 线程池！！！
使用线程池的目的：避免频繁创建和销毁线程，提高效率。

	public class PoolDemo {
		/**ThreadPoolExecutor(int corePoolSize,
	                          int maximumPoolSize,
	                          long keepAliveTime,
	                          TimeUnit unit,
	                          BlockingQueue<Runnable> workQueue)
		 * corePoolSize：核心线程数（线程池启动后，立即创建核心线程开始创建，一直
		 * 创建到数量达到阈值时，将不再创建）
		 * maximumPoolSize：池中允许的最大线程数=核心线程数+临时线程数
		 * keepAliveTime：临时线程不使用后保留的时间的数值
		 * unit：临时线程不使用后保留的时间的单位
		 * workQueue：阻塞队列，作用：核心线程都被使用之后，再来请求保存到
		 * 阻塞队列中。
		 */
		@Test
		public void testThreadPoolExecutor(){
			ExecutorService es = new ThreadPoolExecutor(5,10,3,TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(5),new RejectedExecutionHandler() {
						//当线程池已满（核心线程全部被使用，阻塞队列已满，临时线程全部被使用），
					    //调用该方法。
						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
							System.out.println("线程池已满...");
						}
					});
			for(int i =1;i<=16;i++){
				es.submit(new MyRunner());
			}
	//关闭线程池:不会立即关闭， 执行该方法后，不在接收新的请
			//求，会将已经接收的线程处理完后，才关闭。
			es.shutdown();
		}
	}
	class MyRunner implements Runnable{
		public void run(){
			System.out.println("线程被处理中.."+Thread.currentThread().getId());
			try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

使用场景  

	/**ThreadPoolExecutor(0, Integer.MAX_VALUE,
	                          60L, TimeUnit.SECONDS,
	                          new SynchronousQueue<Runnable>())
		 * 核心线程0个
		 * 临时线程数可以到Integer.MAX_VALUE
		 * 60L, TimeUnit.SECONDS:临时线程不被使用而存活的时间
		 * SynchronousQueue：阻塞队列中最多只能保存一个请求
		 * 大池子小队列
		 * 适用在高并发，短请求的场景中。
		 */
		@Test
		public void testCachedThreadPool(){
			ExecutorService es = Executors.newCachedThreadPool();
		}
		/**ThreadPoolExecutor(nThreads, nThreads,
	                          0L, TimeUnit.MILLISECONDS,
	                          new LinkedBlockingQueue<Runnable>()
		 * 核心线程数需要用户指定。
		 * 临时线程数0个
		 * 0L, TimeUnit.MILLISECONDS：临时线程的存活时间 0
		 * LinkedBlockingQueue：阻塞队列中可以保存Integer.MAX_VALUE
		 * 小池子大队列
		 * 适用场景：减轻服务器的压力。 
		 */
		@Test
		public void testFixedThreadPool(){
			ExecutorService es = Executors.newFixedThreadPool(10);
		}
	}

### 9. Callable

	/**Runnable:run()方法没有返回值类型，也不能抛出异常。
	 * 即可以通过线程池启动，也可以通过Thread类启动。
	 * Callable：call()方法有返回值类型，也可以抛出异常；
	 * 只能通过线程池启动。
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
			System.out.println("线程被处理了...");
			return "success";
		}
		
	}
	
	3.9ForkJoinPool（了解意义）
	
	3.10锁 Lock
	public class LockDemo1 {
		public static String name="李雷";
		public static String gender="男";
		public static void main(String[] args) {
			new Thread(new WriteRunner1()).start();
			new Thread(new ReadRunner1()).start();
		}
	}
	class WriteRunner1 implements Runnable{
		public void run(){
			while(true){
				if("李雷".equals(LockDemo1.name)){
					LockDemo1.name = "韩梅梅";
					LockDemo1.gender = "女";
				}else{
					LockDemo1.name = "李雷";
					LockDemo1.gender = "男";
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


	运行程序出现：
	李雷:女
	韩梅梅:男
	如何解决？使用之前学的同步锁（Lock）
1. synchronized

	public class LockDemo2 {
		public static String name="李雷";
		public static String gender="男";
		public static void main(String[] args) {
			new Thread(new WriteRunner2()).start();
			new Thread(new ReadRunner2()).start();
		}
	}
	class WriteRunner2 implements Runnable{
		public void run(){
			while(true){
				synchronized(LockDemo2.class){
					if("李雷".equals(LockDemo2.name)){
						LockDemo2.name = "韩梅梅";
						LockDemo2.gender = "女";
					}else{
						LockDemo2.name = "李雷";
						LockDemo2.gender = "男";
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
		public static String name="李雷";
		public static String gender="男";
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
					if("李雷".equals(LockDemo3.name)){
						LockDemo3.name = "韩梅梅";
						LockDemo3.gender = "女";
					}else{
						LockDemo3.name = "李雷";
						LockDemo3.gender = "男";
					}
				//释放锁的操作（代码中如果可能出现异常，要将锁的是否放到finally代码块中）
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

对比控制台输出，

重入锁底层使用原理：

3. 读写锁ReadWriteLock
	
	public class LockDemo4 {
		public static String name="李雷";
		public static String gender="男";
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
					if("李雷".equals(LockDemo4.name)){
						LockDemo4.name = "韩梅梅";
						LockDemo4.gender = "女";
					}else{
						LockDemo4.name = "李雷";
						LockDemo4.gender = "男";
					}
				//释放锁的操作（代码中如果可能出现异常，要将锁的是否放到finally代码块中）
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

### 10. 原子性

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


测试num的值为2000，没有问题。我们将两个线程中的1000改为100000，再多次测试num的值均小于200000。分析原因：

添加synchronized同步代码块或锁可以解决该问题，我们还有别的解决方案，原子性AtomicInteger
public static int num = 0;
改为：
public static AtomicInteger num = new AtomicInteger(0);

TestDemo7.num++;
改为（有两处）
TestDemo7.num.addAndGet(1);

