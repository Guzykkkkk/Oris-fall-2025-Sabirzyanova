import org.example.FourthOctober.dto.FieldErrorDto;
import org.example.FourthOctober.dto.response.AuthResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResponseTest {
    @Test
    void testSuccessResponse() {
        AuthResponse response = AuthResponse.builder()
                .success(true)
                .build();

        assertTrue(response.isSuccess());
        assertNull(response.getErrors());
    }

    @Test
    void testErrorResponse() {
        List<FieldErrorDto> errors = List.of(
                new FieldErrorDto("email", "Incorrect")
        );
        AuthResponse response = AuthResponse.builder()
                .success(false)
                .errors(errors)
                .build();

        assertFalse(response.isSuccess());
        assertEquals(1, response.getErrors().size());
    }
}