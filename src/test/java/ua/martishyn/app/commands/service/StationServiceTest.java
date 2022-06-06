package ua.martishyn.app.commands.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.constants.StationServiceConstants;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.http.HttpServletRequest;

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
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, StationServiceConstants.STATION_NAME_INVALID_MESS);

    }

    @Test
    public void shouldReturnFalseWithInvalidStationCodeInputWhenCreateStation() {
        when(mockedRequest.getParameter("name")).thenReturn("Київ-Пас");
        when(mockedRequest.getParameter("code")).thenReturn("123");

        stationService.isStationDataValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, StationServiceConstants.STATION_CODE_INVALID_MESS);

    }

    @After
    public void tearDown(){
        stationService = null;
    }
}
