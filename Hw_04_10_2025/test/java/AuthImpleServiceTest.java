import org.example.FourthOctober.dto.FieldErrorDto;
import org.example.FourthOctober.dto.requests.SignInRequest;
import org.example.FourthOctober.dto.requests.SignUpRequest;
import org.example.FourthOctober.dto.response.AuthResponse;
import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.AuthDataValidationService;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.Impl.AuthServiceImpl;
import org.example.FourthOctober.service.PasswordEncoder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthImpleServiceTest {
    @Test
    void testSignUpWithInvalidData() {
        UserRepository userRepo = new UserRepository() {
            public Optional<UserEntity> findByEmail(String email) { return Optional.empty(); }
            public void save(UserEntity entity) { }
        };
        AuthDataValidationService validation = new AuthDataValidationService() {
            public List<FieldErrorDto> validateEmail(String email) {
                List<FieldErrorDto> errors = new ArrayList<>();
                if (email == null) {
                    errors.add(new FieldErrorDto("email", "Email is null"));
                }
                return errors;
            }
            public List<FieldErrorDto> validatePassword(String password) { return new ArrayList<>(); }
            public List<FieldErrorDto> validateNickname(String nickname) { return new ArrayList<>(); }
        };
        PasswordEncoder encoder = new PasswordEncoder() {
            public String encode(String rawPassword) { return "encoded"; }
            public boolean matches(String rawPassword, String hashPassword) { return true; }
        };
        AuthServiceImpl authService = new AuthServiceImpl(userRepo, validation, encoder);
        SignUpRequest request = SignUpRequest.builder()
                .email(null)
                .password("pass")
                .nickname("name")
                .build();

        AuthResponse response = authService.signUp(request);

        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        System.out.println("success");
    }
    @Test
    void testInterfaceMethodsExist() {
        AuthService authService = new AuthService() {
            public AuthResponse signUp(SignUpRequest request) {
                return AuthResponse.builder().success(true).build();
            }
            public AuthResponse signIn(SignInRequest request) {
                return AuthResponse.builder().success(true).build();
            }
        };
        SignUpRequest signUp = SignUpRequest.builder().build();
        SignInRequest signIn = SignInRequest.builder().build();
        assertTrue(authService.signUp(signUp).isSuccess());
        assertTrue(authService.signIn(signIn).isSuccess());
    }
    @Test
    void testInterfaceMethods() {
        AuthDataValidationService validator = new AuthDataValidationService() {
            public List<FieldErrorDto> validateEmail(String email) { return List.of(); }
            public List<FieldErrorDto> validatePassword(String password) { return List.of(); }
            public List<FieldErrorDto> validateNickname(String nickname) { return List.of(); }
        };
        assertNotNull(validator.validateEmail("test"));
        assertNotNull(validator.validatePassword("pass"));
        assertNotNull(validator.validateNickname("name"));
    }
}