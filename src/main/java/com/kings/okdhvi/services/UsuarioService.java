package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.usuario.*;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.AdicionarCargoRequest;
import com.kings.okdhvi.model.DTOs.CriarImagemRequest;
import com.kings.okdhvi.model.DTOs.UsuarioADTO;
import com.kings.okdhvi.model.DTOs.UsuarioApreDTO;
import com.kings.okdhvi.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository ur;

    @Autowired
    PedidoExclusaoContaServices pecs;

    @Autowired
    ImagemService is;

    @Autowired
    PedidoDeTitulacaoServices pets;

    @Autowired
            DecisaoModeradoraService dms;

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    String emailRegex = ".{3,64}\\@.{4,255}";
    String senhaRegex = ".{8,64}";
    String cpfRegex = "[0-9]{11}";
    String CPFRepetidoRegex = "(.)\1{11}";
    String telefoneRegex = "[0-9]{2}9[1-9][0-9]{7}";
    Date dataMinima = new Date(0,Calendar.JANUARY,1);

    public Long buscarId(UserDetails ud) {
        if(ud == null) {
            return null;
        }
        return encontrarPorEmail(ud.getUsername(), false).getIdUsuario();
    }

    public Usuario saveUsuario(Usuario u) {
        if(u == null) {
            throw new NullResourceException("Usuário nulo submetido");
        }
        validarDados(u, true);
        u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
        u.setEstadoDaConta(EstadoDaConta.PADRAO);

        Usuario r = ur.save(u);
        ur.flush();
        return r;
    }

    public Usuario atualizarFoto(Long id, Imagem i) {
        Usuario u = encontrarPorId(id, false);
        u.setFotoDePerfil(i);
        return ur.save(u);
    }

    @Transactional
    public PedidoExclusaoConta requisitarExclusao(Long id) {
        Usuario u = encontrarPorId(id, false);
        PedidoExclusaoConta pecExistente = u.getPedidoExclusao();
        if(pecExistente != null) {
            pecs.deletarPedidoDeExclusaoPeloId(pecExistente.getId());
        }
        PedidoExclusaoConta pec = new PedidoExclusaoConta(u);
        pecs.salvarPedidoExclusao(pec);
        u.setPedidoExclusao(pec);
        ur.save(u);
        return pec;
    }

    @Transactional
    public Usuario gerarPedidoDeTitulacao(Long id, PedidoDeTitulacaoDTO pdtDTO) {
        PedidoDeTitulacao pet = new PedidoDeTitulacao();
        Usuario u = encontrarPorId(id, false);

        String anexo = pdtDTO.anexoBase64();
        Imagem i = null;
        if(anexo != null && !anexo.isEmpty()) {
            CriarImagemRequest cir = new CriarImagemRequest(anexo, "Documento anexado pelo usuário " +
                    u.getIdUsuario() + "- " + u.getNome(), "Documento de anexo", pdtDTO.tipoAnexo());
            i = is.criarImagem(cir, u);
        }

        pet.setAnexo(i);
        EstadoDaConta edce = EstadoDaConta.MODERADOR;
        switch (pdtDTO.cargoRequisitado()) {
            case 5:
                edce = EstadoDaConta.ADMINISTRADOR;
                break;
            case 4:
                edce = EstadoDaConta.ESPECIALISTA;
                break;
        }
        pet.setCargoRequisitado(edce);
        pet.setMotivacao(pdtDTO.motivacao());
        pet.setRequisitor(u);
        if(u.getPedidoDeTitulacao() != null) {
            pet.setId(u.getPedidoDeTitulacao().getId());
            pets.atualizarPedidoDeTitulacao(pet);
        } else {
            pets.criarPedidoDeTitulacao(pet);
        }
        u.setPedidoDeTitulacao(pet);
        return ur.save(u);
    }




    public Usuario atualizarUsuario (UsuarioADTO novo, Long id) {
        if(novo.equals(null)) {
            throw new NullResourceException("Usuário nulo submetido");
        }
        Usuario original = encontrarPorId(id, false);
        original.setNome(novo.nome());
        original.setTelefone(novo.telefone());
        original.setNotificacoesPorEmail(novo.notificacoesPorEmail());
        original.setSenha(novo.senha());
        validarDados(original, false);
        return ur.save(original);
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void exclusaoGeralAgendada() {
        ArrayList<PedidoExclusaoConta> pedidos = new ArrayList<>(pecs.encontrarTodosPedidosDeExclusao());
        Instant agora = Instant.now();
        pedidos.removeIf(p -> p.getDataPedido().toInstant().plus(30, ChronoUnit.DAYS).isAfter(agora));
        logger.info("Encontrado " + pedidos.size() + " marcados para deleção na data de hoje.");
        pedidos.forEach(p -> {delecaoProgramada(p.getUsuarioPedido().getIdUsuario());});
    }

    public void validarDados(Usuario u, boolean novo) {
        if(novo) {
            if(encontrarPorEmail(u.getEmail(), true) != null || encontrarPorCPF(u.getCpf(), true) != null) {
                throw new DuplicatedResource("Usuário já existente!");
            }
        }

        verificarCPF(u.getCpf());
        verificarEmail(u.getEmail());
        verificarSenha(u.getSenha());
        if(u.getTelefone() != null) {
            verificarTelefone(u.getTelefone());
        }
        verificarDataDeNasc(u.getDataDeNascimento());
        verificarNCPF(u.getCpf());
    }

    public Usuario mockUsuario() {
        Usuario u = new Usuario();
        Random r = new Random();
        u.setCpf("13178339965");
        u.setEmail("abcdef@gmail.com");
        u.setTelefone("47999999999");
        u.setSenha("293912391");
        u.setNome("Escritor C.");
        u.setDataDeNascimento(new Date("03/31/2008"));
        u.setIdUsuario(r.nextLong(1000,1100));
        return u;
    }

    public void alterarTitulacao(Long id, AdicionarCargoRequest acr) {
        Usuario r = encontrarPorId(id, false);
        Usuario u = encontrarPorId(acr.id(), false);
        if(u.getPedidoDeTitulacao() != null) {
            pets.deletarPedidoDeTitulacaoPeloId(u.getPedidoDeTitulacao().getId());
        }
        EstadoDaConta edc = EstadoDaConta.PADRAO;
        //Só pode ser 4 ou 6 (Poder de administrador, para garantir que ele possa adicionar novos admimnistrador) graças ao Pré-Authorize
        int poder = r.getEstadoDaConta() == EstadoDaConta.MODERADOR ? 4 : 6;
        if(acr.idCargo() >= poder) {
            throw new UnauthorizedActionException("O usuário não possui poder o suficiente para realizar tal operação.");
        }
        if(acr.idCargo() != 1) {
            u.setOculto(false);
        }
        switch(acr.idCargo())
        {
            case 1:
                edc = EstadoDaConta.SUSPENSO;
                u.setOculto(true);
                break;
            case 2:
                edc = EstadoDaConta.PADRAO;
                break;
            case 3:
                edc = EstadoDaConta.ESPECIALISTA;
                break;
            case 4:
                edc = EstadoDaConta.MODERADOR;
                break;
            case 5:
                edc = EstadoDaConta.ADMINISTRADOR;
        }
        u.setEstadoDaConta(edc);

    }

    @Transactional
    public void delecaoPorAdministrador(Long id, Long idRequisitor) {
        Usuario u = encontrarPorId(id, false);
        Usuario r = encontrarPorId(idRequisitor, false);
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setNomeModerado(u.getNome());
        dm.setTipo("Usuário");
        dm.setData(Date.from(Instant.now()));
        dm.setResponsavel(r);
        dm.setUsuarioModerado(u);
        dm.setMotivacao("Usuário requisitou a própria deleção.");
        dm.setIdModerado(id);

        dms.criarDecisaoModeradora(dm);
        ur.deleteById(id);
        ur.flush();
    }

    @Transactional
    public void delecaoProgramada(Long id) {
        Usuario u = encontrarPorId(id, false);
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setNomeModerado(u.getNome());
        dm.setTipo("Usuário");
        dm.setData(Date.from(Instant.now()));
        dm.setResponsavel(null);
        dm.setMotivacao("Deleção requisistada pelo usuário e auto-executada pelo sistema.");
        dm.setIdModerado(id);
        dms.criarDecisaoModeradora(dm);
        ur.delete(u);
    }


    public Usuario encontrarPorId(Long id, boolean anulavel) {
        var u = ur.findById(id);
        if(anulavel && u.isEmpty()) {
            return null;
        }
        return u.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public Usuario encontrarPorEmail(String email, boolean anulavel) {
        var u = ur.findByemail(email);
        if(anulavel && u.isEmpty()) {
            return null;
        }
        return u.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public Usuario encontrarPorCPF(String cpf, boolean anulavel) {
        var u = ur.findBycpf(cpf);
        if(anulavel && u.isEmpty()) {
                return null;
        }
        return u.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }


    public void verificarEmail(String email) {
        if (!email.matches(emailRegex)) {
            throw new InvalidEmailException("Email inválido!");
        }
    }

    public void verificarSenha(String senha) {
        if(!(senha.matches(".*[a-z]|[a-z].*"))) {
            throw new InvalidPasswordException("A senha deve conter letras minúsculas!");
        }
        if(!(senha.matches(".*[A-Z]|[A-Z].*"))) {
            throw new InvalidPasswordException("A senha deve conter pelo menos uma letra maiúscula!");
        }
        if(!(senha.matches(".*[0-9]|[0-9].*"))) {
            throw new InvalidPasswordException("A senha deve conter pelo menos um número!");
        }
        if(!(senha.matches(".*\\W|\\W.*"))) {
            throw new InvalidPasswordException("A senha deve conter pelo menos um caracter especial!");
        }
        if (!senha.matches(senhaRegex)) {
            throw new InvalidPasswordException("A senha deve ter entre 8-64 dígitos!");
        }
    }

    public void verificarTelefone(String telefone) {
        if (!telefone.matches(telefoneRegex)) {
            throw new InvalidTelephoneException("Telefone inválido!");
        }
    }

    public void verificarDataDeNasc(Date data) {
        if (dataMinima.after(data)) {
            throw new InvalidDateException("Data inválida!");
        }
    }

    public boolean verificarNCPF(String cpf) {
        int dig1;
        int dig2;

        //Divide o CPF em números
        char[] digits = cpf.toCharArray();
        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = converterEmNumero(digits[i]);
        }
        dig1 = digitoVerificador(numbers, 0, 0);
        dig2 = digitoVerificador(numbers, 1, dig1);

        if (numbers[9] == dig1 && numbers[10] == dig2) {
            return (true);
        }
        return false;
    }

    public void verificarCPF(String cpf) {
        if (cpf.length() != 11) {
            throw new InvalidCPFException("O tamanho do CPF precisa ser de 11 caracteres");
        }
        if (!cpf.matches(cpfRegex)) {
            throw new InvalidCPFException("O CPF deve conter somente números!");
        }
        if (!verificarNCPF(cpf) || cpf.matches(CPFRepetidoRegex)) {
            throw new InvalidCPFException("O CPF é inválido!");
        }
    }

    public int converterEmNumero(Character a) {
        return Integer.parseInt(a.toString());
    }

    public int digitoVerificador(int[] n, int etapa, int digv) {
        int res = 0;
        int somaV = 0;
        if (etapa == 1) {
            n[9] = digv;
        }
        int b = -1;
        for (int i = 10 + etapa; i >= 2; i--) {
            b++;
            somaV += n[b] * i;
        }
        res = somaV % 11;

        if (res > 10 || res == 0|| res == 1) {
            res = 0;
        } else {
            res = 11 - res;
        }
        return res;
    }


}
