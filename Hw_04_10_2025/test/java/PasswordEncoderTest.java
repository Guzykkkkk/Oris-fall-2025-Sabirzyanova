import org.example.FourthOctober.service.Impl.SimpleHashPasswordEncoder;
import org.example.FourthOctober.service.PasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {
    @Test
    void testEncodeMakesPasswordDifferent() {
        // Arrange (готовим данные)
        SimpleHashPasswordEncoder encoder = new SimpleHashPasswordEncoder();
        String originalPassword = "mySecretPassword123";

        // Act (выполняем действие)
        String encodedPassword = encoder.encode(originalPassword);

        // Assert (проверяем результат)
        // Пароль должен измениться после шифрования
        assertNotEquals(originalPassword, encodedPassword,
                "Зашифрованный пароль должен отличаться от оригинального");
    }

    @Test
    void testSamePasswordGivesSameHash() {
        SimpleHashPasswordEncoder encoder = new SimpleHashPasswordEncoder();
        String password = "somePassword";
        String hash1 = encoder.encode(password);
        String hash2 = encoder.encode(password);
        assertEquals(hash1, hash2,
                "passwords have a same hash code");
    }
    @Test
    void testCanCheckPassword() {
        SimpleHashPasswordEncoder encoder = new SimpleHashPasswordEncoder();
        String userPassword = "user1";
        String storedHash = encoder.encode(userPassword);
        boolean shouldWork = encoder.matches(userPassword, storedHash);
        assertTrue(shouldWork,
                "correct password");
        boolean shouldFail = encoder.matches("wrongPassword", storedHash);
        assertFalse(shouldFail,
                "incorrect password");
    }
    @Test
    void testInterfaceExists() {
        PasswordEncoder encoder = new PasswordEncoder() {
            public String encode(String rawPassword) { return "hash"; }
            public boolean matches(String rawPassword, String hashPassword) { return true; }
        };
        assertNotNull(encoder);
        assertEquals("hash", encoder.encode("test"));
        assertTrue(encoder.matches("a", "b"));
    }
}
