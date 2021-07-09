package de.codecentric.helloworld.quartz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@EnableAutoConfiguration
public class SpringQrtzSchedulerConfig {

    private static final String GROUP_1 = "group1";
    private static final String GROUP_2 = "group2";

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        ObjectMapper mapper  = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule).build();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    @Bean
    public JobDetail jobDetailSampleJob1() {
        return SampleJob.createJobDetail("SampleJob1", GROUP_1);
    }

    @Bean
    public JobDetail jobDetailSampleJob2() {
        return SampleJob.createJobDetail("SampleJob2", GROUP_2);
    }

    @Bean
    public SimpleTriggerFactoryBean triggerSampleJob1(@Qualifier("jobDetailSampleJob1") JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setGroup(GROUP_1);
        trigger.setJobDetail(job);
        trigger.setDescription("Triggers Job 1");
        trigger.setRepeatInterval(10000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }

    @Bean
    public SimpleTriggerFactoryBean triggerSampleJob2(@Qualifier("jobDetailSampleJob2") JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setGroup(GROUP_2);
        trigger.setJobDetail(job);
        trigger.setDescription("Triggers Job 2");
        trigger.setRepeatInterval(20000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }

    @Bean
    @Primary
    public SchedulerFactoryBean schedulerSampleJob() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        return schedulerFactory;
    }

    @Bean
    public Scheduler scheduler1(SchedulerFactoryBean factory, @Qualifier("triggerSampleJob1") Trigger trigger, @Qualifier("jobDetailSampleJob1") JobDetail job)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public Scheduler scheduler2(SchedulerFactoryBean factory, @Qualifier("triggerSampleJob2") Trigger trigger, @Qualifier("jobDetailSampleJob2") JobDetail job)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

}