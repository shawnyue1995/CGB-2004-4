package com.jt.conf;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jt.quartz.OrderQuartz;

@Configuration
public class OrderQuartzConfig {
	
	//定义任务详情	   JobDetail封装job任务.
	@Bean
	public JobDetail orderjobDetail() {
		//指定job的名称和持久化保存任务
		return JobBuilder
				.newJob(OrderQuartz.class)		//1.任务的类型
				.withIdentity("orderQuartz")	//2.任务名称
				.storeDurably()
				.build();
	}
	//定义触发器
	@Bean
	public Trigger orderTrigger() {
		/*SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(1)	//定义时间周期
				.repeatForever();*/
		CronScheduleBuilder scheduleBuilder 
			= CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
		return TriggerBuilder
				.newTrigger()
				.forJob(orderjobDetail())
				.withIdentity("orderQuartz")
				.withSchedule(scheduleBuilder).build();
	}
}
