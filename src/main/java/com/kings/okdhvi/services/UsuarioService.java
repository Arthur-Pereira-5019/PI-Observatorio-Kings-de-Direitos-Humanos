package com.kings.okdhvi.services;

public class UsuarioService {

    String emailRegex = ".{3,64}\\@.{4,255}";
    String passwordRegex = ".{8,64}";


    public boolean verifyEmail(String email) {
        return email.matches(emailRegex);
    }

    public boolean verifyPassword(String passw) {
        return passw.matches(passwordRegex);
    }

    public boolean verifyCPF(String cpf) {
        char[] digits = cpf.toCharArray();
        int[] numbers = new int[11];
        for(int i = 0; i < 11;i++) {
            numbers[i] = convertToNumber(digits[i]);
        }
        int t = 0;
        for(int i = 9; i > 0;i--) {
            t += numbers[i] *i;
        }


        return true;
    }

    public int convertToNumber(char a) {
        return Integer.valueOf(a);
    }


}
