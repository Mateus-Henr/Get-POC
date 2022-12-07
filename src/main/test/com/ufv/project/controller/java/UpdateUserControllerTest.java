package com.ufv.project.controller.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserControllerTest
{
    @Test
    public void testRegistration1()
    {
        String str = "MEOO";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkRegistration(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testRegistration2()
    {
        String str = "23";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkRegistration(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testRegistration3()
    {
        String str = "1423";
        boolean resultadoEsperado = true;
        boolean resultado = UpdateUserController.checkRegistration(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testRegistration4()
    {
        String str = "    ";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkRegistration(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testRegistration5()
    {
        String str = " ";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkRegistration(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testEmail1()
    {
        String str = "macaco@monkey.lua";
        boolean resultadoEsperado = true;
        boolean resultado = UpdateUserController.checkEmail(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testEmail2()
    {
        String str = "@monkey.lua";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkEmail(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testEmail3()
    {
        String str = "macaco@.lua";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkEmail(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testEmail4()
    {
        String str = "macaco@monkey.";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkEmail(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testEmail5()
    {
        String str = "macaco@monkeylua";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.checkEmail(str);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testPassword1()
    {
        String str1 = "132444";
        String str2 = "132444";
        boolean resultadoEsperado = true;
        boolean resultado = UpdateUserController.arePasswordsEqual(str1, str2);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testPassword2()
    {
        String str1 = "88872";
        String str2 = "328";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.arePasswordsEqual(str1, str2);
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testPassword3()
    {
        String str1 = "328";
        String str2 = "88872";
        boolean resultadoEsperado = false;
        boolean resultado = UpdateUserController.arePasswordsEqual(str1, str2);
        assertEquals(resultado, resultadoEsperado);
    }
}