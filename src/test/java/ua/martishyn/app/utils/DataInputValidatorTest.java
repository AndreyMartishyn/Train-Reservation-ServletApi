package ua.martishyn.app.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

public class DataInputValidatorTest {
    private static DataInputValidator dataInputValidator;

    @Before
    public void setUp(){
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseWhenDigitsInputForName(){
        String actual = "Small123";
        boolean result =  dataInputValidator.isValidNameField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenLargeInputForName(){
        String actual = "BigInputIsABigProblemForEverybody";
        boolean result =  dataInputValidator.isValidNameField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenSmallInputForPassword(){
        String actual = "pass";
        boolean result =  dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenInputWithSymbolsForPassword(){
        String actual = "pass@#!11";
        boolean result =  dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenSmallInputForEmail(){
        String actual = "p@g.com";
        boolean result =  dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenNotCorrectInputForEmail(){
        String actual = "my@WrongEmail@.com";
        boolean result =  dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }




}
