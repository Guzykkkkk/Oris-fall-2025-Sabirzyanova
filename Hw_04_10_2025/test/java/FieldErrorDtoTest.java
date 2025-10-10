import org.example.FourthOctober.dto.FieldErrorDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldErrorDtoTest {
    @Test
    void testCreateFieldErrorDto() {
    FieldErrorDto error = new FieldErrorDto("email", "Email is required");
    assertEquals("email",error.getField());
    assertEquals("Email is required", error.getMessage());
}

@Test
void testBuilderWorks() {
    FieldErrorDto error = FieldErrorDto.builder()
            .field("password")
            .message("short")
            .build();

    assertEquals("password", error.getField());
    assertEquals("short", error.getMessage());
}

@Test
void testNoArgsConstructor() {
    FieldErrorDto error = new FieldErrorDto();
    assertNotNull(error);
}

@Test
void testAllArgsConstructor() {
    FieldErrorDto error = new FieldErrorDto("nickname", "Required");
    assertEquals("nickname", error.getField());
}
}
