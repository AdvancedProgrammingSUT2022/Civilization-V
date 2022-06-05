package Model.User;

import View.Images;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class User {
    private String username;
    private  String password;
    private String nickname;
    private int score ;
    private int profPicIndex;
    
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
}
