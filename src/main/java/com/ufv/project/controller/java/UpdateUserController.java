package com.ufv.project.controller.java;

public class UpdateUserController
{
    //Função para dar um Set em todas informações do User e retornar o novo User
    public static boolean arePasswordsEqual(String password, String secondPassword)
    {
        return password.equals(secondPassword);
    }

}
