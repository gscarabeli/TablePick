/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
*/
package br.edu.fesa.tablepick.model.test;

import br.edu.fesa.tablepick.util.ValidacaoUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 
@author Gustavo*/
public class EmailValidationTest {

    @Test
    //The password must pass to REGEX: "^[a-zA-Z0-9+&*-]+(?:\.[a-zA-Z0-9+&-]+)@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$";

    public void testEmailValid() {
        assertTrue(ValidacaoUtil.validaEmail("082210030@faculdade.cefsa.edu.br"));
        assertTrue(ValidacaoUtil.validaEmail("0822_10030@faculdade.cefsa.edu.br"));
        assertTrue(ValidacaoUtil.validaEmail("a0822_10030@faculdade.cefsa.edu.br"));
        assertTrue(ValidacaoUtil.validaEmail("aB0822_10030@faculdade.cefsa.edu.br"));
    }

    @Test
    public void testEmailIsNotValid() {
        assertFalse(ValidacaoUtil.validaEmail("082210030@faculdade.cefsa.edu."));
        assertFalse(ValidacaoUtil.validaEmail("082210030faculdade.cefsa.edu.br"));
        assertFalse(ValidacaoUtil.validaEmail("082210030@faculdade"));
        assertFalse(ValidacaoUtil.validaEmail("@faculdade.com"));
    }
}