package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration  //标识我是配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    @Value("${redis.nodes}")
    private String nodes;   //node,node,node

    /**
     * spring整合redis集群
     */
    @Bean
    public JedisCluster jedisCluster(){
        String[] strNodes=nodes.split(",");
        Set< HostAndPort > set=new HashSet<HostAndPort>();
        for (String node:strNodes) {
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            set.add(new HostAndPort(host,port));
        }
        return new JedisCluster(set);
    }

    /**
     * spring整合redis分片机制
     */
    @Bean
    public ShardedJedis shardedJedis(){
        //1.获取每个节点的信息
        String[] strNodes = nodes.split(",");
        List<JedisShardInfo> shards = new ArrayList<>();
        //2.遍历所有node.为list集合赋值
        for(String node :strNodes){ //node=ip:port
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            JedisShardInfo info = new JedisShardInfo(host,port);
            shards.add(info);
        }

        ShardedJedis shardedJedis = new ShardedJedis(shards);
        return shardedJedis;
    }
  /*  @Value("${redis.host}")
    private String  host;
    @Value("${redis.port}")
    private Integer port;

    //SpringBoot管理  Spring框架的优化的API
    @Bean
    public Jedis jedis(){

        return new Jedis(host,port);
    }*/
}