import java.util.ArrayList;


public class UndoMoves {

    private ArrayList<String[][]> moveHistory; 

    public UndoMoves() {
        moveHistory = new ArrayList<>(); 
    }

    public void addMove(String[][] currentState) {
        moveHistory.add(currentState); 
    }

    public String[][] undoMove() {

        if (!moveHistory.isEmpty()) {

            return moveHistory.remove(moveHistory.size() - 1); 
            
        }

        return null; 
    }
}
