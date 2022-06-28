package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.TicketFormDto;
import ua.martishyn.app.data.service.TicketService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class TicketServiceTest {
    @Mock
    HttpServletRequest mockedRequest;

    TicketService ticketService;

    @Before
    public void setUp() {
        ticketService = new TicketService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateTicketDtoObjectFromPassedParametersFromRequest() {
        when(mockedRequest.getParameter("train")).thenReturn(any());
        when(mockedRequest.getParameter("fromStation")).thenReturn(any());
        when(mockedRequest.getParameter("toStation")).thenReturn(any());
        when(mockedRequest.getParameter("departure")).thenReturn(any());
        when(mockedRequest.getParameter("arrival")).thenReturn(any());
        when(mockedRequest.getParameter("class")).thenReturn(any());
        when(mockedRequest.getParameter("price")).thenReturn(String.valueOf(anyInt()));
        when(mockedRequest.getParameter("duration")).thenReturn(String.valueOf(anyInt()));

        final TicketFormDto ticketFormDto = ticketService.ticketInfoPreFill(mockedRequest);
        Assert.assertNotNull(ticketFormDto);
    }

    @After
    public void tearDown() {
        mockedRequest = null;
        ticketService = null;
    }
}