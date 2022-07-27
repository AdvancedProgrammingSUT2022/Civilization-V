package View.GraphicViewController;

import Controller.NetworkController;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.SavingDataController.DataSaver;
import Model.Enums.Menus;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.RequestType;
import Model.NetworkRelated.Response;
import Model.User.User;
import View.Images;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ScoreBoardPageController implements Initializable {
    public VBox list;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        Text text1 = new Text("Top 10 Players:");
        text1.setStyle("-fx-font-family: 'Cooper Black' ; -fx-fill: #86033b; -fx-font-size: 60;");
        list.getChildren().add(text1);
        Comparator<User> comparator = Comparator.comparing(User::getScore);
        DataSaver.getInstance().updateUsers();
        LoginAndRegisterController.getInstance().getUsers().sort(comparator);
        for (User user: LoginAndRegisterController.getInstance().getUsers()) {
            Label text = new Label("\n"+user.getUsername() + "\t\t\t\t" + user.getScore());
            text.setStyle("-fx-font-size: 20; -fx-font-family: Garamond;");
            ImageView imageView;
            if(i == 1){
                imageView = creatingImageView("/images/scoreBoard/firstPlace.png", 80, 80);
            } else if (i == 2){
                imageView = creatingImageView("/images/scoreBoard/secondPlace.png", 80, 80);
            } else if (i == 3){
                imageView = creatingImageView("/images/scoreBoard/thirdPlace.png", 80, 80);
            } else {
                imageView = creatingImageView("/images/scoreBoard/"+i+".png", 80, 80);
            }
            Label isOnline;
            Label lastSeen = null;
            if(user.getOnline()){
                isOnline = new Label("\n\t\t\tonline");
            } else {
                isOnline = new Label("\n\t\t\toffline");
                lastSeen = new Label("\n\t\t\t"+user.getLastSeen());
                lastSeen.setStyle("fx-font-size: 20; -fx-font-family: Garamond;");
            }
            isOnline.setStyle("-fx-font-size: 20; -fx-font-family: Garamond;");
            String profIndex = String.valueOf(user.getProfPicIndex() + 1);
            ImageView u_imageView = creatingImageView("/images/profiles/"+profIndex+".png", 60, 60);
            HBox u_hBox = new HBox(imageView, u_imageView, new Separator(), text, new Separator(), isOnline, new Separator());
            Button friendshipButton = null;
            if(!user.getUsername().equals(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername()) &&
                    !LoginAndRegisterController.getInstance().getLoggedInUser().getFriendsName().contains(user.getUsername())) {
                friendshipButton = creatingButton("add " + user.getUsername() + " to friends");
                Button finalFriendshipButton = friendshipButton;
                friendshipButton.setOnMouseClicked(mouseEvent -> {
                    finalFriendshipButton.setDisable(true);
                    ArrayList<String> params = new ArrayList<>();
                    params.add(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
                    params.add(user.getUsername());
                    Request request = new Request(RequestType.Friendship, params);
                    request.setAction("request for friendship");
                    NetworkController.getInstance().send(request);
                });
            }
            if(lastSeen != null) {
                u_hBox.getChildren().add(lastSeen);
                u_hBox.getChildren().add(new Separator());
            }
            if(friendshipButton != null ) u_hBox.getChildren().add(friendshipButton);
            if(LoginAndRegisterController.getInstance().getLoggedInUser().getFriendsName().contains(user.getUsername())){
                Label label = new Label("\n\t"+user.getUsername()+" is one of your friends");
                label.setStyle("-fx-fill: #86033b; -fx-font-size: 18;");
                u_hBox.getChildren().add(label);
            }
            text.setAlignment(Pos.CENTER_RIGHT);
            list.getChildren().add(u_hBox);
            list.getChildren().add(new Separator());
            i++;
            if(i == 11) break;
        }
    }
    private javafx.scene.image.ImageView creatingImageView(String address, int FitWidth, int FitHeight){
        Image image = new Image(address);
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        if(FitWidth != 0) imageView.setFitWidth(FitWidth);
        if(FitHeight != 0) imageView.setFitHeight(FitHeight);
        return imageView;
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 25;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }

    public void back(){
        main.java.Main.changeMenu(Menus.MAIN_MENU.value);
    }

    private Button creatingButton(String name){
        Button button = new Button(name);
        button.setStyle("-fx-font-family: Britannic Bold;" + " -fx-background-radius: 20;" +
                " -fx-background-color: rgba(201, 238, 221, 0.7);" + " -fx-font-size: 18; "
                + "-fx-text-fill: #4f4e4e;");
        return button;
    }
}
