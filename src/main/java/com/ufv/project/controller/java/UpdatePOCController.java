package com.ufv.project.controller.java;

import com.ufv.project.model.Field;
import com.ufv.project.model.User;

import java.util.List;

public class UpdatePOCController
{
    /**
     * Find index of a user in a userList
     *
     * @param userList List of User's objects
     * @param user User object
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
     * @param userList List of User's objects
     * @param field Field object
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