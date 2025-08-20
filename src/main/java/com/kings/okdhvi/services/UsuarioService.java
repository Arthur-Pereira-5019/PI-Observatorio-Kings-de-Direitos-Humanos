package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.*;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    String cpfRegex = ".{11}";
    String CPFRepetidoRegex = "(.)\1{11}";
    String telefoneRegex = "[0-9]{2}9[1-9][0-9]{7}";
    Date dataMinima = new Date(0,Calendar.JANUARY,1);

    public Usuario saveUsuario(Usuario u) {
        validarDados(u);
        return ur.save(u);
    }

    public boolean login (String senha, String email) {
        verificarEmail(email);
        verificarSenha(senha);
        if(encontrarPorEmail(email).getSenha().equals(senha)) {
            return true;
        }
        return false;
    }

    public Usuario atualizarUsuario (Usuario u, Long id) {
        Usuario original = encontrarPorId(id);
        original = u;
        u.setIdUsuario(id);
        return ur.save(original);
    }

    public void validarDados(Usuario u) {
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
        u.setCpf("131.783.399-65");
        u.setEmail("abcdef@gmail.com");
        u.setTelefone("47999999999");
        u.setSenha("293912391");
        u.setNome("Escritor C.");
        u.setDataDeNascimento(new Date("03/31/2008"));
        u.setIdUsuario(r.nextLong(1000,1100));
        return u;
    }

    public void deletarPeloId(Long id) {
        ur.delete(encontrarPorId(id));
    }

    public Usuario encontrarPorId(Long id) {
        return ur.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    public Usuario encontrarPorEmail(String email) {
        return ur.findByemail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
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
