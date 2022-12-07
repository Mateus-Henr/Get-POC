package com.ufv.project.controller.java;

import java.util.regex.Pattern;

public class CreateUserController
{
    public static boolean arePasswordsEqual(String password, String secondPassword)
    {
        return password.equals(secondPassword);
    }

    public static boolean checkEmail(String email)
    {
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email);
    }

    public static boolean checkRegistration(String registration)
    {
        return Pattern.matches("\\d\\d\\d\\d", registration);
    }

}
