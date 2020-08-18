package com.jt.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

public class TestShards {//该类表示测试redis的分片机制

    /**
     * 在Linux中有三台redis需要通过车呢工序进行链接
     * 实现数据的存储
     */
    @Test
    public void test01(){
        List< JedisShardInfo > shardInfos=new ArrayList<>();
        shardInfos.add(new JedisShardInfo("192.168.126.129",6379));
        shardInfos.add(new JedisShardInfo("192.168.126.129",6380));
        shardInfos.add(new JedisShardInfo("192.168.126.129",6381));
        //分片的API
        ShardedJedis shardedJedis=new ShardedJedis(shardInfos);
        shardedJedis.set("王者荣耀","你好我是老菜鸡");
        System.out.println(shardedJedis.get("王者荣耀"));
    }
}
