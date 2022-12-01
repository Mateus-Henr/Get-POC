package com.ufv.project.controller.java;

import com.ufv.project.model.Field;
import com.ufv.project.model.User;

import java.util.List;

public class UpdatePOCController {
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