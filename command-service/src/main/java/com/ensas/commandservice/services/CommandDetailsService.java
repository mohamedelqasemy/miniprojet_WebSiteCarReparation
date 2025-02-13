package com.ensas.commandservice.services;

import com.ensas.commandservice.repositories.CommandDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandDetailsService {
    private final CommandDetailsRepository commandDetailsRepository;




}
