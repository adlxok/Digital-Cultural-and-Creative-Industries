package org.example.digitalculturalportal.config;

import org.example.digitalculturalportal.quartz.postScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Quartz配置
 * @author jisamin
 * @since 2024/7/26
 */
@Configuration
public class QuartzConfig {
    //配置JobDetail 刷新分数任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(postScoreRefreshJob.class);
        factoryBean.setName("postScoreRefreshJob"); //任务名称
        factoryBean.setGroup("communityJobGroup"); //任务组名称
        factoryBean.setDurability(true); //声明这个任务是否持久化
        factoryBean.setRequestsRecovery(true); //声明这个任务是否可恢复
        return factoryBean;
    }
    //配置Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean) 触发器
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
        SimpleTriggerFactoryBean factoryBean=new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail); //绑定JobDetail
        factoryBean.setName("postScoreRefreshTrigger"); //名称
        factoryBean.setGroup("communityJobGroup"); //任务组名
        factoryBean.setRepeatInterval(1000*60*5); //重复执行时间间隔5分钟
        factoryBean.setJobDataMap(new JobDataMap()); //，，默认使用map存数据
        return factoryBean;

    }


}
