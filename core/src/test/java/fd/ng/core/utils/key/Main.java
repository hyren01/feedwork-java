package fd.ng.core.utils.key;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class Main {

	private static SnowflakeImpl idgen = new SnowflakeImpl(0, 0);
	private static Map<Long, Integer> idBox = new ConcurrentHashMap<>();

	public static void main(String[] args) {
//		runOneThread();
		runMultiThread();
	}

	private static void runMultiThread() {
		int taskNums = 12;
		ExecutorService threadPool = Executors.newFixedThreadPool(taskNums);

		for(int i=1; i <=taskNums; i++) {
			final int task = i;
			Future<Integer> future = threadPool.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					try {
						int nums = 1_000_000;
						long start = System.currentTimeMillis();
						for (int i = 0; i < nums; i++) {
							long id = idgen.nextId();
							if(idBox.containsKey(id))
								throw new RuntimeException("重复的id：" + id + " curTask=" + task + ", boxTask=" + idBox.get(id));
							idBox.put(id, task);
						}
						System.out.println("thread [" + task + "] nums=" + nums + " time : " + (System.currentTimeMillis() - start) + "ms");
						return 1;
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
				}
			});
		}
	}

	private static void runOneThread() {
        SnowflakeImpl idgen = new SnowflakeImpl(0, 0);
        int nums = 1_000_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < nums; i++) {
            long id = idgen.nextId();
        }
        System.out.println("nums=" + nums + " time : " + (System.currentTimeMillis() - start) + "ms");

        // 使用Set检查是否有重复的id
//		nums = 100;
        Set<Long> ids = new HashSet<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < nums; i++) {
            long id = idgen.nextId();
            if(nums<1000)
                System.out.println(id + " : " + Long.toBinaryString(id));
            if(ids.contains(id)) throw new RuntimeException("current id : " + id);
            ids.add(id);
        }
        System.out.println("nums=" + nums + " time : " + (System.currentTimeMillis() - start) + "ms");
    }
}
