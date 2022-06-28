package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.constants.StationServiceConstants;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StationServiceTest {

    @Mock
    HttpServletRequest mockedRequest;

    @InjectMocks
    StationService stationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnFalseWithInvalidStationNameInputWhenCreateStation() {
        when(mockedRequest.getParameter("name")).thenReturn("Kyiv-#$%&4sdg");

        stationService.isStationDataValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongName", true);

    }

    @Test
    public void shouldReturnFalseWithInvalidStationCodeInputWhenCreateStation() {
        when(mockedRequest.getParameter("name")).thenReturn("Київ-Пас");
        when(mockedRequest.getParameter("code")).thenReturn("123");

        stationService.isStationDataValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongCode", true);
    }

    @Test
    public void shouldReturnTrueWithValidInputWhenCreateOrEditStation() {
        when(mockedRequest.getParameter("name")).thenReturn("Київ-Пас");
        when(mockedRequest.getParameter("code")).thenReturn("КПР");

        Assert.assertThat(stationService.isStationDataValid(mockedRequest), is(true));
    }

    @Test
    public void shouldReturnNewStationFromRequestWhenCreateStation() {
        when(mockedRequest.getParameter("name")).thenReturn("Київ-Пас");
        when(mockedRequest.getParameter("code")).thenReturn("КПР");

        Station stationForCreate = stationService.getStationForCreate(mockedRequest);
        Assert.assertEquals("Київ-Пас", stationForCreate.getName());
        Assert.assertEquals("КПР", stationForCreate.getCode());
    }

    @Test
    public void shouldReturnStationFromRequestWhenUpdateStation() {
        when(mockedRequest.getParameter("id")).thenReturn("1");
        when(mockedRequest.getParameter("name")).thenReturn("Київ-Пас");
        when(mockedRequest.getParameter("code")).thenReturn("КПР");

        Station stationForUpdate = stationService.getStationForUpdate(mockedRequest);
        Assert.assertEquals(1, stationForUpdate.getId());
        Assert.assertEquals("Київ-Пас", stationForUpdate.getName());
        Assert.assertEquals("КПР", stationForUpdate.getCode());
    }

    @After
    public void tearDown(){
        stationService = null;
    }
}
