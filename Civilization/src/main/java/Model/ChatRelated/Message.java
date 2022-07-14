package Model.ChatRelated;

import Model.User.User;

public class Message {
    private User sender_user;
//    private User getter_user;
    private String text;
    private String send_time;

    public Message(User sender_user, String text, String send_time) {
        this.sender_user = sender_user;
        this.text = text;
        this.send_time = send_time;
    }


    public User getSender_user() {
        return sender_user;
    }

    public void setSender_user(User sender_user) {
        this.sender_user = sender_user;
    }
//
//    public User getGetter_name() {
//        return getter_user;
//    }
//
//    public void setGetter_name(User getter_user) {
//        this.getter_user = getter_user;
//    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSend_time() {
        return send_time;
    }
}
