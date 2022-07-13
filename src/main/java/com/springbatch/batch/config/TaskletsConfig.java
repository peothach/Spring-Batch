package com.springbatch.batch.config;

import com.springbatch.batch.listener.JobTaskletCompletionNotificationListener;
import com.springbatch.batch.processor.LinesTaskletProcessor;
import com.springbatch.batch.reader.LinesTaskletReader;
import com.springbatch.batch.writer.LinesTaskletWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class TaskletsConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    protected Step readLines() {
        return steps
                .get("readLines")
                .tasklet(linesReader())
                .build();
    }

    @Bean
    protected LinesTaskletReader linesReader() {
        return new LinesTaskletReader();
    }

    @Bean
    protected Step processLines() {
        return steps
                .get("processLines")
                .tasklet(linesProcessor())
                .build();
    }

    @Bean
    protected LinesTaskletProcessor linesProcessor() {
        return new LinesTaskletProcessor();
    }

    @Bean
    protected Step writeLines() {
        return steps
                .get("writeLines")
                .tasklet(linesWriter())
                .build();
    }

    @Bean
    protected LinesTaskletWriter linesWriter() {
        return new LinesTaskletWriter();
    }

    @Bean
    public Job taskletJob() {
        return jobs
                .get("taskletJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobTaskletCompletionNotificationListener())
                .start(readLines())
                .next(processLines())
                .next(writeLines())
                .build();
    }

    @Bean(name = "asyncJobLauncher")
    protected JobLauncher asyncJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }
}
