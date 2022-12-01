package com.ufv.project.controller.java;
import com.ufv.project.model.Subject;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateUserController
{
    public static boolean arePasswordsEqual(String password, String secondpassword)
    {
        return password.equals(secondpassword);
    }

    public static boolean checkEmail(String email){
        return Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email);
    }

    public static boolean checkRegistration(String registration){
        return Pattern.matches("\\d\\d\\d\\d", registration);
    }

    public static List<MenuItem> initializeCheckMenuItemsFromList(List<Subject> subjectList)
    {
        List<MenuItem> items = new ArrayList<>();

        for (Subject subject : subjectList)
        {
            CheckMenuItem menuItem = new CheckMenuItem();

            menuItem.setId(String.valueOf(subject.getId()));
            menuItem.setText(subject.getName() + " - " + subject.getDescription());

            items.add(menuItem);
        }

        return items;
    }


}
