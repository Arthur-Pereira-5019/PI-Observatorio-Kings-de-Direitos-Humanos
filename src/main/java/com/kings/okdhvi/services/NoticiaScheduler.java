package com.kings.okdhvi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NoticiaScheduler {
    @Autowired
    private NoticiaServices ns;

    @Scheduled(fixedRate = 2000000)
    public void baixarNoticiasPeriodicamente() {
        ns.salvarNoticias();
    }
}
