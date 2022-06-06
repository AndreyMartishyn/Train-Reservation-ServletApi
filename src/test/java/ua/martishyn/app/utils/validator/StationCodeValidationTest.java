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
public class StationCodeValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String stationCodeInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> stationCodeInput() {
        return Arrays.asList(new Object[][]{
                {"1111"},
                {"AAAA"},
                {"Station"},
                {"@#$%@"},
                {"A-AA"},
                {"Станція-1"},
                {"Станці9-Не-Підходить"},
                {"Ето-не-станція"},
                {""},
                {null}
        });
    }

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForStationCodeInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidStationCodeInput(stationCodeInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}

