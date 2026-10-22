package com.yourname.myapp.service;

import com.yourname.myapp.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandLineService {

    private final OrderLineRepository orderLineRepository;

    public CommandLineService(OrderLineRepository orderLineRepository){
        this.orderLineRepository = orderLineRepository;
    }
}
