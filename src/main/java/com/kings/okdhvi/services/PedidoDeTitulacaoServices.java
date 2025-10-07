package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.repositories.PedidoDeTitulacaoRepository;
import com.kings.okdhvi.repositories.PedidoExclusaoContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDeTitulacaoServices {
    @Autowired
    PedidoDeTitulacaoRepository petr;

    public PedidoDeTitulacao salvarPedidoTitulacao(PedidoDeTitulacao pet) {
        return petr.save(pet);
    }

    public PedidoDeTitulacao encontrarPedidoDeTitulacao(Long id) {
        return petr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido de titulação não encontrado!"));
    }

    public List<PedidoDeTitulacao> encontrarTodosPedidosDeTitulacao() {
        return petr.findAll();
    }

    public void deletarPedidoDeTitulacaoPeloId(Long id) {
        petr.delete(encontrarPedidoDeTitulacao(id));
    }
}
