package fd.ng.test.junit.parallel;

import fd.ng.test.junit.ExtendBasalRunner;
import fd.ng.test.junit.FdBaseTestCase;
import fd.ng.test.junit.TestCaseLog;
import fd.ng.test.junit.rules.RepeatRule;
import fd.ng.test.junit.rules.RetryRule;
import fd.ng.test.junit.rules.anno.Repeat;
import fd.ng.test.junit.rules.anno.Retry;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Random;

//@Ignore("仅用于 ParallelRunnerTest的手工运行")
@RunWith(ExtendBasalRunner.class)
public class TestCase1 extends FdBaseTestCase {
	private static final String UAT_ID = "UAT 1";

//	@Rule public RetryRule _RetryRule = new RetryRule();
//	@Rule public RepeatRule _RepeatRule = new RepeatRule();

	@BeforeClass
	public static void start() {
		TestCaseLog.println("===== start [" + UAT_ID + "] =====\n");
	}
	@Before
	public void before() {
		TestCaseLog.println("before in [" + UAT_ID + "]");
	}
	@After
	public void after() {
		TestCaseLog.println("after in [" + UAT_ID + "]\n");
	}
	@AfterClass
	public static void end() {
		TestCaseLog.println("===== end [" + UAT_ID + "] =====\n");
	}

	@Test
	@Repeat(value = 5, showFlag = true)
	public void testRepeat() {
		TestCaseLog.println("testRepeat in [" + UAT_ID + "] start");
		for(int i=0; i<10; i++) {
			try {
				TestCaseLog.println("testRepeat in [" + UAT_ID + "] running ... ... loop " + i);
				Random rand = new Random(System.nanoTime() + i);
				Thread.sleep(rand.nextInt(155) + 5);
			} catch (InterruptedException e) {
			}
		}
		TestCaseLog.println("testRepeat in [" + UAT_ID + "] end");
	}

	@Test
	public void test2() {
		TestCaseLog.println("test2 in [" + UAT_ID + "] start");
		for(int i=0; i<10; i++) {
			try {
				TestCaseLog.println("test2 in [" + UAT_ID + "] running ... ... loop " + i);
				Random rand = new Random(System.nanoTime() + i);
				Thread.sleep(rand.nextInt(10) + 3);
			} catch (InterruptedException e) {
			}
		}
		TestCaseLog.println("test2 in [" + UAT_ID + "] end");
	}

	@Test
	public void test3() {
		TestCaseLog.println("test3 in [" + UAT_ID + "] start");
		for(int i=0; i<10; i++) {
			try {
				TestCaseLog.println("test3 in [" + UAT_ID + "] running ... ... loop " + i);
				Random rand = new Random(System.nanoTime() + i);
				Thread.sleep(rand.nextInt(55) + 2);
			} catch (InterruptedException e) {
			}
		}
		TestCaseLog.println("test3 in [" + UAT_ID + "] end");
	}

	@Test
	@Retry(showFlag = true)
	public void testRetry() {
		try {
			Random rand = new Random(System.nanoTime() + 32987);
			Thread.sleep(rand.nextInt(15) + 5);
		} catch (InterruptedException e) {}
		TestCaseLog.println("testRetry in [" + UAT_ID + "]");
	}
}
