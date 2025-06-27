package br.com.dio.validator;
import br.com.dio.exception.ValidatorException;
import br.com.dio.model.UserModel;

public class UserValidator {

    private UserValidator() {

    }

    public static void verifyModel(final UserModel model) throws ValidatorException {
        if(stringIsBlank(model.getName()))
            throw new ValidatorException("Inform a valid name");
        if(model.getName().length() <= 1 )
            throw new ValidatorException("The name should have more than one character");
        if(stringIsBlank(model.getEmail()))
            throw new ValidatorException("Inform a valid email");
        if((!model.getEmail().contains("@")) || (!model.getEmail().contains(".")))
            throw new ValidatorException("Inform a valid email");
    }

    private static boolean stringIsBlank(final String value){
        return value == null || value.isBlank();
    }

}
