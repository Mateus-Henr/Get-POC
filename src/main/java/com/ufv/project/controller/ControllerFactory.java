package com.ufv.project.controller;

import com.ufv.project.model.DataModel;
import javafx.util.Callback;

public class ControllerFactory
{
    public static Callback<Class<?>, Object> controllerFactoryWithDataModel(DataModel dataModel)
    {
        return type ->
        {
            if (type == LoginController.class)
            {
                return new LoginController();
            }
            else if (type == CreatePOCController.class)
            {
                return new CreatePOCController(dataModel);
            }
            else if (type == PersonalInfoController.class)
            {
                return new PersonalInfoController(dataModel);
            }
            else if (type == SearchPOCController.class)
            {
                return new SearchPOCController(dataModel);
            }
            else if (type == TopMenuController.class)
            {
                return new TopMenuController(dataModel);
            }
            else if (type == AnalyzePOCController.class)
            {
                return new AnalyzePOCController(dataModel);
            }
            else if (type == UpdateUserController.class)
            {
                return new UpdateUserController(dataModel);
            }
            else if (type == UpdatePOCController.class)
            {
                return new UpdatePOCController(dataModel);
            }
            else
            {
                try
                {
                    return type.getDeclaredConstructor().newInstance();
                }
                catch (Exception e)
                {
                    throw new IllegalArgumentException("ERROR: Invalid controller.");
                }
            }
        };

    }

}
