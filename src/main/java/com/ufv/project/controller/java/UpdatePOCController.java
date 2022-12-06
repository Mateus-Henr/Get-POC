package com.ufv.project.controller.java;

import com.ufv.project.model.Field;
import com.ufv.project.model.User;

import java.util.List;
import java.util.regex.Pattern;

public class UpdatePOCController
{
    public static boolean checkStringMax(String string){
        return string.length() <= 100;
    }

    //Função para dar um Set em todas informações do POC e retornar o novo POC
    public static int findIndex(List<? extends User> userList, User user)
    {
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUsername().equals(user.getUsername()))
            {
                return i;
            }
        }

        return -1;
    }

    public static int findIndex(List<Field> userList, Field field)
    {
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getId() == field.getId())
            {
                return i;
            }
        }

        return -1;
    }

}