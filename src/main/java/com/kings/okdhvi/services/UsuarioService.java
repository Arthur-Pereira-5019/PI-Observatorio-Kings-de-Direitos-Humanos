package com.kings.okdhvi.services;

public class UsuarioService {

    String emailRegex = ".{3,64}\\@.{4,255}";
    String senhaRegex = ".{8,64}";
    String cpfRegex = ".{11}";


    public boolean verificarEmail(String email) {
        return email.matches(emailRegex);
    }

    public boolean verificarSenha(String senha) {
        return senha.matches(senhaRegex);
    }

    public String verificarNCPF(String cpf) {
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
            return("Verdadeiro");
        }
        return("Falso");
    }

    public void verificarCPF(String cpf) {

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
