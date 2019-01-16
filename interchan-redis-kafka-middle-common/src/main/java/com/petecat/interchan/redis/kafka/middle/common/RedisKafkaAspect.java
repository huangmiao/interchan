package com.petecat.interchan.redis.kafka.middle.common;


import com.alibaba.fastjson.JSON;
import com.mhuang.kafka.common.bean.KafkaMsg;
import com.mhuang.kafka.common.exception.JKafkaException;
import com.petecat.interchan.redis.commands.RedisExtCommands;
import com.petecat.interchan.redis.kafka.middle.common.annaotion.RedisKafka;
import com.petecat.interchan.redis.lock.DistributedLockHandler;
import com.petecat.interchan.redis.lock.Lock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @ClassName: LogAspect
 * @Description:aop 拦截Kafka进行处理
 * @author: mhuang
 * @date: 2017年8月15日 下午12:31:03
 */
@Component
@Aspect
@Order(100)
public class RedisKafkaAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private final String CONSUMER = "-consumer";

    private final String LOCK = "-lock-";

    @Autowired
    private RedisExtCommands redisExtCommands;

    @Autowired
    private DistributedLockHandler distributedLockHandler;

    private static final Integer REDIS_KAFKA_DB_INDEX = 15;

    @Pointcut("@annotation(com.petecat.interchan.redis.kafka.middle.common.annaotion.RedisKafka)")
    private void kafkaMsgProcess() {
    }

    public static Boolean getMethodByRemark(MethodSignature methodSignature) throws Exception {
        Method method0 = methodSignature.getMethod();
        RedisKafka rediksKafka = method0.getAnnotation(RedisKafka.class);
        return rediksKafka.NotRepeat();
    }

    @Before("kafkaMsgProcess()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        logger.debug("---正在拦截---kafka---");
        Object[] obj = joinPoint.getArgs();
        KafkaMsg kafkaMsg = (KafkaMsg) obj[0];
        Lock lock = new Lock();
        try {
            Boolean notRepeat = getMethodByRemark((MethodSignature) joinPoint.getSignature());
            lock.setName(kafkaMsg.getTopic() + LOCK + kafkaMsg.getOffset());
            lock.setValue(kafkaMsg.getMsg().toString());
            if (distributedLockHandler.tryLock(lock, notRepeat)) {
                String value = redisExtCommands.hget(REDIS_KAFKA_DB_INDEX, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset());
                if (StringUtils.isEmpty(value)) {
                    redisExtCommands.hset(REDIS_KAFKA_DB_INDEX, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset(), kafkaMsg.getMsg());
                } else {
                    logger.error("数据为：{}", JSON.toJSONString(kafkaMsg));
                    throw new JKafkaException("kafka这条消息已经处理过了！");
                }
            }
        } catch (Exception e) {
            if (!(e instanceof JKafkaException)) {
                redisExtCommands.hdel(REDIS_KAFKA_DB_INDEX, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset());
            }
        } finally {
            distributedLockHandler.releaseLock(lock);
        }
    }
}
