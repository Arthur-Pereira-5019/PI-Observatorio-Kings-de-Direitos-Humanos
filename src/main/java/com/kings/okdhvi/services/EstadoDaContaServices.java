package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.repositories.EstadoDaContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoDaContaServices {

    @Autowired
    EstadoDaContaRepository edcr;

    public EstadoDaConta criarEstadoDaConta(EstadoDaConta edc) {
        if(edc == null) {
            throw new NullResourceException("Estado da Conta nulo submetido");
        }
        return edcr.save(edc);
    }

    public List<EstadoDaConta> encontrarTodosEstadosDaConta(EstadoDaConta edc) {
        return edcr.findAll();
    }

    public void deletarEstadoDaContaPeloId(Long id) {
        EstadoDaConta edc = encontrarEstadoDaContaPeloId(id);
        edcr.delete(edc);
    }

    public EstadoDaConta encontrarEstadoDaContaPeloId(Long id) {
        return edcr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estado da Conta não encontrado!"));
    }

    public EstadoDaConta atualizarEstadoDaConta(EstadoDaConta novo) {
        if(novo == null) {
            throw new NullResourceException("Estado da Conta nulo submetido");
        }
        EstadoDaConta original = encontrarEstadoDaContaPeloId(novo.getIdEstado());
        original.setEstadoNocivo(novo.isEstadoNocivo());
        original.setNomeEstado(novo.getNomeEstado());
        original.setAplicavelPorModerador(novo.isAplicavelPorModerador());
        original.setRequisitavel(novo.isRequisitavel());
        original.setEstadoNocivo(novo.isEstadoNocivo());
        original.setPodeComentar(novo.isPodeComentar());
        original.setPodeConcederTitulacoes(novo.isPodeConcederTitulacoes());
        return edcr.save(original);
    }
}
