package com.ufv.project.controller.java;

import com.ufv.project.model.Field;
import com.ufv.project.model.User;

import java.util.List;
import java.util.regex.Pattern;

public class UpdatePOCController
{
    /**
     * Find index of a user in a userList
     *
     * @param userList
     * @param user
     * @return i the index of the user
     */
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

    /**
     * find index of a field in a userList
     *
     * @param userList
     * @param field
     * @return i index of the field
     */
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