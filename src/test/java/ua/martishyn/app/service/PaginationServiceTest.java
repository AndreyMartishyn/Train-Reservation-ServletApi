package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.LoginCommand;
import ua.martishyn.app.data.service.PaginationService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class PaginationServiceTest {
    private static final String PAGE_NUMBER = "4";

    private PaginationService paginationService;

    @Mock
    HttpServletRequest mockedRequest;

    private LoginCommand loginCommand;

    @Before
    public void setUp() throws Exception {
        paginationService = new PaginationService();
        loginCommand = new LoginCommand();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnPageNumberWhenParameterPagePassed() {
        when(mockedRequest.getParameter("currentPage")).thenReturn(PAGE_NUMBER);
        Assert.assertEquals(Integer.parseInt("4"), paginationService.getCurrentPage(mockedRequest));
    }

    @Test
    public void shouldReturnFirstPageWhenPageParameterIsNull() {
        when(mockedRequest.getParameter("currentPage")).thenReturn(null);
        Assert.assertEquals(Integer.parseInt("1"), paginationService.getCurrentPage(mockedRequest));
    }

    @Test
    public void shouldReturnZeroBecauseClassIsNotSupportedForExecutionPaginationHelperMethod() {
        Assert.assertEquals(0, paginationService.getNumberOfRows(loginCommand));
    }

    @Test
    public void shouldNotReturnServiceClassWhenCommandNotSupported() {
        String expected = "Not supported";
        Assert.assertEquals(expected, paginationService.getServiceClassBasedOnCommand(loginCommand));
    }

    @After
    public void tearDown() {
        paginationService = null;
        loginCommand = null;
        mockedRequest = null;
    }
}
