package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.PedidoDeTitulacaoRepository;
import com.kings.okdhvi.repositories.PedidoExclusaoContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDeTitulacaoServices {
    @Autowired
    PedidoDeTitulacaoRepository petr;

    public PedidoDeTitulacao criarPedidoDeTitulacao(PedidoDeTitulacao pet) {
        return petr.save(pet);
    }


    public PedidoDeTitulacao encontrarPedidoDeTitulacao(Long id) {
        return petr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido de titulação não encontrado!"));
    }

    public List<PedidoDeTitulacao> encontrarTodosPedidosDeTitulacao() {
        return petr.findAll();
    }

    @Transactional
    public void deletarPedidoDeTitulacaoPeloId(Long id) {
        petr.delete(encontrarPedidoDeTitulacao(id));
    }

    public PedidoDeTitulacao atualizarPedidoDeTitulacao(PedidoDeTitulacao pet) {
       PedidoDeTitulacao velhoPet = encontrarPedidoDeTitulacao(pet.getId());
        velhoPet.setRequisitor(pet.getRequisitor());
        velhoPet.setMotivacao(pet.getMotivacao());
        velhoPet.setCargoRequisitado(pet.getCargoRequisitado());
        velhoPet.setContato(pet.getContato());
        return petr.save(velhoPet);
    }

    public PedidoDeTitulacao encontrarPedidoPeloUsuario(Usuario u) {
        return petr.findByRequisitor(u).orElseThrow(() -> new ResourceNotFoundException("O usuário não tem requisições"));
    }
}
