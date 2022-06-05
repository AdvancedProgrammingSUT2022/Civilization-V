package View.GraphicViewController;

import Controller.PreGameController.LoginAndRegisterController;
import Model.Enums.Menus;
import Model.User.User;
import View.Menu.Menu;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoardPageController implements Initializable {
    public VBox list;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        Text text1 = new Text("Top 6 Players:");
        text1.setStyle("-fx-font-family: 'Cooper Black' ; -fx-fill: #86033b; -fx-font-size: 60;");
        list.getChildren().add(text1);
        for (User user: LoginAndRegisterController.getInstance().getUsers()) {
            Text text = new Text(i+"_ " + user.getUsername() + "    __   Score: " + user.getScore());
            list.getChildren().add(text);
            text.setStyle("-fx-font-family: 'Cooper Black' ; -fx-fill: #4b0862; -fx-font-size: 50;");
            if(i<4)text.setStyle("-fx-font-family: 'Cooper Black' ; -fx-fill: #8a7939; -fx-font-size: 50;");
            i++;
            if(i == 11)break;
        }
    }

    public void buttonSizeIncrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 25;-fx-background-color: rgba(231,231,121,0.83);");
    }

    public void buttonSizeDecrease(MouseEvent mouseEvent) {
        Button button =(Button) mouseEvent.getSource();
        button.setStyle("-fx-font-size: 18; -fx-background-color: rgba(220, 90, 6, 0.76);");
    }

    public void back(){
        main.java.Main.changeMenu(Menus.MAIN_MENU.value);
    }
}
