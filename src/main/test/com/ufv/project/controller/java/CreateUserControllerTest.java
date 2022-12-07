package com.ufv.project.controller.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

class CreateUserControllerTest
{

    @Test
    public void testRegistrationWord(){
        String str = "CARO";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkRegistration(str);
        assertTrue(resultado==resultadoEsperado);
    }
    @Test
    public void testRegistrationNumber(){
        String str = "123";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkRegistration(str);
        assertTrue(resultado==resultadoEsperado);
    }

    @Test
    public void testRegistrationTrue(){
        String str = "4567";
        boolean resultadoEsperado = true;
        boolean resultado = CreateUserController.checkRegistration(str);
        assertTrue(resultado==resultadoEsperado);
    }

    @Test
    public void testRegistrationNULL(){
        String str = "";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkRegistration(str);
        assertTrue(resultado==resultadoEsperado);
    }
    @Test
    public void testRegistrationSpaces(){
        String str = "    ";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkRegistration(str);
        assertTrue(resultado==resultadoEsperado);
    }

    @Test
    public void testEmailTrue(){
        String str = "joaoandrade1@ufv.br";
        boolean resultadoEsperado = true;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }

    @Test
    public void testEmailNoUser(){
        String str = "@ufv.br";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testEmailNoDomain(){
        String str = "joaoaandrade1@.br";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testEmailNoUser(){
        String str = "@ufv.br";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testEmailAfterDot(){
        String str = "joaoandrade1@ufv.";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testEmailNoDot(){
        String str = "joaoandrade1@ufvbr";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.checkEmail(str);
        assertTrue(resultado==resultadoEsperado);
    }
    //certo
    //compara passwords
    public void testPasswordTrue(){
        String str1 = "1234";
        String str2 = "1234";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.arePasswordsEqual(str1,str2);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testPasswordOneDiferent(){
        String str1 = "123";
        String str2 = "1234";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.arePasswordsEqual(str1,str2);
        assertTrue(resultado==resultadoEsperado);
    }

    public void testPasswordTwoDifferent(){
        String str1 = "1234";
        String str2 = "123";
        boolean resultadoEsperado = false;
        boolean resultado = CreateUserController.arePasswordsEqual(str1,str2);
        assertTrue(resultado==resultadoEsperado);
    }


}