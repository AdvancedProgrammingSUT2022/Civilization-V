package View.GraphicViewController;

import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.MainMenuController;
import Model.Enums.Menus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField nickname;
    @FXML private PasswordField password;
    @FXML private Label error;

    @FXML
    Label menuName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuName.setText(LoginAndRegisterController.getInstance().showCurrentMenu());
    }
    
    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 25;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(201, 238, 221, 0.7);");
    }

    public void login(MouseEvent mouseEvent) {
        error.setText(LoginAndRegisterController.getInstance().login(username.getText(),password.getText()));
        error.setVisible(true);
        if(error.getText().equals("user logged in successfully!"))main.java.Main.changeMenu(Menus.MAIN_MENU.value);
    }

    public void register(MouseEvent mouseEvent) {
        error.setText(LoginAndRegisterController.getInstance().register(username.getText(),password.getText(),nickname.getText()));
        error.setVisible(true);
    }
}
