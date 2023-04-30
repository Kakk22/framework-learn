package com.cyf;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 时间轮实现
 *
 * @author 陈一锋
 * @date 2023/2/9 10:01 下午
 */
public class Scheduled {

    private Map<Integer, List<Long>> times = new ConcurrentHashMap<>(64);

    private List<Job> jobs = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Scheduled scheduled = new Scheduled();

        Date now = DateUtil.offsetSecond(new Date(), 10);
        Date now1 = DateUtil.offsetSecond(new Date(), 20);
        System.out.println(now);
        //正常从数据库获取数据
        scheduled.jobs.add(Job.builder().id(1L).name("任务1").cron("0/5 * * * * ? ").nextScheduledTime(now).build());
        scheduled.jobs.add(Job.builder().id(2L).name("任务2").cron("0/10 * * * * ? ").nextScheduledTime(now1).build());


        Thread taskThread = new Thread(() -> {
            // TODO: 2023/2/11 这里要对其一下时间
            while (true) {
                //获取接下来30秒的任务 列表 计算任务
                List<Job> jobs = scheduled.jobs;
                Map<Integer, List<Long>> times = scheduled.times;
                long currentTimeMillis = System.currentTimeMillis();
                for (Job job : jobs) {
                    long diffTime = (job.getNextScheduledTime().getTime() - currentTimeMillis) / 1000;
                    if (diffTime < 30) {
                        //计算执行时间刻度所在下标
                        int index = (int) (job.getNextScheduledTime().getTime() / 1000) % 60;
                        System.out.println("添加任务成功,任务执行时间:" + job.getNextScheduledTime());
                        if (times.containsKey(index)) {
                            times.get(index).add(job.getId());
                        } else {
                            List<Long> list = new ArrayList<>();
                            list.add(job.getId());
                            times.put(index, list);
                        }
                        // TODO: 2023/2/9 根据cron设置新的时间
                    }
                }

                System.out.println("任务取数线程完成,将数据放入时间轮:" + JSON.toJSONString(times));
                try {
                    TimeUnit.MILLISECONDS.sleep(20000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread rangeThread = new Thread(() -> {
            // TODO: 2023/2/11 这里要对其一下时间
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {

                Map<Integer, List<Long>> times = scheduled.times;

                int nowSecond = Calendar.getInstance().get(Calendar.SECOND);

                int idx = nowSecond % 60;
                List<Long> ids = times.remove(idx);
                System.out.println("当前时间:" + DateUtil.now());
                if (ids != null && !ids.isEmpty()) {
                    System.out.println("获取到时间轮数据,开始执行:" + ids);
                }
            }
        });

        System.out.println(59 % 60);
        System.out.println((59 + 60) % 60);

        taskThread.start();
        rangeThread.start();

        TimeUnit.SECONDS.sleep(60 * 20);
    }


    @Builder
    @Data
    static class Job {
        private Long id;

        private String name;
        /**
         * cron表达式
         */
        private String cron;
        /**
         * 下次调度时间
         */
        private Date nextScheduledTime;
    }
}
