import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordCheckerTest {

    PasswordChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
        checker.setCheckDigits(true);
        checker.setCheckLowers(true);
        checker.setCheckUppers(true);
        checker.setMinLength(8);
        checker.setMaxLength(12);
    }

    @Test
    void validPassword() {
        int result = checker.check("Abcde123", "Abcde123");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcde12", "Abcde123", "Abcdef123", "Abcdef12345", "Abcde123456",
    "Abcdef1234560"})
    void wrongLengthPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_LENGTH, result);
    }
}
