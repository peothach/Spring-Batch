package com.springbatch.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class LinesTaskletWriter implements Tasklet, StepExecutionListener {
    private final Logger logger = LoggerFactory
            .getLogger(LinesTaskletWriter.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.info("Invoke execute method");
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Lines Writer initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Lines Writer ended.");
        return ExitStatus.COMPLETED;
    }
}
