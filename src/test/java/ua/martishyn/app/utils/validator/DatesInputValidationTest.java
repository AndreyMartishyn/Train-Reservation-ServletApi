package ua.martishyn.app.utils.validator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.martishyn.app.data.utils.constants.DateConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(value = Parameterized.class)
public class DatesInputValidationTest {
    private static DataInputValidator dataInputValidator;

    @Parameterized.Parameter(value = 0)
    public String departureDate;
    @Parameterized.Parameter(value = 1)
    public String arrivalDate;

    @Parameterized.Parameters(name = "{index}: testDomain - {0}, {1}")
    public static List<Object[]> nameInput() {
        return Arrays.asList(new Object[][]{
                {"2222-12-22T2222:2222", "2223-12-22T123:123"},
                {"2222-12-22T12:22", "2223-12-22T12:12"},
                {"2222-12-22T12:22", "2222-11-22T12:12"},
                {"2222-11-22T12:22", "2222-12-22T12:12"},
                {"2222-11-22T12:22", "2222-11-22T12:23"},
                {"", ""},
                {null, null}
        });
    }

    @Before
    public void setUp() {
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Test
    public void shouldReturnFalseForDatesPassedParametrized() {
        Assert.assertThat(dataInputValidator.isValidDateInput(departureDate, arrivalDate), is(false));
    }
    @After
    public void tearDown() {
        dataInputValidator = null;
    }
}
