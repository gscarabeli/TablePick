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
public class PasswordValidationTest {

    @Test
    public void testUserStrongPassword() {
        //The password must contain: A capital letter, a number, 
        //a special character ($&@#) and at least 8 characters 

        //This password is valid
        assertTrue(ValidacaoUtil.validaSenha("Test@123"));

    }

    @Test
    public void testUserNotStrongPassword() {
        //The password must contain: A capital letter, a number, 
        //a special character ($&@#) and at least 8 characters 

        //This password is not valid
        assertFalse(ValidacaoUtil.validaSenha("test~123"));
    }
}