import java.io.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    FileReader in;
    FileWriter out;

    BufferedReader read;
    BufferedWriter write;
    String line;
    Pattern pattern;
    Matcher match;



    public User() throws IOException {
        in = new FileReader("users.txt");
        out = new FileWriter("users.txt",true);
        read = new BufferedReader(in);
        write = new BufferedWriter(out);
        pattern = Pattern.compile("^[A-Za-z0-9]+$");
    }

    public boolean registerUser(String username, String pass) throws IOException {

        System.out.println(username+":"+pass);

        match = pattern.matcher(username);
        if(match.find()) {

            while ((line = read.readLine()) != null) {
                String[] split = line.split(":");
                if(split[0].equals(username)){
                    return false;
                }
            }

            String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());

            try {
                write.write(username + ":" + encodedPass);
                write.newLine();
                write.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean checkPassword(String p1, String p2){ return p1.equals(p2); }






}
