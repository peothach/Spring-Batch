package com.springbatch.batch.writer;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


public class LinesChunkWriter implements ItemWriter<String>, StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Line Writer initialized...");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Line Writer ended...");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println("Write data from processor " + list);
        System.out.println("Done!");
    }
}
