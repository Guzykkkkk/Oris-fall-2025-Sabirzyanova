import org.example.FourthOctober.servlets.ProfileServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileServletTest {
    @Test
    void testProfileServlet() {
        ProfileServlet servlet = new ProfileServlet();
        assertNotNull(servlet);
    }
}
