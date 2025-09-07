package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.usuario.*;
import com.kings.okdhvi.exception.usuario.NullResourceException;
import com.kings.okdhvi.model.EstadoDaContaEnum;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.RetornoLogin;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository ur;

    String emailRegex = ".{3,64}\\@.{4,255}";
    String senhaRegex = ".{8,64}";
    String cpfRegex = "[0-9]{11}";
    String CPFRepetidoRegex = "(.)\1{11}";
    String telefoneRegex = "[0-9]{2}9[1-9][0-9]{7}";
    Date dataMinima = new Date(0,Calendar.JANUARY,1);

    public Usuario saveUsuario(Usuario u) {
        if(u == null) {
            throw new NullResourceException("Usuário nulo submetido");
        }
        validarDados(u);
        u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
        u.setEstadoDaConta(EstadoDaContaEnum.PADRAO);
        return ur.save(u);
    }

    public RetornoLogin login (String senha, String email) {
        verificarEmail(email);
        verificarSenha(senha);
        Usuario encontrado = encontrarPorEmail(email, false);
        if(encontrado.getSenha().equals(senha)) {
            return new RetornoLogin(encontrado.getIdUsuario(), encontrado.getNome());
        }
        throw new InvalidCPFException("Login Inválido! Verifique E-Mail e Senha.");
    }

    public Usuario atualizarImagem (Long id, Imagem i) {
        Usuario u = encontrarPorId(id, false);
        u.setImagem(i);
        return ur.save(u);
    }

    public Usuario atualizarUsuario (Usuario novo) {
        if(novo == null) {
            throw new NullResourceException("Usuário nulo submetido");
        }
        Usuario original = encontrarPorId(novo.getIdUsuario(), false);
        original.setCpf(novo.getCpf());
        original.setEmail(novo.getEmail());
        original.setNome(novo.getNome());
        original.setEmail(novo.getEmail());
        original.setOculto(novo.isOculto());
        original.setTelefone(novo.getTelefone());
        original.setDataDeNascimento(novo.getDataDeNascimento());
        original.setSenha(novo.getSenha());
        return ur.save(original);
    }

    public void validarDados(Usuario u) {
        if(encontrarPorEmail(u.getEmail(), true) != null || encontrarPorCPF(u.getCpf(), true) != null) {
            throw new DuplicatedResource("Usuário já existente!");
        }

        verificarCPF(u.getCpf());
        verificarEmail(u.getEmail());
        verificarSenha(u.getSenha());
        verificarSenha(u.getTelefone());
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

    public void deletarPeloId(Long id) {
        ur.delete(encontrarPorId(id, false));
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
        if (!senha.matches(senhaRegex)) {
            throw new InvalidPasswordException("Senha inválida!");
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
