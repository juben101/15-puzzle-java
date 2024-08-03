import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorSelector extends JFrame implements ActionListener {

    private JButton backgroundButton;

    private JButton fontButton; 

    private FifteenPuzzle game; 

    public ColorSelector(FifteenPuzzle game) {

        super("Color Selector"); 

        this.game = game; 

        setSize(300, 200); 
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1)); 

        backgroundButton = new JButton("Select Background Color");
        backgroundButton.addActionListener(this);

        fontButton = new JButton("Select Font Color");
        fontButton.addActionListener(this);

        add(backgroundButton);

        add(fontButton);

    }

    // Action performed when a button is clicked
    public void actionPerformed(ActionEvent e) {

        JButton buttonClicked = (JButton) e.getSource(); // Get the clicked button

        Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.WHITE); // Show color chooser dialog

        if (selectedColor != null) {

            if (buttonClicked == backgroundButton) {

                for (int i = 0; i < game.board.length; i++) {

                    for (int j = 0; j < game.board[i].length; j++) {
                        game.board[i][j].setBackground(selectedColor);
                    }

                }

            } 

            else if (buttonClicked == fontButton) {
                
                for (int i = 0; i < game.board.length; i++) {

                    for (int j = 0; j < game.board[i].length; j++) {
                        game.board[i][j].setForeground(selectedColor);
                    }

                }
                
            }
        }
    }
}
