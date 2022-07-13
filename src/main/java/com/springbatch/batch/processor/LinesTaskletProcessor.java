package com.springbatch.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class LinesTaskletProcessor implements Tasklet, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(
            LinesTaskletProcessor.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Thread.sleep(10000);
        logger.info("Invoke execute method");
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Lines Processor initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }
}
