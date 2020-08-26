package com.jt.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**当用户订单提交30分钟后,如果还没有支付.则交易关闭
	 * 现在时间 - 订单创建时间 > 30分钟  则超时
	 * new date - 30 分钟 > 订单创建时间
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//1.计算超时时间
		Calendar calendar = Calendar.getInstance(); //获取当前时间
		calendar.add(Calendar.MINUTE, -30);
		Date timeOut = calendar.getTime();			//获取时间
		//2.实现数据库更新
		orderMapper.updateStatus(timeOut);

		System.out.println("定时任务执行成!!!!!!"); 	//1分钟执行一次
	}
}
