import org.example.FourthOctober.model.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {
    @Test
    void testUserEntityCreation() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("test@test.com")
                .password("hash007")
                .nickname("Test User")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("hash1007", user.getPassword());
        assertEquals("Test User", user.getNickname());
    }

    @Test
    void testUserEntitySetters() {
        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setEmail("setter@test.com");
        user.setPassword("newspass");
        user.setNickname("Setter");

        assertEquals(2L, user.getId());
        assertEquals("setter@test.com", user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        UserEntity user1 = UserEntity.builder().id(1L).email("a@b.com").build();
        UserEntity user2 = UserEntity.builder().id(1L).email("a@b.com").build();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
