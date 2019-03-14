import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

public class Login {

    String username,password, line;
    FileReader in = new FileReader("users.txt");
    BufferedReader read = new BufferedReader(in);

    public Login() throws FileNotFoundException {}

    public boolean checkLogin(String user, String pass) throws IOException {
        read.mark(1024000);
        while((line = read.readLine()) != null){
            String[] data = line.split(":");
            data[1] = new String(Base64.getDecoder().decode(data[1]));
            if(data[0].equals(user) && data[1].equals(pass)){
                username = user;
                password = pass;
                return true;
            }
        }
        read.reset();
        return false;
    }



}
