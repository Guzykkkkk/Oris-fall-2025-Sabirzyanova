import org.example.FourthOctober.servlets.CookieServlet;
import org.example.FourthOctober.servlets.LogoutServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CookieServletTest {
    @Test
    void testServletCreation() {
        CookieServlet servlet = new CookieServlet();
        assertNotNull(servlet);
    }
}

