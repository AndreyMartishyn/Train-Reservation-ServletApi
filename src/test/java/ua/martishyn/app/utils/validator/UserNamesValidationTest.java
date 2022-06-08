package ua.martishyn.app.utils.validator;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(value = Parameterized.class)
public class UserNamesValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String nameInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> nameInput() {
        return Arrays.asList(new Object[][]{
                {"Small123"},
                {"verybigInputIsNotSuitableNow"},
                {""},
                {"A-A"},
                {"ss'''іі1"},
                {"1111"},
                {"StrangeeeeName11232&&&%$"}
        });
    }

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForNameInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidNameField(nameInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}
