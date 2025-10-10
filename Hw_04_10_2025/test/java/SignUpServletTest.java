import org.example.FourthOctober.servlets.SignUpServlet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignUpServletTest {
    @Test
    void testSignUpServlet() {
        SignUpServlet servlet = new SignUpServlet();
        assertNotNull(servlet);
    }
}
