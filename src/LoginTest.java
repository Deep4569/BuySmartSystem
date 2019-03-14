import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginTest extends TestCase {

    Login login = new Login();

    public LoginTest() throws FileNotFoundException {
    }

    public void testCheckLogin() throws IOException {
        assertTrue(login.checkLogin("username", "password"));
        assertFalse(login.checkLogin("error", "error"));
    }
}