/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.util;

import java.util.regex.Pattern;
/**
 *
 * @author DAVI
 */
public class ValidacaoUtil {
    
    //private static final String EMAIL_REGEX = "^[a-zA-Z0-9+&-]+(?:\\.[a-zA-Z0-9_+&-]+)@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)\\.[a-zA-Z]{2,7}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String SENHA_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%_*?&])[A-Za-z\\d@#$!%_*?&]{8,}$";

    public static boolean validaEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean validaSenha(String senha) {
        return Pattern.matches(SENHA_REGEX, senha);
    }
    
    public static boolean isCampoVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
