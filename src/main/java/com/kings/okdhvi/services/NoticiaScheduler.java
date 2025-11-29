package com.kings.okdhvi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class NoticiaScheduler {
    @Autowired
    private NoticiaServices ns;

    // a cada 1h
    @Scheduled(fixedRate = 60)
    public void baixarNoticiasPeriodicamente() {
        ns.salvarNoticias();
    }
}
