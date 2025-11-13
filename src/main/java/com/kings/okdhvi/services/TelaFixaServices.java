package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.PersistenceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.TelaFixa;
import com.kings.okdhvi.repositories.TelaFixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelaFixaServices {
    @Autowired
    TelaFixaRepository tfr;

    public TelaFixa saveTela(TelaFixa tf) {
        if(tf.getId() >= 2L || tf.getId() < 0L) {
            throw new PersistenceException("Tela inexistente!");
        }
        try {
            return tfr.save(tf);
        } catch (Exception e) {
            throw new PersistenceException("Erro ao atualizar tela");
        }
    }

    public TelaFixa lerTela(Long id) {
        if(tfr.findById(id).isPresent()) {
            return tfr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tela não encontrada!"));
        } else {
            return saveTela(new TelaFixa(id, "", ""));
        }
    }
}
