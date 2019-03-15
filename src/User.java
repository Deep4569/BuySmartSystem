import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    FileReader in;
    FileWriter out;

    String currentUser;

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

    public boolean registerUser(String username, String pass, String address, String phone) throws IOException {
        read.mark(1024000);
        match = pattern.matcher(username);
        if(match.find()) {

            while ((line = read.readLine()) != null) {
                String[] split = line.split(":");
                if(split[0].equals(username)){
                    read.reset();
                    return false;
                }
            }
            String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());
            try {
                write.write(username + ":" + encodedPass + ":" + address + ":" + phone.replaceAll("^[()-]+$",""));
                write.newLine();
                write.close();
                read.reset();
                currentUser = username;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentUser = username;
        return false;
    }

    public boolean checkPassword(String p1, String p2){ return p1.equals(p2); }

    public String getUsername(){
        return currentUser;
    }

    public String getPassword() throws IOException {
        String[] data = getData(currentUser);
        return data != null ? new String(Base64.getDecoder().decode(data[1])) : null;
    }

    public String getAddress() throws IOException {
        String[] data = getData(currentUser);
        return data != null ? data[2] : null;
    }

    public String getPhone() throws IOException{
        String[] data = getData(currentUser);
        return data != null ? data[3] : null;
    }

    public void setUsername(String data) throws IOException {
        changeData(0, data);
    }

    public void setPassword(String data) throws IOException {
        changeData(1, data);
    }

    public void setAddress(String data) throws IOException {
        changeData(2, data);
    }

    public void setPhone(String data) throws IOException {
        changeData(3, data);
    }

    public void changeData(int val, String data) throws IOException {
        read.mark(1024000);
        ArrayList<String> list = new ArrayList<>();
        while ((line = read.readLine()) != null) {
            String[] split = line.split(":");
            if(split[0].equals(currentUser)){
                if(val == 0){ line = line.replaceAll(currentUser, data); currentUser = data;}
                else if(val == 1) line = line.replaceAll(split[val], Base64.getEncoder().encodeToString(data.getBytes()));
                else line = line.replaceAll(split[val], data);

            }
            list.add(line);
        }
        BufferedWriter x = new BufferedWriter(new FileWriter("users.txt", false));
        for(String s : list){
            x.write(s);
            x.newLine();
            x.flush();
        }
        x.close();
        read.reset();
    }

    public String[] getData(String user) throws IOException {
        in = new FileReader("users.txt");
        read = new BufferedReader(in);
        read.mark(1024000);
        while ((line = read.readLine()) != null) {
            String[] split = line.split(":");
            if(split[0].equals(user)){
                read.reset();
                return split;
            }
        }
        read.reset();
        return null;
    }

}
