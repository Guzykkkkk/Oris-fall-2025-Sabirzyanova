import org.example.FourthOctober.dto.requests.SignUpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignUpRequestTest {
    @Test
    void testSignUpRequestCreation() {
        SignUpRequest request = SignUpRequest.builder()
                .email("test@test.com")
                .password("Pass123")
                .nickname("tester")
                .build();

        assertEquals("test@test.com", request.getEmail());
        assertEquals("Pass123", request.getPassword());
        assertEquals("tester", request.getNickname());
    }

    @Test
    void testNoArgsConstructor() {
        SignUpRequest request = new SignUpRequest();
        assertNotNull(request);
    }
}
