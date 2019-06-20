package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    LoginController LC = new LoginController();

    @Test
    void checkUserinput_NoUsername_ReturnedFalse() {
        boolean result = LC.checkUserinput(null, "password");
        assertEquals(result, false);
    }

    @Test
    void checkUserinput_NoPassword_ReturnedFalse() {
        boolean result = LC.checkUserinput("username", null);
        assertEquals(result, false);
    }

    @Test
    void checkUserinput_UsernameAndPassword_ReturnedTrue() {
        boolean result = LC.checkUserinput("username", "password");
        assertEquals(result, true);
    }
}