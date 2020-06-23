package fd.ng.core.utils;

import fd.ng.core.conf.AppinfoConf;
import fd.ng.core.utils.key.SnowflakeImpl;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UuidUtil {
	private static final Logger logger = LogManager.getLogger(UuidUtil.class.getName());
	private static final long STARTED_NANO_TIME = System.nanoTime();

	private static final SnowflakeImpl idgen;

	static {
		if(AppinfoConf.ProjectId==null ||
				AppinfoConf.ProjectId.trim().length()<1) {
			logger.warn("projectId is not exist in appinfo.conf, will be not use the system's built-in ID generator.");
			idgen = null;
		} else if("SingletonHRSProjectInWholeCustEnv".equals(AppinfoConf.ProjectId)) {
			logger.warn("Used singleton ID generator(did=31, mid=31)!");
			idgen = new SnowflakeImpl(
					SnowflakeImpl.MAX_DATACENTER_NUM, SnowflakeImpl.MAX_MACHINE_NUM);
		} else {
			// 这种情况说明该项目配置了自己的projectid，需要在项目里面初始化SnowflakeImpl实例。
			// 使用方式参考 guidUsedDefaultSnowflake() 方法上面给的示例代码
			idgen = null;
		}
	}

	public UuidUtil() { throw new AssertionError("No UuidUtil instances for you!"); }

	/**
	 * 获得全局唯一的ID。一个项目的多线程下依然可以放心使用。
	 *
	 * 该方法只有把projectid设置成'SingletonHRSProjectInWholeCustEnv'，才能使用，否则会报空指针异常！！！！！！！！
	 * 如果部署了多个独立进程的应用（或者多个Docker），那么就会主键冲突了，需要给每个独立部署的项目设置不一样名字的 projectid。
	 * 项目中，最好封装一个本项目使用工具类（例如：PrimayKeyGener），在工具类中调用这个方法。
	 * 构建自己项目使用的 SnowflakeImpl，参考如下代码：
	 * 			public class PrimayKeyGener {
	 * 				private static final Logger logger = LogManager.getLogger();
	 *
	 * 				private static final SnowflakeImpl idgen;
	 *
	 * 				static {
	 * 					String projectId = AppinfoConf.ProjectId;
	 * 					if (StringUtil.isEmpty(projectId)) throw new AppSystemException("ProjectId Can't be empty");
	 * 					try (DatabaseWrapper db = new DatabaseWrapper()) {
	 * 						Result rs = SqlOperator.queryResult(db, "SELECT * FROM keytable_snowflake WHERE project_id = ?",
	 * 								projectId);
	 * 						if (rs.getRowCount() != 1) {
	 * 							throw new AppSystemException("DB data select exception: keytable_snowflake select fail!");
	 *                                                }
	 * 						Integer datacenterId = rs.getInteger(0, "datacenter_id");
	 * 						Integer machineId = rs.getInteger(0, "machine_id");
	 * 						idgen = new SnowflakeImpl(datacenterId, machineId);* 					} catch (Exception e) {
	 * 						throw new AppSystemException("DB data select exception: keytable_snowflake select fail!");
	 *                                    }
	 * 				}
	 *
	 * 				public static long getNextId() {
	 * 					return idge                tId();
	 *            }
	 *
	 * 			}
	 *
	 * @return 全局唯一的ID
	 */
	public static long guidUsedDefaultSnowflake() {
		return idgen.nextId();
	}

	/**
	 * 得到没有中间横线的UUID串，性能比使用replace快5倍
	 * @return
	 */
	public static String uuid() {
		return uuid(UUID.randomUUID());
	}
	public static String uuid(UUID uuid) {
		long mostSigBits  = uuid.getMostSignificantBits();
		long leastSigBits = uuid.getLeastSignificantBits();
		return (digits(mostSigBits >> 32, 8) +
				digits(mostSigBits >> 16, 4) +
				digits(mostSigBits, 4) +
				digits(leastSigBits >> 48, 4) +
				digits(leastSigBits, 12));
	}

	/**
	 * 用当前线程 id 和 当前毫秒时间作为UUID。对于WEB类应用，基本满足要求了。
	 * 但是，不能在循环调用！
	 * @return
	 */
	public static String threadId_Millis() { return Thread.currentThread().getId() + "-" + System.currentTimeMillis(); }

	public static long nanoTime() {
		return System.nanoTime();
	}
	public static long elapsedNanoTime() {
//		long cur = System.nanoTime();
//		System.out.printf("%l, %l", STARTED_NANO_TIME, cur);
//		return cur - STARTED_NANO_TIME;
		return System.nanoTime() - STARTED_NANO_TIME;
	}
	public static long timeMillis() { return System.currentTimeMillis(); }

	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}
}
