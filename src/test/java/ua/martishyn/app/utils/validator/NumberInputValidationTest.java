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
public class NumberInputValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String numberInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> stationNameInput() {
        return Arrays.asList(new Object[][]{
                {"1A-_sas25A11"},
                {"1111111111111111111"},
                {"1_1"},
                {"?11"},
                {"111?1"},
                {"334A"},
                {"ABC"},
                {null},
                {""}
        });
    }

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForStationNameInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidNumInput(numberInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}
