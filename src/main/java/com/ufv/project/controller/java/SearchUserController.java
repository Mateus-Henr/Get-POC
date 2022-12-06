package com.ufv.project.controller.java;

import com.ufv.project.model.*;

public class SearchUserController {
    //Função para pesquisar e retornar a lista de usuarios a partir da inserção de uma string
    public static DataModel setDataModelFromUserType(User user){
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
