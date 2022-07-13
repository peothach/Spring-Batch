package com.springbatch.batch.config;

import com.springbatch.batch.processor.LinesChunkProcessor;
import com.springbatch.batch.reader.LinesChunkReader;
import com.springbatch.batch.writer.LinesChunkWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunksConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<String> itemChunkReader() {
        return new LinesChunkReader();
    }

    @Bean
    public ItemProcessor<String, String> itemChunkProcessor() {
        return new LinesChunkProcessor();
    }

    @Bean
    public ItemWriter<String> itemChunkWriter() {
        return new LinesChunkWriter();
    }

    @Bean
    protected Step processChunkLines(ItemReader<String> reader,
                                     ItemProcessor<String, String> processor, ItemWriter<String> writer) {
        return steps
                .get("processChunkLines")
                .<String, String>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job chunksJob() {
        return jobs
                .get("chunksJob")
                .start(processChunkLines(itemChunkReader(), itemChunkProcessor(), itemChunkWriter()))
                .build();
    }
}
