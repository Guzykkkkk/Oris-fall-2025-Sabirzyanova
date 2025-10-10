import org.example.FourthOctober.servlets.LogoutServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LogoutServletTest {
    @Test
    void testServletExists() {
        LogoutServlet servlet = new LogoutServlet();
        assertNotNull(servlet);
    }
}