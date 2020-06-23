package fd.ng.core.utils.key;

/**
 * 使用方式：
 * 数据库中建立guidgen_seed_table，包括：分类、数据中心序号、机器序号
 * 每个应用或进程，在初始启动时，按照自己归属的分类到该表中获取分配给自己使用的数据中心序号、机器序号
 * 获取序号时，务必使用数据库的for update，来保证不同机器、不同进程、不同线程中得到互相不冲突的序号
 * 编写工具类，static final方式创建SnowflakeImpl对象，并提供静态方法调用nextId，供应用（进程）各个程序使用
 * “分类”字段的设置方式例如：每个web应用设置一个分类、所有采集Agent设置一个分类、所以作业Agent设置一个分类 ......
 */
public class SnowflakeImpl {

	/**
	 * 起始的时间戳
	 */
	private final static long START_STMP = 1420041600000L; //1480166465631L;

	/**
	 * 每一部分占用的位数
	 */
	private final static long SEQUENCE_BIT = 12; //序列号占用的位数
	private final static long MACHINE_BIT = 5;   //机器标识占用的位数
	private final static long DATACENTER_BIT = 5;//数据中心占用的位数

	/**
	 * 每一部分的最大值
	 */
	public final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
	public final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
	public final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	/**
	 * 每一部分向左的位移
	 */
	private final static long MACHINE_LEFT = SEQUENCE_BIT;
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	private long datacenterId;  //数据中心
	private long machineId;     //机器标识
	private long sequence = 0L; //序列号
	private long lastTimestamp = -1L;//上一次时间戳

	public SnowflakeImpl(long datacenterId, long machineId) {
		if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
			throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
		}
		if (machineId > MAX_MACHINE_NUM || machineId < 0) {
			throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
		}
		this.datacenterId = datacenterId;
		this.machineId = machineId;
	}

	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public synchronized long nextId() {
		long currTimestamp = currentTimeMillis();
		//如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (currTimestamp < lastTimestamp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}

		//如果是同一时间生成的，则进行毫秒内序列
		if (currTimestamp == lastTimestamp) {
			//相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			//毫秒内序列溢出(同一毫秒的序列数已经达到最大)
			if (sequence == 0L) {
				//阻塞到下一个毫秒,获得新的时间戳
				currTimestamp = getNextMillis();
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			// 不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastTimestamp = currTimestamp;

		return (currTimestamp - START_STMP) << TIMESTMP_LEFT //时间戳部分
				| datacenterId << DATACENTER_LEFT       //数据中心部分
				| machineId << MACHINE_LEFT             //机器标识部分
				| sequence;                             //序列号部分
	}

	private long getNextMillis() {
		long timestamp = currentTimeMillis();
		while (timestamp <= lastTimestamp) {
			timestamp = currentTimeMillis();
		}
		return timestamp;
	}

	private long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}