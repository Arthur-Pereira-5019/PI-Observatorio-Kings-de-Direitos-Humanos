package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.InvalidCPFException;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

public class UsuarioService {

    @Autowired
    UsuarioRepository ur;

    String emailRegex = ".{3,64}\\@.{4,255}";
    String senhaRegex = ".{8,64}";
    String cpfRegex = ".{11}";
    String CPFRepetidoRegex = "(.)\1{11}";
    String telefoneRegex = "[0-9]{2}9[1-9][0-9]{7}";
    Date dataMinima = new Date(1900, Calendar.JANUARY,1);



    public boolean verificarEmail(String email) {
        return email.matches(emailRegex);
    }

    public boolean verificarSenha(String senha) {
        return senha.matches(senhaRegex);
    }

    public boolean verificarTelefone(String telefone) {
        return telefone.matches(telefoneRegex);
    }

    public boolean verificarDataDeNasc(Date data) {
        return dataMinima.before(data);
    }

    public boolean verificarNCPF(String cpf) {
        int dig1;
        int dig2;

        //Divide o CPF em números
        char[] digits = cpf.toCharArray();
        int[] numbers = new int[11];
        for(int i = 0; i < 11;i++) {
            numbers[i] = converterEmNumero(digits[i]);
        }
            dig1 = digitoVerificador(numbers,0,0);
            dig2 = digitoVerificador(numbers,1,dig1);

        if(numbers[9] == dig1 && numbers[10] == dig2) {
            return(true);
        }
        return(false);
    }

    public String verificarCPF(String cpf) {
        if(cpf.length()!=11) {
            throw new InvalidCPFException("O tamanho precisa ser de 11 caracteres");
        }
        if(!cpf.matches(cpfRegex)) {
            throw new InvalidCPFException("O CPF deve conter somente números!");
        }
        if(!verificarNCPF(cpf) || !cpf.matches(CPFRepetidoRegex)) {
            throw new InvalidCPFException("O CPF é inválido!");
        }
        return "Logado!";
    }

    public int converterEmNumero(Character a) {
        return Integer.parseInt(a.toString());
    }

    public int digitoVerificador(int[] n, int etapa, int digv){
        int res = 0;
        int somaV = 0;
        if(etapa == 1) {
            n[9] = digv;
        }
        int b = -1;
        for(int i = 10+etapa; i >= 2;i--) {
            b++;
            somaV += n[b] * i;
        }
        res = somaV%11;

        if(res >= 10) {
            res = 0;
        }else {
            res = 11 - res;
        }
        return res;
    }


}
