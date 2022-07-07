package com.springbatch.batch;

import com.springbatch.entity.Order;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class OrderItemReader implements ItemReader<Order> {

    @Override
    public Order read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
