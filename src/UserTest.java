import junit.framework.TestCase;

import java.io.IOException;

public class UserTest extends TestCase {
    User user = new User();
    public UserTest() throws IOException {
    }
//
    public void testRegisterUser() throws IOException {
        assertFalse(user.registerUser("testUser","test", "1234 test rd", "123-456-7890"));
    }

    public void testCheckPassword() {
        assertTrue(user.checkPassword("test","test"));
        assertFalse(user.checkPassword("test","yeet"));
    }

    public void testGetUsername() throws IOException {
        user.currentUser = "testUser";
        assertTrue(user.getUsername().equals("testUser"));
        assertFalse(user.getUsername().equals("potato"));
    }

    public void testGetPassword() throws IOException {
        user.currentUser = "testUser";
        assertTrue(user.getPassword().equals("test"));
        assertFalse(user.getPassword().equals("potato"));
    }

    public void testGetAddress() throws IOException {
        user.currentUser = "testUser";
        assertTrue(user.getAddress().equals("1234 test rd"));
        assertFalse(user.getAddress().equals("potato"));
    }

    public void testGetPhone() throws IOException {
        user.currentUser = "testUser";
        assertTrue(user.getPhone().equals("123-456-7890"));
        assertFalse(user.getPhone().equals("123-123-1230"));
    }

    public void testSetUsername() throws IOException {
        user.currentUser = "testUser";
        user.setUsername("deep");
        user.currentUser = "deep";
        assertEquals("deep", user.getUsername());

        user.setUsername("testUser");
    }

    public void testSetPassword() throws IOException {
        user.currentUser = "testUser";
        user.setPassword("test2");
        assertEquals("test2", user.getPassword());

        user.setPassword("test");
    }
//
    public void testSetAddress() throws IOException {
        user.currentUser = "testUser";
        user.setAddress("1 Test dr");

        assertEquals("1 Test dr", user.getAddress());

        user.setAddress("1234 test rd");
    }

    public void testSetPhone() throws IOException {
        user.currentUser = "testUser";
        user.setPhone("111-000-1010");

        assertEquals("111-000-1010", user.getPhone());

        user.setPhone("123-456-7890");
    }

    public void testChangeData() throws IOException {
        user.currentUser = "testUser";

        user.changeData(2,"test st");
        assertEquals("test st", user.getAddress());
        assertFalse(user.getAddress().equals("1234 test rd"));
        user.changeData(2,"1234 test rd");
    }

    public void testGetData() throws IOException {

        user.currentUser = "testUser";

        String[] data = user.getData("testUser");
        String[] testData = {"testUser","dGVzdA==","1234 test rd","123-456-7890"};

        for(int i = 0; i < data.length; i++){
            assertEquals(testData[i],data[i]);
        }

    }
}