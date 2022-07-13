package com.springbatch.batch.reader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LinesChunkReader implements ItemReader<String>, StepExecutionListener {

    protected int count = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (count++ > 0){
            return null;
        }

        System.out.println("Reading...");
        return "Data from LinesChunkReader";
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Line Reader initialized...");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Line Reader ended...");
        return ExitStatus.COMPLETED;
    }
}
