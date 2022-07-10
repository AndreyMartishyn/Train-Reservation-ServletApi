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
public class StationNameValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter
    public String stationNameInput;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}")
    public static List<Object[]> stationNameInput() {
        return Arrays.asList(new Object[][]{
                {"Station1111"},
                {"Станція "},
                {"Simple-Station1"},
                {"Такая%собі1Станція"},
                {"Станція-нація1"},
                {"Кам'янець-Подільский1"},
                {"1111"},
                {""},
                {null}
        });
    }

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForStationNameInputParametrized() {
        Assert.assertThat(dataInputValidator.isValidStationNameInput(stationNameInput), is(false));
    }

    @After
    public void tearDown(){
        dataInputValidator = null;
    }
}

