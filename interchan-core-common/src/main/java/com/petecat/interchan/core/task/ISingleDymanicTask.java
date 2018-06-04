package com.petecat.interchan.core.task;

/**
 * @author mhuang
 * @Title: ISingleDymanicTask
 * @Package com.petecat.interchan.core.task
 * @Description: 接口类
 * @date 2018/6/4 10:02
 */
public interface ISingleDymanicTask {

    Boolean startJob(String jobName,Runnable run,String conn);

    Boolean stopJob(String jobName);

    Boolean updateCronLazy(String jobName,String cron);

    Boolean updateJob(String jobName,String cron);

    Boolean updateJob(String jobName,Runnable run);

    Boolean updateJob(String jobName,Runnable run,String conn);
}
