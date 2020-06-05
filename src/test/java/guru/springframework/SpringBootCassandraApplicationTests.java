package guru.springframework;

import guru.springframework.domain.UserProfile2;
import guru.springframework.services.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCassandraApplicationTests {
	@Autowired
	private UserProfileService service;

	@Test
	public void test() throws InterruptedException, IOException {
		// init the data
		List<String> userIds = getFromFile();
		// read
		int threadNum = Integer.valueOf(System.getProperty("threadNum", "8"));
		int sleep = Integer.valueOf(System.getProperty("sleep", "1"));
		int testTime = Integer.valueOf(System.getProperty("testTime", "1000"));

		CountDownLatch countDownLatch = new CountDownLatch(threadNum);
		AtomicInteger i = new AtomicInteger(0);
		AtomicInteger totalRequests = new AtomicInteger(0);
		AtomicInteger totalSuccess = new AtomicInteger(0);
		AtomicInteger totalFail = new AtomicInteger(0);
		AtomicInteger totalError = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for(int ii = 0; ii < threadNum; ii++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					Random randome = new Random();
					int myI = i.incrementAndGet();
					int fail = 0;
					int success = 0;
					int error = 0;
					int request = 0;
					long startTime  = System.currentTimeMillis();
					while (true) {
						try {
							request += 1;
							String userId = userIds.get(randome.nextInt(userIds.size()));
							UserProfile2 ret = service.getById(userId);
							if (ret == null) {
								fail += 1;
							} else {
								if(userId.equals(ret.getUserId())) {
									System.out.println("zhouxiang====>"+ret.getProfile());
									success += 1;
								}
							}

							// sleep
							if(sleep > 0)
								Thread.sleep(sleep);
							// stop, record the time
							if(System.currentTimeMillis() - startTime > testTime) {
								totalRequests.addAndGet(request);
								totalSuccess.addAndGet(success);
								totalFail.addAndGet(fail);
								System.out.println(String.format("Thread-%d request:%d success:%d fail:%d", myI, request, success, fail));
								countDownLatch.countDown();
								break;
							}
						} catch (InterruptedException e) {
							error += 1;
							e.printStackTrace();
						}
					}
				}
			});
		}
		countDownLatch.await();
		System.out.println(String.format("%d-%d-%d-%d", totalRequests.get(), totalSuccess.get(), totalFail.get(), totalError.get()));
	}

	private List<String> getFromFile() throws IOException {
		//DESC: get from the file
		List<String> res = new ArrayList<>(32000);
		final ClassPathResource classPathResource = new ClassPathResource("userid");
		final File file = classPathResource.getFile();

		final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			res.add(line);
		}
		return res;
	}

}
