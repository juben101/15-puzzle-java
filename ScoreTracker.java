import javax.swing.*;

public class ScoreTracker {

    private int moves; 
    
    private JLabel movesLabel; 

    public ScoreTracker(JLabel movesLabel) {
        this.movesLabel = movesLabel;
        moves = 0; 
        updateScorePanel(); 
    }

    private void updateScorePanel() {
        movesLabel.setText("Moves: " + moves); 
    }

    public void updateScore(int additionalMoves) {
        moves += additionalMoves;
        updateScorePanel(); 
    }

    public int getMoves() {
        return moves;
    }
}
