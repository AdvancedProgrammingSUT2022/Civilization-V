package View.GraphicViewController;

import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.ProfileMenuController;
import Model.Enums.Menus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {

    @FXML
    Label userName;
    @FXML
    ImageView profilePic;
    @FXML
    Label nickNameError;
    @FXML
    Label PasswordError;
    @FXML
    TextField oldPass;
    @FXML
    TextField newPass;
    @FXML
    TextField newNickname;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText(LoginAndRegisterController.getInstance().getLoggedInUser().getUsername());
        //profilePic.setImage(LoginAndRegisterController.getInstance().getLoggedInUser().getProfilePic());
    }

    public void changeNickName(MouseEvent mouseEvent) {
        nickNameError.setText(ProfileMenuController.getInstance().changeNickname(newNickname.getText()));
        nickNameError.setVisible(true);
    }

    public void changePass(MouseEvent mouseEvent) {
        PasswordError.setText(ProfileMenuController.getInstance().changeCurrentPassword(oldPass.getText(),newPass.getText()));
        PasswordError.setVisible(true);
    }
}
