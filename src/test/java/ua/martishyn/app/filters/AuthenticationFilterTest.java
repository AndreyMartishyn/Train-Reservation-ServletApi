package ua.martishyn.app.filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.filters.AuthenticationFilter;
import ua.martishyn.app.data.entities.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthenticationFilterTest {

    @Mock
    HttpServletRequest mockedRequest;

    @Mock
    HttpServletResponse mockedResponse;

    @Mock
    HttpSession mockedSession;

    @Mock
    FilterChain filterChain;

    private AuthenticationFilter authenticationFilter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationFilter = new AuthenticationFilter();
    }

    @Test
    public void shouldRedirectToMainPageWhenOnlyContextPathInURI() throws ServletException, IOException {
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");

        authenticationFilter.doFilter(mockedRequest, mockedResponse, filterChain);
        verify(mockedResponse, times(1)).sendRedirect(mockedRequest.getContextPath() + "/index.command");
    }

    @Test
    public void shouldProceedWithRequestWhenURIContainsPermittedResource() throws ServletException, IOException {
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/search-tickets.command");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");

        authenticationFilter.doFilter(mockedRequest, mockedResponse, filterChain);
    }

    @Test
    public void shouldReturnToLoginPageWhenUserIsNullAndProhibitedResource() throws ServletException, IOException {
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/users-page.command");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");
        when(mockedSession.getAttribute("name")).thenReturn(null);
        when(mockedRequest.getSession()).thenReturn(mockedSession);

        authenticationFilter.doFilter(mockedRequest, mockedResponse, filterChain);
        verify(mockedResponse, times(1)).sendRedirect(mockedRequest.getContextPath() + "/login-page.command");
    }
}
