package com.ufv.project.controller.java;

import java.util.regex.Pattern;

public class CreateUserController
{
    /**
     * Function to compare 2 passwords
     *
     * @param password first password field string
     * @param secondPasswordsecond password field string
     * @return if password == second password
     */
    public static boolean arePasswordsEqual(String password, String secondpassword)
    {
        return password.equals(secondpassword);
    }

    /**
     * Check if a string is an email
     *
     * @param email user email string
     * @return if the patern is an email with (something)@(something).(something)
     */
    public static boolean checkEmail(String email)
    {
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{4,}[.]{1}+[a-z]{2,}", email);
    }

    /**
     * Check if a registration is valid
     *
     * @param registration registration string
     * @return if the patern is a string with 4 numbers
     */
    public static boolean checkRegistration(String registration)
    {
        return Pattern.matches("\\d\\d\\d\\d", registration);
    }

}
