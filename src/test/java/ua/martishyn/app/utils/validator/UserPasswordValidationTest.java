package ua.martishyn.app.utils.validator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(value = Parameterized.class)
public class UserPasswordValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String passwordInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> nameInput() {
        return Arrays.asList(new Object[][]{
                {"wronglengthPasswordMoreThansixteenSymbols"},
                {"normalNoDigit"},
                {""},
                {null},
                {"password1#$%^"},
                {"P-A-S-W2123123213"},
                {"ASdasd3425a@#$%^"},
                {"1234567890"}
        });
    }
    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForNameInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidPasswordField(passwordInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}
