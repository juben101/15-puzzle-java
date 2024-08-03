import java.awt.event.*;
import javax.swing.*;

public class RestartButton extends JButton implements ActionListener {

    private FifteenPuzzle game; 

    public RestartButton(FifteenPuzzle game) {
        super("Restart"); 
        this.game = game; 
        addActionListener(this); 
    }

    // ActionListener implementation to handle button click events
    public void actionPerformed(ActionEvent e) {
        game.restartGame(); 
    }
}
