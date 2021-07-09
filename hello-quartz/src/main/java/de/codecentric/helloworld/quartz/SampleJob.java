package de.codecentric.helloworld.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleJob implements Job {

    public void execute(JobExecutionContext context) {
        log.warn("executing SampleJob {}", context.toString());
    }

    protected static JobDetail createJobDetail(String jobName, String group) {
        return JobBuilder.newJob().ofType(SampleJob.class)
                .storeDurably()
                .withIdentity(jobName, group)
                .withDescription("Invoke Sample Job " + jobName + " in group " + group)
                .build();
    }
}