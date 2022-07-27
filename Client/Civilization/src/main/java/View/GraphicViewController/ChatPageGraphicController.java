package View.GraphicViewController;

import Controller.NetworkController;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.SavingDataController.DataSaver;
import Model.ChatRelated.Chat;
import Model.ChatRelated.Message;
import Model.ChatRelated.PrivateChat;
import Model.ChatRelated.Room;
import Model.Enums.Menus;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.RequestType;
import Model.User.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.stage.Popup;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ChatPageGraphicController {
    public TextArea messageText;
    public TextField searchTextField;
    public ImageView GlobalChat;
    public ImageView groupIcon;
    public ImageView privateIcon;
    public VBox allMessages;
    public HBox search_hBox;
    public VBox allChats;
    public Pane MainPane;
    private ArrayList<String> selected_users_for_group = new ArrayList<>();
    private Chat selectedChat = null;
    public static Pane paneCpy;


    @FXML
    public void initialize() {
        paneCpy = MainPane;
        int index = 0;
        sendRequestForUpdateChats();///////////
        DataSaver.getInstance().updateUsers();
        for(Chat chat : LoginAndRegisterController.getInstance().getLoggedInUser().getChats()){
            addChatToList(index, chat);
            index += 2;
        }
    }
    public void searchForChats(MouseEvent keyEvent) {
        foundUser();
        search_hBox.getChildren().get(0).setOnMouseClicked(mouseEvent ->{
            allChats.getChildren().clear();
            initialize();
            search_hBox.getChildren().remove(0);
        });
    }

    private void foundUser(){
        allChats.getChildren().clear();
        int index = 0;
        for(User user : LoginAndRegisterController.getInstance().getUsers()){
            if(user.getUsername().startsWith(searchTextField.getText()) &&
                    !user.getUsername().equals(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername())){
                userListsChat(index, user);
                index += 2;
            }
        }
    }

    private void userListsChat(int index, User user){
        int proIndex = user.getProfPicIndex() + 1;
        ImageView imageView = creatingImageView("/images/profiles/"+proIndex+".png", 50, 50);
        Label name = new Label(user.getUsername());
        name.getStyleClass().add("name");
        name.getStyleClass().add("chat_style");
        Label time = new Label(user.getUsername()+" is " + (user.getOnline() ? "online:)" : "offline :(" )+"\nif you want to create private chat with "+user.getUsername()+" please click with mouse"
                + "\ncurrent time : "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        Label message_pr = new Label(time.getText());
        message_pr.getStyleClass().add("chat_style");
        VBox vBox = new VBox(name, message_pr);
        vBox.setPrefWidth(540);
        HBox chatPreview = new HBox(imageView, vBox);
        allChats.getChildren().add(chatPreview);
        allChats.getChildren().add(new Separator());
        chatPreview.setOnMouseClicked(mouseEvent -> newPrivateChat(index, user));
    }
    private void newPrivateChat(int index, User user){
        if(!foundChat(user)){
            ArrayList<String> users_name = new ArrayList<>();
            users_name.add(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
            users_name.add(user.getUsername());
            selectedChat = new PrivateChat("", users_name);
            user.addChat(selectedChat);
            LoginAndRegisterController.getInstance().getLoggedInUser().addChat(selectedChat);
            LoadingChatMessages(selectedChat);
            sendRequestForUpdateChats();
        }
        setStylesChat(index);
    }

    private boolean foundChat(User user){
        if(LoginAndRegisterController.getInstance().getLoggedInUser().getChats() != null){
            for(Chat chat : LoginAndRegisterController.getInstance().getLoggedInUser().getChats()){
                if(chat.getUsers_names().contains(user)){
                    selectedChat = chat;
                    LoadingChatMessages(selectedChat);
                    return true;
                }
            }
        } return false;
    }

    private void setStylesChat(int index){
        for(int i = 0; i < allChats.getChildren().size(); i++){
            allChats.getChildren().get(i).setStyle("-fx-background-color: white;");
        } allChats.getChildren().get(index).setStyle("-fx-background-color: green;");
    }

    public void newGroupChat(MouseEvent mouseEvent) {
        groupIcon.setOpacity(0.5);
        // search for users and add to group
        TextField search_users = creatingTextField("search for users", "textFields", 10, 300, 0);
        ImageView imageView_searchChatIcon = creatingImageView("/images/Chat/searchChatIcon.png", 50, 50);
        Button search = creatingButton("search", "buttons", "", 60, 0);
        HBox search_hBox = new HBox(imageView_searchChatIcon, search_users, search);
        // vbox for users
        TextField name = creatingTextField("write group name", "textFields", 10, 300, 0);
        VBox users_vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane(users_vbox);
        Button create_group = creatingButton("create group", "buttons", "#0ae3f2", 0, 0);
        Button cancel_creating = creatingButton("cancel", "buttons", "#c7c5c6", 0, 0);
        HBox buttonsPack = new HBox(create_group, cancel_creating);
        VBox popup_vBox = new VBox(search_hBox, name, scrollPane, buttonsPack);
        popup_vBox.setPrefHeight(400);
        popup_vBox.setPrefWidth(400);
        Popup GroupPopup = new Popup();
        Window window = main.java.Main.scene.getWindow();
        GroupPopup.getContent().add(popup_vBox);
        GroupPopup.setY(window.getHeight() / 3);
        GroupPopup.setX(window.getWidth() / 3);
        GroupPopup.show(window);
        MainPane.setEffect(new Lighting());
        searching(search, users_vbox);
        creatingGroup(GroupPopup, create_group, name.getText());
    }
    private void creatingGroup(Popup GroupPopup, Button create_group, String name){
        StringBuilder nameSB = new StringBuilder();
        nameSB.append(name).append("\n");
        create_group.setOnMouseClicked(mouseEvent1 -> {
            if(!selected_users_for_group.isEmpty()){
                create_group.setDisable(false);
                create_group.setEffect(null);
                for(String username : selected_users_for_group){
                    nameSB.append(username).append(" ");
                    if(GroupPopup.getWidth() == 60){
                        nameSB.append("\n");
                    }
                }
                selected_users_for_group.add(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
                Room room = new Room(nameSB.toString(), selected_users_for_group);
                for(String username : selected_users_for_group){
                    LoginAndRegisterController.getInstance().getUser(username).addChat(room);
                }
                allChats.getChildren().clear();;
                initialize();
                int index = 2*(LoginAndRegisterController.getInstance().getLoggedInUser().getChats().size() - 1);
                selectingChatByChat(index, room);
                MainPane.setEffect(null);
                GroupPopup.hide();
                selected_users_for_group.clear();
                sendRequestForUpdateChats();
            } else {
                create_group.setDisable(true);
                create_group.setEffect(new Lighting());
            }
        });
    }

    private void searching(Button search, VBox users_vbox){
        search.setOnMouseClicked(mouseEvent1 -> {
            users_vbox.getChildren().clear();
            selected_users_for_group.clear();
            int index = 0;
            for(User user : LoginAndRegisterController.getInstance().getUsers()){
                searchUser(index, users_vbox, user);
                index += 2;
            }
        });
    }

    private void searchUser(int index, VBox users_vBox, User user){
        int profileIndex = user.getProfPicIndex()+1;
        ImageView imageView = creatingImageView("/images/profiles/"+profileIndex+".png", 50 ,50);
        Label name = new Label(user.getUsername());
        HBox user_preview = new HBox(imageView, name);
        user_preview.setPrefWidth(350);
        user_preview.setPrefHeight(70);
        users_vBox.getChildren().add(user_preview);
        users_vBox.getChildren().add(new Separator());
        user_preview.setOnMouseClicked(mouseEvent -> {
            if(selected_users_for_group.contains(user.getUsername())){
                users_vBox.getChildren().get(index).setStyle("-fx-background-color: white;");
                selected_users_for_group.remove(user.getUsername());
            } else {
                users_vBox.getChildren().get(index).setStyle("-fx-background-color: green;");
                selected_users_for_group.add(user.getUsername());
            }
        });
    }
    public void goToGlobalChat(MouseEvent mouseEvent) {
        // go to global chat
    }


    public void send(MouseEvent mouseEvent){
        if(selectedChat != null && !messageText.getText().isEmpty()){
            String messageS = messageText.getText();
            VBox message_box = new VBox();
            Label name = new Label(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
            name.getStyleClass().add("name");
            Label message_text = new Label(messageText.getText());
            message_text.getStyleClass().add("message_text");
            message_text.setPrefHeight(50);
            Label message_time = new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")) + "\ndelivered");
            message_time.getStyleClass().add("-fx-label-padding: 10 0 0 240;");
            message_time.getStyleClass().add("-fx-font-size: 10;");
            message_box.getChildren().add(name);
            message_box.getChildren().add(message_text);
            message_box.getChildren().add(message_time);
            message_box.getStyleClass().add("message_box");
            message_box.setMaxWidth(Region.USE_PREF_SIZE);
            message_box.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            VBox.setMargin(message_box, new Insets(10, 0, 0, 0));
            int profPicIndex = LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex() + 1;
            ImageView sender = creatingImageView("images/profiles/" + profPicIndex + ".png", 60, 60);
            HBox hBox = new HBox();
            hBox.getChildren().add(sender);
            hBox.getChildren().add(message_box);
            Button edit = new Button("edit");
            edit.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                    " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                    + "-fx-text-fill: #4f4e4e;");
            Button delete = new Button("delete");
            delete.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                    " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                    + "-fx-text-fill: #4f4e4e;");
            hBox.getChildren().add(edit);
            hBox.getChildren().add(delete);
            edit.setOnMouseClicked(mouseEvent1 -> {
                Popup popup = new Popup();
                Window window = main.java.Main.scene.getWindow();
                TextField textField = new TextField();
                textField.setText(message_text.getText());
                Button enter = new Button("enter");
                enter.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                        " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                        + "-fx-text-fill: #4f4e4e;");
                VBox vBox = new VBox(textField, new Separator(), enter);
                popup.getContent().add(vBox);
                MainPane.setEffect(new Lighting());
                popup.show(window);
                enter.setOnMouseClicked(mouseEvent2 -> {
                    message_text.setText(textField.getText());
                    MainPane.setEffect(null);
                    popup.hide();
                });
            });
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.setStyle("-fx-padding : 0 0 15 0; -fx-background-color: rgba(242, 245, 244, 0.5); -fx-background-radius: 17;");
            allMessages.getChildren().add(hBox);
            Message message = new Message(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername(), messageS, message_time.getText());
            delete.setOnMouseClicked(mouseEvent1 -> {
                Popup popup = new Popup();
                Window window = main.java.Main.scene.getWindow();
                Label question = new Label("Are you sure ?");
                question.setStyle("-fx-font-family: Gramond; -fx-background-radius: 40;");
                Button yes = new Button("Yes");
                yes.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                        " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                        + "-fx-text-fill: #4f4e4e;");
                Button no = new Button("No");
                no.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                        " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                        + "-fx-text-fill: #4f4e4e;");
                HBox hBox1 = new HBox(yes, new Separator(), no);
                VBox vBox = new VBox(question, hBox1);
                popup.getContent().add(vBox);
                MainPane.setEffect(new Lighting());
                popup.show(window);
                yes.setOnMouseClicked(mouseEvent2 -> {
                    selectedChat.getMessages().remove(message);
                    allMessages.getChildren().remove(hBox);
                    MainPane.setEffect(null);
                    popup.hide();
                });
                no.setOnMouseClicked(mouseEvent2 -> {
                    MainPane.setEffect(null);
                    popup.hide();
                });
            });
            selectedChat.addMessages(message);
            sendRequestForUpdateChats();
            messageText.setText("");
        }
    }

    public void backToMain(MouseEvent mouseEvent){
        sendRequestForUpdateChats();
        if(CalledMethods.getInstance().getMethodsName().get(CalledMethods.getInstance().getMethodsName().size()-1).equals("MainPage"))
            main.java.Main.changeMenu(Menus.MAIN_MENU.value);
        else main.java.Main.changeMenu(Menus.GAME_MENU.value);
    }

    public void OpacityGroupIconToDefault(MouseEvent mouseEvent) {
        groupIcon.setOpacity(1);
    }

    public void changeGroupIconOpacity(MouseEvent mouseEvent) {
        groupIcon.setOpacity(0.5);
    }

    private void addChatToList(int index, Chat chat){
        Image image;
        HBox chatPreview = null;
        if(chat instanceof Room){
            image = new Image("/images/Chat/GroupChat.png");
            ImageView imageViewForChat = new ImageView(image);
            Label room_name = new Label(chat.getChat_name());
            chatPreview = new HBox(imageViewForChat, room_name);
            chatPreview.setPrefWidth(670);
        } else {
//            String chatKindOf = "private chat with ";
//            String withWho = null;
//            if(chat.getUsers_names().get(0).getUsername().equals(user.getUsername())) withWho = chat.getUsers_names().get(1).getUsername();
//            else if (chat.getUsers_names().get(1).getUsername().equals(user.getUsername())) withWho = chat.getUsers_names().get(0).getUsername();
//            chat.setChat_name(chatKindOf + withWho);
//            chat.setChat_name(x);
            image = new Image("/images/Chat/privateChat.png");
            ImageView imageViewForChat = new ImageView(image);
            Label another_username = null;
            for(String username : chat.getUsers_names()){
                if(!username.equals(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername())){
                    another_username = new Label(username);
                }
            }
            chatPreview = new HBox(imageViewForChat, another_username);
            chatPreview.setPrefWidth(670);
        }
        allChats.getChildren().add(chatPreview);
        allChats.getChildren().add(new Separator());
        chatPreview.setOnMouseClicked(mouseEvent -> {
            selectingChatByChat(index, chat);
        });
    }
    private void selectingChatByChat(int index, Chat chat){
        selectedChat = chat;
        LoadingChatMessages(chat);
        setStylesChat(index);
    }

    private void LoadingChatMessages(Chat chat){
        allMessages.getChildren().clear();
        if(chat.getMessages() != null){
            for(Message message : chat.getMessages()){
                VBox message_box = new VBox();
                Label name = new Label(message.getSender_user());
                Label message_text = new Label(message.getText());
                message_text.getStyleClass().add("message");
                Label message_time = new Label(message.getSend_time());
                message_time.getStyleClass().add("-fx-label-padding: 10 0 0 240;");
                message_time.getStyleClass().add("-fx-font-size: 10;");
                message_box.getChildren().add(name);
                message_box.getChildren().add(message_text);
                message_box.getChildren().add(message_time);
                message_box.getStyleClass().add("message_box");
                if(!message.getSender_user().equals(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername())) message_box.setStyle("-fx-background-color: gray;");
                message_box.setMaxWidth(Region.USE_PREF_SIZE);
                message_box.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                VBox.setMargin(message_box, new Insets(10, 0, 0, 0));
                int profPicIndex = LoginAndRegisterController.getInstance().getUser(message.getSender_user()).getProfPicIndex() + 1;
                ImageView imageView_sender = creatingImageView("/images/profiles/"+profPicIndex+".png", 50, 50);
                HBox hBox = new HBox();
                hBox.getChildren().add(imageView_sender);
                hBox.getChildren().add(message_box);
                if(message.getSender_user().equals(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername())) {
                    hBox.setAlignment(Pos.BOTTOM_LEFT);
                    Button edit = new Button("edit");
                    edit.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                            " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                            + "-fx-text-fill: #4f4e4e;");
                    Button delete = new Button("delete");
                    delete.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                            " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                            + "-fx-text-fill: #4f4e4e;");
                    hBox.getChildren().add(edit);
                    hBox.getChildren().add(delete);
                    edit.setOnMouseClicked(mouseEvent1 -> {
                        Popup popup = new Popup();
                        Window window = main.java.Main.scene.getWindow();
                        TextField textField = new TextField();
                        textField.setText(message_text.getText());
                        Button enter = new Button("enter");
                        enter.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                                " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                                + "-fx-text-fill: #4f4e4e;");
                        VBox vBox = new VBox(textField, new Separator(), enter);
                        popup.getContent().add(vBox);
                        MainPane.setEffect(new Lighting());
                        popup.show(window);
                        enter.setOnMouseClicked(mouseEvent2 -> {
                            message_text.setText(textField.getText());
                            MainPane.setEffect(null);
                            popup.hide();
                        });
                    });
                    delete.setOnMouseClicked(mouseEvent1 -> {
                        Popup popup = new Popup();
                        Window window = main.java.Main.scene.getWindow();
                        Label question = new Label("Are you sure ?");
                        question.setStyle("-fx-font-family: Gramond; -fx-background-radius: 40;");
                        Button yes = new Button("Yes");
                        yes.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                                " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                                + "-fx-text-fill: #4f4e4e;");
                        Button no = new Button("No");
                        no.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                                " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                                + "-fx-text-fill: #4f4e4e;");
                        HBox hBox1 = new HBox(yes, new Separator(), no);
                        VBox vBox = new VBox(question, hBox1);
                        popup.getContent().add(vBox);
                        MainPane.setEffect(new Lighting());
                        popup.show(window);
                        yes.setOnMouseClicked(mouseEvent2 -> {
                            selectedChat.getMessages().remove(message);
                            allMessages.getChildren().remove(hBox);
                            MainPane.setEffect(null);
                            popup.hide();
                        });
                        no.setOnMouseClicked(mouseEvent2 -> {
                            MainPane.setEffect(null);
                            popup.hide();
                        });
                    });
                }
                else hBox.setAlignment(Pos.BOTTOM_RIGHT);
                hBox.setPrefWidth(200);
                hBox.setPrefHeight(70);
                hBox.setStyle("-fx-padding : 0 0 15 0;");
                allMessages.getChildren().add(hBox);
            }
        }
    }


    private ImageView creatingImageView(String address, int FitWidth, int FitHeight){
        Image image = new Image(address);
        ImageView imageView = new ImageView(image);
        if(FitWidth != 0) imageView.setFitWidth(FitWidth);
        if(FitHeight != 0) imageView.setFitHeight(FitHeight);
        return imageView;
    }

    private TextField creatingTextField(String promptText, String styleClass, int Insets, int preWidth, int preHeight){
        TextField name = new TextField();
        name.setPromptText(promptText);
        if(!styleClass.equals("")) name.setStyle(styleClass);
        if(preWidth != 0) name.setPadding(new Insets(Insets));
        if(preHeight != 0) name.setPrefWidth(preWidth);
        return name;
    }

    private Button creatingButton(String name, String styleClass, String color, int preWidth, int preHeight){
        Button button = new Button(name);
        if(!styleClass.equals("")){
            button.getStyleClass().add(styleClass);
        }
        if(!color.equals("")){
            button.setStyle("-fx-background-color: "+color+";");
        }
        if(preWidth != 0) button.setPrefWidth(preWidth);
        if(preHeight != 0) button.setPrefHeight(preHeight);
        return button;
    }
    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (javafx.scene.control.Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 21;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        javafx.scene.control.Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }
    public void sendRequestForUpdateChats(){
        ArrayList<User> users = LoginAndRegisterController.getInstance().getUsers();
        String json = new Gson().toJson(users);
        ArrayList<String> params = new ArrayList<>();
        params.add(json);
        Request request = new Request(RequestType.ForSavingChats, params);
        NetworkController.getInstance().send(request);
    }
}
