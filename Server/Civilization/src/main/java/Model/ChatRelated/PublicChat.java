package Model.ChatRelated;

import Model.User.User;

import java.util.ArrayList;

public class PublicChat extends Chat{
    public PublicChat(String chat_name, ArrayList<String> users_names) {
        super(chat_name, users_names);
        this.setChat_name("CIVILIZATION GLOBAL CHAT");
    }
}
