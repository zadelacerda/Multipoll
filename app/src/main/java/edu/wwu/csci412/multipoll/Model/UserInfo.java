
package edu.wwu.csci412.multipoll.Model;
import java.util.List;
public class UserInfo {

    String UserId;
    String Username;
    String email;
    String password;
    List<String> FriendIds;

    public UserInfo(String id, String name, String email, String pass) {
        this.UserId = id;
        this.Username = name;
        this.email = email;
        this.password = pass;
        List<String> FriendIds;
    }

    public void changeName(String newName){
        Username = newName;
    }

    public void newFriend(String newFriend){
        FriendIds.add(newFriend);
    }
}

