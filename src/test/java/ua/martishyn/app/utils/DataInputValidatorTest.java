package ua.martishyn.app.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

/**
 * Unit testing for DataInputValidator API
 */

public class DataInputValidatorTest {
    private static DataInputValidator dataInputValidator;

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseWhenDigitsInputForName() {
        String actual = "Small123";
        boolean result = dataInputValidator.isValidNameField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenLargeInputForName() {
        String actual = "BigInputIsABigProblemForEverybody";
        boolean result = dataInputValidator.isValidNameField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenSmallInputForPassword() {
        String actual = "pass";
        boolean result = dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenInputWithSymbolsForPassword() {
        String actual = "pass@#!11";
        boolean result = dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenSmallInputForEmail() {
        String actual = "p@g.com";
        boolean result = dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenNotCorrectInputForEmail() {
        String actual = "my@WrongEmail@.com";
        boolean result = dataInputValidator.isValidPasswordField(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenNotCorrectInputForNumber() {
        String actual = "123*4";
        boolean result = dataInputValidator.isValidNumInput(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenStringInputWithThrash() {
        String actual = "Nmn22@XZa%^";
        boolean result = dataInputValidator.isValidStringInput(actual);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenDateEmptyOrNull() {
        String date1 = null;
        String date2 = "";
        boolean result = dataInputValidator.isValidDateInput(date1, date2);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenCorrectInputForDates() {
        String date1 = "2222-22-22 22:22";
        String date2 = "2223-33-44 10:22";
        boolean result = dataInputValidator.isValidDateInput(date1, date2);
        Assert.assertFalse(result);
    }
}
