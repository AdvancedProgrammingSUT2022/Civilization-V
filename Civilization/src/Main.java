
import Controller.GameController.GameController;
import Controller.GameController.Movement;
import Model.Movement.Graph;
import View.PreGameView.LoginMenuView;

public class Main {

    public static void main(String[] args) {
        LoginMenuView loginMenuView = new LoginMenuView();
        loginMenuView.run();
    }
}

//    user create --username nima --password nimo --nickname enigma
//        user create --username sero --password hart --nickname serr
//        user create --username arash --password aa --nickname mio
//        user login --username nima --password nimo
//        menu enter Main_Menu

////3 player mode
//        play game --player1 sero --player2 nima --player3 arash
////2 player mode
//        play game --player1 nima --player2 sero

////gamePlay commands
////print map
////next turn
////get player name