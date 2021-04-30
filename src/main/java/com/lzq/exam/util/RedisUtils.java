package com.lzq.exam.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作工具类
 *
 * @author beastars
 */
@Component
@Slf4j
public class RedisUtils {
  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * PostConstruct 该注解被用来修饰一个非静态的void（）方法。
   * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
   */
  @PostConstruct
  private void init() {
    // 设置 redisTemplate 的序列化方法，否则 redis 中存储的键值会是 \xAC\xED\x00\x05t\x00\x05**
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
  }

  /**
   * 获取 redis 中 string 格式对应 key 的值
   */
  public Object get(Object key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * 设置 string 格式的键值
   *
   * @return 是否添加成功
   */
  public boolean set(Object key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      log.error("[redis] redis set error", e);
      return false;
    }
  }

  /**
   * 设置 string 格式的键值，并设置过期时间
   *
   * @param time 过期时间 秒
   * @return 是否添加成功
   */
  public boolean set(Object key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        this.set(key, value);
      }
      return true;
    } catch (Exception e) {
      log.error("[redis] redis set error", e);
      return false;
    }
  }

  /**
   * 判断 key 是否存在
   */
  public boolean hasKey(Object key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 删除指定键
   *
   * @param key 一个或多个键
   */
  public void delete(Object... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(CollectionUtils.arrayToList(key));
      }
    }
  }
}
