package com.ufv.project.controller.java;

import java.util.regex.Pattern;

public class UpdateUserController
{

    public static boolean checkEmail(String email)
    {
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email);
    }

    public static boolean checkRegistration(String registration)
    {
        return Pattern.matches("\\d\\d\\d\\d", registration);
    }

    public static boolean checkStringMax(String string){
        return string.length() <= 100;
    }

    //Função para dar um Set em todas informações do User e retornar o novo User
    public static boolean arePasswordsEqual(String password, String secondPassword)
    {
        return password.equals(secondPassword);
    }

}
