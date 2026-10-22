package com.yourname.myapp.service;

import com.yourname.myapp.repository.CommandRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CommandRepository commandRepository;

    public OrderService(CommandRepository commandRepository){
        this.commandRepository = commandRepository;
    }
}
