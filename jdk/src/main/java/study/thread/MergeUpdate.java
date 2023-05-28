package study.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 陈一锋
 * @date 2023/5/24 10:11 PM
 */
public class MergeUpdate {

    private Queue<Request> queue = new LinkedBlockingQueue<>(1000);

    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(10);

    public volatile boolean start = true;

    public MergeUpdate() {
        scheduledExecutorService.submit(() -> {
            while (start){
                System.out.println("开始处理请求" + Thread.currentThread().getName());
                int size = queue.size();
                if (size == 0) {
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                List<Request> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(queue.poll());
                }
                System.out.println(Thread.currentThread().getName() + "当前处理数量:" + list.size() + ",队列剩余数量:" + queue.size());
                Map<Long, List<Request>> resultMap = list.stream().collect(Collectors.groupingBy(Request::getUserId));
                Map<Long, Optional<BigDecimal>> map = list.stream().collect(Collectors.groupingBy(Request::getUserId, Collectors.mapping(Request::getAmount, Collectors.reducing(BigDecimal::add))));
                for (Map.Entry<Long, Optional<BigDecimal>> entry : map.entrySet()) {
                    threadPoolExecutor.submit(() -> {
                        Long userId = entry.getKey();
                        Optional<BigDecimal> amount = entry.getValue();
                        System.out.println("处理用户:" + userId + ",金额:amount" + amount.get() + "耗时:50ms");
                        try {
                            //模拟更新数据库
                            Thread.sleep(50L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Result result = new Result(true, "更新成功");
                        resultMap.get(userId).forEach(r -> r.getResult().complete(result));
                    });
                }
            }
        });
    }

    public Result changeAmount(Request request) {
        CompletableFuture<Result> future = new CompletableFuture<>();
        request.setResult(future);
        boolean success = queue.offer(request);
        if (!success) {
            return new Result(false, "to many request");
        }
        try {
            return future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return new Result(false, "time out");
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        MergeUpdate mergeUpdate = new MergeUpdate();
        CountDownLatch cd = new CountDownLatch(300);
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 300; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    cd.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Request request = new Request();
                request.setAmount(new BigDecimal(finalI));
                request.setUserId((long) (finalI % 8));
                Result result = mergeUpdate.changeAmount(request);
                System.out.println(Thread.currentThread().getName() + "获取结果:" + result);
                if (result.getSuccess()) {
                    atomicInteger.incrementAndGet();
                }
            }).start();
            cd.countDown();
        }
        Thread.sleep(1000L);
        System.out.println("成功数量:" + atomicInteger.get());
        mergeUpdate.start = false;
        mergeUpdate.scheduledExecutorService.shutdown();
        mergeUpdate.threadPoolExecutor.shutdown();
    }
}

@Data
class Request {
    private Long userId;
    private BigDecimal amount;
    private CompletableFuture<Result> result;
}

@Data
@AllArgsConstructor
class Result {
    private Boolean success;
    private String msg;
}
