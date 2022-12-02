package com.ufv.project.controller.fx;

import com.ufv.project.model.DataModel;
import javafx.util.Callback;

public class ControllerFactoryFX
{

    /**
     * Gets an instance of the specified controller.
     *
     * @param dataModel data to be passed in the selected controller.
     * @return new instance of a specified controller.
     */
    public static Callback<Class<?>, Object> controllerFactoryWithDataModel(DataModel dataModel)
    {
        return type ->
        {
            if (type == LoginControllerFX.class)
            {
                return new LoginControllerFX();
            }
            else if (type == CreatePOCControllerFX.class)
            {
                return new CreatePOCControllerFX(dataModel);
            }
            else if (type == PersonalInfoControllerFX.class)
            {
                return new PersonalInfoControllerFX(dataModel);
            }
            else if (type == SearchPOCControllerFX.class)
            {
                return new SearchPOCControllerFX(dataModel);
            }
            else if (type == TopMenuControllerFX.class)
            {
                return new TopMenuControllerFX(dataModel);
            }
            else if (type == AnalyzePOCControllerFX.class)
            {
                return new AnalyzePOCControllerFX(dataModel);
            }
            else if (type == UpdateUserControllerFX.class)
            {
                return new UpdateUserControllerFX(dataModel);
            }
            else if (type == UpdatePOCControllerFX.class)
            {
                return new UpdatePOCControllerFX(dataModel);
            }
            else if (type == SearchUserControllerFX.class)
            {
                return new SearchUserControllerFX(dataModel);
            }
            else if (type == CreateUserControllerFX.class)
            {
                return new CreateUserControllerFX(dataModel);
            }
            else
            {
                try
                {
                    return type.getDeclaredConstructor().newInstance();
                }
                catch (Exception e)
                {
                    throw new IllegalArgumentException("Invalid controller.");
                }
            }
        };

    }

}
