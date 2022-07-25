package Model.User;

import Model.ChatRelated.Chat;
import com.google.gson.annotations.Expose;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class User {
    @Expose
    private String username;
    @Expose
    private  String password;
    @Expose
    private String nickname;
    @Expose
    private int score ;
    @Expose
    private int profPicIndex;
    @Expose
    private boolean isOnline = false;
    @Expose
    private ArrayList<Chat> chats = new ArrayList<>();
    
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getProfPicIndex() {
        return profPicIndex;
    }

    public void setProfPicIndex(int profPicIndex) {
        this.profPicIndex = profPicIndex;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }
    public void addChat(Chat chat){
        this.chats.add(chat);
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean getOnline(){
        return this.isOnline;
    }
}
