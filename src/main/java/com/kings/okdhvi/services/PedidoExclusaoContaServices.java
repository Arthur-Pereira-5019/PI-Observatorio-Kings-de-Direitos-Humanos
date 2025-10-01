package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.repositories.PedidoExclusaoContaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoExclusaoContaServices {
    @Autowired
    PedidoExclusaoContaRepository pecr;

    Logger logger = LoggerFactory.getLogger(PedidoExclusaoContaServices.class);

    public PedidoExclusaoConta salvarPedidoExclusao(PedidoExclusaoConta pec) {
        return pecr.save(pec);
    }

    public PedidoExclusaoConta encontrarPedidoDeExclusaoPeloId(Long id) {
        return pecr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido de exclusão não encontrado!"));
    }

    public List<PedidoExclusaoConta> encontrarTodosPedidosDeExclusao() {
        return pecr.findAll();
    }

    public void deletarPedidoDeExclusaoPeloId(Long id) {
        pecr.delete(encontrarPedidoDeExclusaoPeloId(id));
    }






}
