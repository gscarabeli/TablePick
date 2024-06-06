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
public class FieldsEmptyValidationTest {

    @Test
    public void testFieldEmpty() {
        assertTrue(ValidacaoUtil.isCampoVazio(null));
        assertTrue(ValidacaoUtil.isCampoVazio(""));
        assertTrue(ValidacaoUtil.isCampoVazio("   "));
        assertFalse(ValidacaoUtil.isCampoVazio("não está vazio"));
        assertFalse(ValidacaoUtil.isCampoVazio("texto"));
    }
}