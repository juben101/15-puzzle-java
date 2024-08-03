import javax.swing.*;

public class HelpSystem {
    
    public void showTutorial() {
        
        String tutorial = "Welcome to the Fifteen Puzzle game!\n\n" +
                
        "The objective of the game is to arrange the tiles in numerical order " +
        
        "by sliding them into the empty space.\n\n" +
        
        "To move a tile, simply click on it. You can only move tiles that are adjacent " +
        
        "to the empty space.\n\n" +
        
        "You can use the 'Undo Move' button to undo your last move if needed.\n\n" +
        
        "Enjoy the game!";

        JOptionPane.showMessageDialog(null, tutorial, "Fifteen Puzzle - Tutorial", JOptionPane.INFORMATION_MESSAGE);
    }
}
