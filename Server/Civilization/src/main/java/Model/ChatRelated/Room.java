package Model.ChatRelated;

import Model.User.User;

import java.util.ArrayList;

public class Room extends Chat{ // group chat
    public Room(String chat_name, ArrayList<String> users_names) {
        super(chat_name, users_names);
    }
}
