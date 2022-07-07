package com.springbatch.batch;

import com.springbatch.entity.Order;
import com.springbatch.listener.JobCompletionNotificationListener;
import com.springbatch.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderRepository orderRepository;

    @Bean
    public ItemReader<Order> reader() {
        log.info("Go to reader: " + Thread.currentThread().getName());
        return new FlatFileItemReaderBuilder<Order>()
                .name("orderItemReader")
                .resource(new ClassPathResource("orders.csv"))
                .delimited()
                .names(new String[] {"customerId", "itemId", "itemPrice", "itemName", "purchaseDate"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                    setTargetType(Order.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<Order, Order> processor() {
        log.info("Go to process: " + Thread.currentThread().getName());
        return new ItemProcessor<Order, Order>() {
            @Override
            public Order process(Order order) throws Exception {
                log.info(Thread.currentThread().getName() + ": " + order);
                return order;
            }
        };
    }

    @Bean
    public ItemWriter<Order> writer() {
        log.info("Go to writer: " + Thread.currentThread().getName());
        RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();
        writer.setRepository(orderRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job importOrderJob() {
        log.info("Go to importOrderJob: " + Thread.currentThread().getName());
        return jobBuilderFactory.get("importOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Order, Order> chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
