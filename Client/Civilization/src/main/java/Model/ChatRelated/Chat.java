package Model.ChatRelated;

import Model.User.User;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Chat {
    @Expose
    private String chat_name;
    @Expose
    private ArrayList<String> users_names;
    @Expose
    private ArrayList<Message> messages = new ArrayList<>();

    public Chat(String chat_name, ArrayList<String> users_names){
        this.chat_name = chat_name;
        this.users_names = users_names;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public ArrayList<String> getUsers_names() {
        return users_names;
    }

    public void setUsers_names(ArrayList<String> users_names) {
        this.users_names = users_names;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessages(Message message){
        this.messages.add(message);
    }
}
