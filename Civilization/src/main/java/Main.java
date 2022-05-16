import Controller.SavingDataController.UserDataController;
import View.PreGameView.LoginMenuView;

public class Main {
    public static void main(String[] args){
        LoginMenuView loginMenuView = new LoginMenuView();
        UserDataController.getInstance().loadUsers();
        loginMenuView.run();
        UserDataController.getInstance().saveUsers();
    }
}
/*
user create --username nima --password nimo --nickname enigma
user create --username sero --password hart --nickname serr
user create --username arash --password aa --nickname mio
user login --username nima --password nimo
menu enter Main_Menu
play game --player1 nima --player2 sero
print map
select combat unit --y 1 --x 1
select civil unit --y 1 --x 1
unit found city athens
select city --y 1 --x 1
move unit to --y 1 --x 1
unit attack --y 1 --x 1
city attack --y 1 --x 1
*/
//gamePlay commands
//print map
//next turn
//get player name