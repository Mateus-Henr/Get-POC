package com.ufv.project.controller.java;

import java.util.regex.Pattern;

public class UpdateUserController
{
    /**
     * Check if a string is an email.
     *
     * @param email user email string.
     * @return if the pattern is an email with (something)@(something).(something).
     */
    public static boolean checkEmail(String email)
    {
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{3,}[.]{1}+[a-z]{2,}", email);
    }

    /**
     * Function to compare 2 passwords.
     *
     * @param password       first password field string.
     * @param secondPassword password field string.
     * @return password is equal to second password.
     */
    public static boolean arePasswordsEqual(String password, String secondPassword)
    {
        return password.equals(secondPassword);
    }

    /**
     * Check if a registration is valid.
     *
     * @param registration registration string.
     * @return if the pattern is a string with 4 numbers.
     */
    public static boolean checkRegistration(String registration)
    {
        return Pattern.matches("\\d\\d\\d\\d", registration);
    }

}
