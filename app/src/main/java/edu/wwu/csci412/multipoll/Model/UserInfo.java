
package edu.wwu.csci412.multipoll.Model;
import java.util.List;
public class UserInfo {

    String UserId;
    String Username;
    String email;
    String password;
    //List<String> FriendIds;

    public UserInfo(){

    }
    public UserInfo(String id, String name, String email, String pass) {
        this.UserId = id;
        this.Username = name;
        this.email = email;
        this.password = pass;
        //List<String> FriendIds;
    }

    public void changeName(String newName){
        Username = newName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

