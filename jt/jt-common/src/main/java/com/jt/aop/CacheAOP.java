package com.jt.aop;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;

@Component  //1.我是一个javaBean
@Aspect     //2.我是一个切面
public class CacheAOP {

    //引入redis缓存配置
    @Autowired
    //private Jedis jedis;
    //private ShardedJedis jedis;
    private JedisCluster jedis;


    /**
     * AOP缓存实现的业务策略
     * 1.切入点表达式应该拦截  @CacheFind注解
     * 2.通知方法: 环绕通知
     * 注意事项:  如果使用环绕通知,则必须在第一个参数的位置添加 ProceedingJoinPoint
     *
     * 动态获取注解参数的步骤:
     *  1.@annotation(cacheFind)   切入点表达式要求拦截一个类型为cacheFind注解.
     *  2.并且利用连接点为参数中的cacheFind赋值.
     * */

    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind){
        try {
            Object result = null;
            //1.如何动态获取注解中的数据
            String prekey = cacheFind.key();
            //2.动态获取方法中的参数  将数组转化为字符串
            String args = Arrays.toString(joinPoint.getArgs());
            String key = prekey + "::" + args;
            //3.检验redis中是否有数据
            if(jedis.exists(key)){
                //有缓存  从redis缓存中获取json 之后还原对象返回
                String json = jedis.get(key);
                //target代表这目标方法的返回值类型......
                //动态获取目标方法的返回值类型??   向上造型 不用强转   向下造型 需要强转
                MethodSignature methodSignature =
                        (MethodSignature) joinPoint.getSignature();
               Class returnClass = methodSignature.getReturnType();
                //将json数据转化为对象
                result = ObjectMapperUtil.toObject(json, returnClass);
                System.out.println("AOP实现缓存查询!!!!");

            }else{
                //第一次查询数据库.
                result = joinPoint.proceed();    //执行目标方法.
                System.out.println("AOP执行数据库操作");
                //2.将数据保存到redis中
                String json = ObjectMapperUtil.toJSON(result);
                if(cacheFind.seconds()>0) //表示需要设定超时时间
                    jedis.setex(key, cacheFind.seconds(), json);
                else
                    //不需要超时
                    jedis.set(key, json);
            }
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);  //将检查异常,转化为运行时异常
        }
    }



  /*  //1.定义切入点表达式
    @Pointcut("bean(itemCatServiceImpl)") //只拦截xxx类中的方法
    public void pointCut(){

    }*/

    /**
     * 2.定义通知方法
     * 需求:
     *  1.想获取目标方法名称
     *  2.获取目标方法对象
     *  3.获取用户传递的参数
    */

   /* @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("我是前置通知");
        //1.获取类名称
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        //2.获取对象
        Object target = joinPoint.getTarget();
        //3.获取参数
        Object[] objs = joinPoint.getArgs();
        System.out.println("类名名称:"+className);
        System.out.println("方法名称:"+methodName);
        System.out.println("对象名称:"+target);
        System.out.println("方法参数:"+objs);
    }*/
}

