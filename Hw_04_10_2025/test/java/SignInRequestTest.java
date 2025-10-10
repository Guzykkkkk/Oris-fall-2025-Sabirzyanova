import org.example.FourthOctober.dto.requests.SignInRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInRequestTest {
    @Test
    void testSignInRequestCreation() {
        SignInRequest request = SignInRequest.builder()
                .email("user@mail.com")
                .password("password123")
                .build();
        assertEquals("user@mail.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }
}
