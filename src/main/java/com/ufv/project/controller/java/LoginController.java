package com.ufv.project.controller.java;

import com.ufv.project.model.*;

public class LoginController
{
    // Metodo para pesquisar no banco de dados e retornar se o login e senha estão corretos.
    //Metodo para retornar o tipo de Usuario que faz login (Professor, Aluno, Estudante)

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