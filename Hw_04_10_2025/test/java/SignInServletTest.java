import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.servlets.SignInServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SignInServletTest {
    private SignInServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private AuthService authService;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new SignInServlet();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        authService = mock(AuthService.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("authService")).thenReturn(authService);

        servlet.init(servletConfig);
    }

    @Test
    void testShowsLoginPageForUnauthorizedUser() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(null);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/jsp/sign-in.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRedirectsToProfileIfAlreadyLoggedIn() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("user@test.com");
        servlet.doGet(request, response);
        verify(response).sendRedirect("/profile");
    }
}
