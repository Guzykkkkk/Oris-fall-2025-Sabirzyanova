import org.example.FourthOctober.servlets.SessionServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionServletTest {
    @Test
    void testSessionServlet() {
        SessionServlet servlet = new SessionServlet();
        assertNotNull(servlet);
    }
}
