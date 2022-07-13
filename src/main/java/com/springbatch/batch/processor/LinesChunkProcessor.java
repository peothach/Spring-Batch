package com.springbatch.batch.processor;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

public class LinesChunkProcessor implements ItemProcessor<String, String>, StepExecutionListener {

    @Override
    public String process(String s) throws Exception {
        System.out.println("Get data from Reader " + s);
        return "Process data and return";
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Line Processor initialized...");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Line Processor ended...");
        return ExitStatus.COMPLETED;
    }
}
