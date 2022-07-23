package View.GraphicViewController;

import Controller.NetworkController;
import Controller.PreGameController.LoginAndRegisterController;
import Controller.PreGameController.ProfileMenuController;
import Model.Enums.Menus;
import Model.NetworkRelated.Request;
import Model.NetworkRelated.RequestType;
import Model.NetworkRelated.Response;
import View.Images;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
    FileChooser fileChooser = new FileChooser();

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
        profilePic.setImage(Images.profilePics.pics.get(LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex()));
        fileChooser.setInitialDirectory(new File("/"));
    }

    public void changeNickName(MouseEvent mouseEvent) {
        ArrayList<String> params = new ArrayList<>(){{
            add(newNickname.getText());
        }};
        Response response = NetworkController.getInstance().send(new Request(RequestType.ChangeNickname,params));
        nickNameError.setText(response.getMessage());
        ProfileMenuController.getInstance().changeNickname(newNickname.getText());
        nickNameError.setVisible(true);
    }

    public void changePass(MouseEvent mouseEvent) {
        ArrayList<String> params = new ArrayList<>(){{
            add(oldPass.getText());
            add(newPass.getText());
        }};
        Response response = NetworkController.getInstance().send(new Request(RequestType.ChangePassword,params));
        PasswordError.setText(response.getMessage());
        ProfileMenuController.getInstance().changeCurrentPassword(oldPass.getText(),newPass.getText());
        PasswordError.setVisible(true);
    }

    public void nextProf(MouseEvent mouseEvent) {
        ArrayList<String> param = new ArrayList<>();
        param.add(Integer.toString(Images.profilePics.pics.size()));
        NetworkController.getInstance().send(new Request(RequestType.NextProfilePic,param));
        int index = LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex();
        if(index + 1 == Images.profilePics.pics.size())index = -1;
        profilePic.setImage(Images.profilePics.pics.get(index + 1));
        LoginAndRegisterController.getInstance().getLoggedInUser().setProfPicIndex(index + 1);
    }

    public void previousProf(MouseEvent mouseEvent) {
        ArrayList<String> param = new ArrayList<>();
        param.add(Integer.toString(Images.profilePics.pics.size()));
        NetworkController.getInstance().send(new Request(RequestType.PrevProfilePic,param));
        int index = LoginAndRegisterController.getInstance().getLoggedInUser().getProfPicIndex();
        if(index - 1 == -1)index = Images.profilePics.pics.size() ;
        profilePic.setImage(Images.profilePics.pics.get(index - 1));
        LoginAndRegisterController.getInstance().getLoggedInUser().setProfPicIndex(index -1);
    }

    public void choosePic(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(file.getPath());
        profilePic.setImage(image);
        Images.profilePics.pics.add(image);
        ArrayList<String> param = new ArrayList<>();
        param.add(Integer.toString(Images.profilePics.pics.indexOf(image)));
        NetworkController.getInstance().send(new Request(RequestType.ChoosePic,param));
        LoginAndRegisterController.getInstance().getLoggedInUser().setProfPicIndex(Images.profilePics.pics.indexOf(image));
    }
}
