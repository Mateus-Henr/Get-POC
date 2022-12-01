package com.ufv.project.controller.java;

import com.ufv.project.model.*;

public class SearchUserController {
    public static DataModel setDatamodelFromUserType(User user){
        UserTypesEnum userType = user.getUserType();

        DataModel dataModel = null;

        if (userType == UserTypesEnum.STUDENT)
        {
            dataModel = new DataModel((Student) user);
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            dataModel = new DataModel((Professor) user);
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            dataModel = new DataModel((Administrator) user);
        }

        return dataModel;
    }
}
