package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.repositories.PedidoExclusaoContaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PedidoExclusaoContaServices {
    @Autowired
    PedidoExclusaoContaRepository pecr;

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
