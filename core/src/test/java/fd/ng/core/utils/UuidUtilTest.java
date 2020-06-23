package fd.ng.core.utils;

import fd.ng.core.utils.key.SnowflakeImpl;
import fd.ng.test.junit.TestCaseLog;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class UuidUtilTest {

	@Test
	public void guid() {
		// 预热
		long guid_perf = UuidUtil.guidUsedDefaultSnowflake();
		guid_perf = UuidUtil.guidUsedDefaultSnowflake();

		// test perf
		long start = System.currentTimeMillis();
		for(int i=0; i<10_000; i++) {
			long cur_guid = UuidUtil.guidUsedDefaultSnowflake();
			if(cur_guid<guid_perf) throw new RuntimeException(cur_guid + " must be less " + guid_perf);
		}
		long end = System.currentTimeMillis();
		// 5毫秒内生成1万个ID
		assertThat((end-start), lessThan(5L));

		// test correct
		for(int i=0; i<10_000; i++) {
			long guid = UuidUtil.guidUsedDefaultSnowflake();

			String guidStr = Long.toBinaryString(guid);
			assertThat(guidStr.length(), is(60));

			String didStr = guidStr.substring(38, 43);
			assertThat(didStr, is(Long.toBinaryString(SnowflakeImpl.MAX_DATACENTER_NUM)));

			String midStr = guidStr.substring(43, 48);
			assertThat(midStr, is(Long.toBinaryString(SnowflakeImpl.MAX_MACHINE_NUM)));
		}
	}

	@Test
	public void threadId_Millis() {
		String uuid = UuidUtil.threadId_Millis();
		assertThat(uuid, startsWith(Thread.currentThread().getId()+"-"));
	}

	@Test
	public void elapsedNanoTime() {
		long uuid = UuidUtil.elapsedNanoTime();
		assertThat(uuid, Matchers.greaterThan(0L));
		TestCaseLog.println("UuidUtil.elapsedNanoTime()=" + uuid);
	}

	@Ignore("观察几个内置方法的异同")
	@Test
	public void watchUUID() {
		System.out.printf("NEW  UUID=%s %n", UuidUtil.uuid());
		System.out.printf("Orgn UUID=%s %n", UUID.randomUUID().toString());

		StopWatch watch = new StopWatch();
		watch.start("ORGN");
		for(int i=0; i<100000; i++) {
			UUID.randomUUID().toString().replace("-", StringUtil.EMPTY);
		}
		watch.stopShow();

		watch.start("NEW ");
		for(int i=0; i<100000; i++) {
			UuidUtil.uuid();
		}
		watch.stopShow();
	}

	@Test
	public void uuid() {
		UUID uuid = UUID.randomUUID();
		for(int i=0; i<230000; i++) {
			String orgnUUID = uuid.toString().replace("-", StringUtil.EMPTY);
			String newUUID  = UuidUtil.uuid(uuid);
			assertThat(orgnUUID, is(newUUID));
		}
	}
}