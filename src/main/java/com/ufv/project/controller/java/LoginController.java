package com.ufv.project.controller.java;

import com.ufv.project.model.*;

public class LoginController
{
    /**
     * Create a DataModel from a User.
     *
     * @param user User object
     * @return user's dataModel
     */
    public static DataModel setDatamodelFromUsertype(User user)
    {
        DataModel dataModel = null;

        if (user.getUserType() == UserTypesEnum.STUDENT)
        {
            dataModel = new DataModel((Student) user);
        }
        else if (user.getUserType() == UserTypesEnum.PROFESSOR)
        {
            dataModel = new DataModel((Professor) user);
        }
        else if (user.getUserType() == UserTypesEnum.ADMIN)
        {
            dataModel = new DataModel((Administrator) user);
        }

        return dataModel;

    }

}