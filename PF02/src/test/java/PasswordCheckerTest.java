import org.junit.jupiter.api.BeforeEach;
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

    @ParameterizedTest //EC01
    @ValueSource(strings = {"Abcde123"})
    void validPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Abcd1234",
            "Abcdef123",
            "Abcdef12345",
            "Abcdef123456"
    })
    void validLengthPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC02 && EC03
    @ValueSource(strings = {
            "Abcde12",
            "Abcdef1234560"
    })
    void wrongLengthPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_LENGTH, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Abcdefgh1"
    })
    void validDigitsPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC04
    @ValueSource(strings = {
            "Abcdefghi"
    })
    void wrongDigitsPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_DIGITS, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Abc1234567"
    })
    void validUppersPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC05
    @ValueSource(strings = {
            "abcdef1234"
    })
    void wrongUppersPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_UPPERS, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aBC123456"
    })
    void validLowersPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC06
    @ValueSource(strings = {
            "ABCDE12345"
    })
    void wrongLowersPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_LOWERS, result);
    }

    @ParameterizedTest //EC07 && EC08
    @ValueSource(strings = {
            "",
            " "
    })
    void blankPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.ERR_BLANK, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Abcdef1234"
    })
    void notBlankPassword(String password) {
        int result = checker.check(password, password);
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC09
    @ValueSource(strings = {
            "Abcdef123"
    })
    void validMatchPassword(String password) {
        int result = checker.check(password, "Abcdef123");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @ParameterizedTest //EC10
    @ValueSource(strings = {
            "Abcdef1234",
            "AbcdE123",
    })
    void wrongMatchPassword(String password) {
        int result = checker.check(password, "Abcde123");
        assertEquals(PasswordChecker.ERR_NO_MATCH, result);
    }

    @ParameterizedTest //ET01 && ET02
    @ValueSource(ints = {
            -1,
            0
    })
    void invalidMinLength(int minLength) {
        assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(minLength);
        });
    }

    @ParameterizedTest //ET03
    @ValueSource(ints = {
            7,
    })
    void invalidMaxLength(int maxLength) {
        assertThrows(IllegalArgumentException.class, () -> {
            checker.setMaxLength(maxLength);
        });
    }

    @ParameterizedTest //ET04
    @ValueSource(ints = {
            8,
            10
    })
    void settingMinAboveMaxThrowsException(int maxLength) {
        checker.setMaxLength(maxLength);
        assertThrows(IllegalArgumentException.class, () -> {
            checker.setMinLength(9);
        });
    }
}
