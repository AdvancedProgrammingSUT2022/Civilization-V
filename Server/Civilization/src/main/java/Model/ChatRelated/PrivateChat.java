package Model.ChatRelated;

import Model.User.User;

import java.util.ArrayList;

public class PrivateChat extends Chat{
    public PrivateChat(String chat_name, ArrayList<String> users_names) {
        super(chat_name, users_names);
    }

    @Override
    public void setChat_name(String chat_name) {
        super.setChat_name(chat_name);
    }
}
