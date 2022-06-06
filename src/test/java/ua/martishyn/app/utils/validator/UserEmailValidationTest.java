package ua.martishyn.app.utils.validator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(value = Parameterized.class)
public class UserEmailValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String emailInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> nameInput() {
        return Arrays.asList(new Object[][]{
                {"testmail.com"},
                {"t@gmail.com"},
                {"123@AOL.c"},
                {"$%^$#@gmail.com"},
                {"andrii.martishyn@gm$%^.com"},
                {"danger@bigInputForDomen.com"},
                {"danger@rambler.commmmmm"},
                {"danger@rambler.c"},
                {"dangerBigInputFoundSuddenly@alsoBigInput.com"},
                {"Кіріліца@gmail.com"},
                {"Кіріліца.Не.Працює1@gmail.com"},
                {"invalid_+.@gmail.com"},
                {""}
        });
    }
    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForNameInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidEmailField(emailInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}
