import ca.cmpt213.a2.model.*;
import ca.cmpt213.a2.ui.Menu;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        Menu menu = new Menu(gameBoard);
        menu.show();
    }
}
