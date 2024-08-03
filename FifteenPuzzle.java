import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class FifteenPuzzle implements ActionListener {

    private final int DIM = 4; // For N * N board DIM = N
    private final int SIZE = DIM * DIM; // Total number of cells in the board
    private int emptyCell = DIM * DIM; // Initial empty cell in the board
    JButton[][] board = new JButton[DIM][DIM];
    private JFrame frame;
    private JPanel panel = new JPanel();
    private ScoreTracker scoreTracker;
    private TimeTracker timeTracker;
    private UndoMoves undoMoves;
    private boolean tutorialShown = false;
    private SoundEffects soundEffects;
    private ColorSelector colorSelector;
    private QuoteManager quoteManager;
    private JPanel quotePanel;
    private Timer timer; 

    // Array to store winning configuration
    public String[] WIN = new String[SIZE - 1];
    public String[][] GOAL = new String[DIM][DIM]; // Define the goal configuration

    public boolean soundEffectsEnabled = true;

    public void toggleSoundEffects() {
        soundEffectsEnabled = !soundEffectsEnabled;
    }

    public String getSoundMenuItemText() {
        return soundEffectsEnabled ? "Switch off toggle sound" : "Switch on toggle sound";
    }

    public void createRestartButton() {

        RestartButton restartButton = new RestartButton(this);
        frame.add(restartButton, BorderLayout.WEST);

    }

    public void createTimerPanel(JLabel timerLabel) {

        frame.add(timerLabel, BorderLayout.SOUTH);

        // Initialize and start the timer
        timer = new Timer(1000, e -> {
            timeTracker.updateTimerLabel();

        });

        timer.start();
    }

    public void createUndoButton() {

        JButton undoButton = new JButton("Undo Move");

        undoButton.addActionListener(e -> {

            String[][] lastMove = undoMoves.undoMove();

            if (lastMove != null) {
                restoreBoardState(lastMove);
                scoreTracker.updateScore(-1);

            }
        });

        frame.add(undoButton, BorderLayout.EAST);
    }

    public void restoreBoardState(String[][] state) {

        if (state != null && board != null) {

            for (int i = 0; i < DIM; i++) {

                for (int j = 0; j < DIM; j++) {

                    if (state[i][j] != null && board[i][j] != null) {
                        board[i][j].setText(state[i][j]);

                        if (state[i][j].equals("0")) {
                            emptyCell = i * DIM + j;
                            board[i][j].setVisible(false);

                        } else {

                            board[i][j].setVisible(true);

                        }
                    }
                }
            }
        }
    }

    public boolean isSolvable(ArrayList<Integer> list) {

        int inversionSum = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 0) {
                inversionSum += ((i / DIM) + 1);
                continue;
            }

            int count = 0;

            for (int j = i + 1; j < list.size(); j++) {

                if (list.get(j) == 0) {
                    continue;
                } else if (list.get(i) > list.get(j)) {
                    count++;
                }
            }

            inversionSum += count;

        }

        return ((inversionSum & 1) == 0);
    }

    public int getIndex(int i, int j) {
        return ((i * DIM) + j);
    }

    public int indexOf(String cellNum) {

        for (int ROW = 0; ROW < board.length; ROW++) {

            for (int COL = 0; COL < board[ROW].length; COL++) {

                if (board[ROW][COL] != null && board[ROW][COL].getText().equals(cellNum)) {
                    return (getIndex(ROW, COL));
                }
            }
        }
        return -1;
    }

    public boolean makeMove(int row, int col) {

        final int emptyRow = emptyCell / DIM;
        final int emptyCol = emptyCell % DIM;
        int rowDiff = emptyRow - row;
        int colDiff = emptyCol - col;
        boolean isInRow = (row == emptyRow);
        boolean isInCol = (col == emptyCol);
        boolean isNotDiagonal = (isInRow || isInCol);

        if (isNotDiagonal) {

            int diff;

            if (isInRow && colDiff != 0) {
                diff = Math.abs(colDiff);
                int step = colDiff / Math.abs(colDiff);
                
                for (int i = 0; i < diff; i++) {
                    board[emptyRow][emptyCol - step * i].setText(
                            board[emptyRow][emptyCol - step * (i + 1)].getText());
                }

            } else if (isInCol && rowDiff != 0) {

                diff = Math.abs(rowDiff);
                int step = rowDiff / Math.abs(rowDiff);
                
                for (int i = 0; i < diff; i++) {
                    board[emptyRow - step * i][emptyCol].setText(
                            board[emptyRow - step * (i + 1)][emptyCol].getText());
                }
            }

            board[emptyRow][emptyCol].setVisible(true);
            board[row][col].setText("0");
            board[row][col].setVisible(false);
            emptyCell = getIndex(row, col);

            return true;
        }
        return false;
    }

    public boolean isFinished() {

        if (board == null || GOAL == null) {
            return false;
        }

        if (board != null && GOAL != null) {

            for (int ROW = 0; ROW < board.length; ROW++) {

                for (int COL = 0; COL < board[ROW].length; COL++) {

                    if (board[ROW][COL] == null || !board[ROW][COL].getText().equals(GOAL[ROW][COL])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent event) {

        JButton buttonPressed = (JButton) event.getSource();
        int index = indexOf(buttonPressed.getText());

        if (index == -1) {
            throw new IllegalArgumentException("Index should be between 0-15");
        }

        int row = index / DIM;
        int col = index % DIM;

        if (soundEffectsEnabled) {
            soundEffects.playMoveSound();
        }

        // Save current board state for undo functionality
        String[][] currentState = new String[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            
            for (int j = 0; j < DIM; j++) {
                currentState[i][j] = board[i][j].getText();
            }

        }
        
        
        undoMoves.addMove(currentState);


        // Make the move and update score if successful
        if (makeMove(row, col)) {

            scoreTracker.updateScore(1);
        }

        // Check if the game is finished
        if (isFinished()) {

            try {
                // Stop the timer if the game is finished
                timer.stop();

            } catch (Exception e) {

                e.printStackTrace(); // Print the stack trace to diagnose any exceptions

            }

            // Calculate elapsed time
            long elapsedTimeMillis = System.currentTimeMillis() - timeTracker.getStartTime();
            long minutes = (elapsedTimeMillis / 1000) / 60;
            long seconds = (elapsedTimeMillis / 1000) % 60;
            int movesTaken = scoreTracker.getMoves();

            soundEffects.playCompletionSound();

            JOptionPane.showMessageDialog(null, "Congratulations! You won the game in " + minutes + " min " + seconds + " sec with " + movesTaken + " moves.", "Victory!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    // Method to initialize the game board
    public void initializeBoard(int boardSize) {

        frame = new JFrame("15 PUZZLE GAME");
        frame.setLocation(450, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(DIM, DIM));
        panel.setBackground(Color.GRAY);

        ArrayList<Integer> initialList;

        // Generate a solvable initial board configuration
        do {
            initialList = new ArrayList<>(SIZE);
            for (int i = 0; i < SIZE; i++) {
                initialList.add(i, i);
            }
            Collections.shuffle(initialList);

        } while (!isSolvable(initialList));

        // Populate the board with buttons
        for (int index = 0; index < SIZE; index++) {
            
            final int ROW = index / DIM;
            final int COL = index % DIM;
            JButton button = new JButton(String.valueOf(initialList.get(index)));
            button.setBackground(Color.yellow);
            button.setForeground(Color.black);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setFont(new Font("Monospaced", Font.BOLD, 40));
            button.setBorder(new RoundedBorder(15));
            button.addActionListener(this);
            panel.add(button);
            board[ROW][COL] = button;

            // Hide the empty cell
            if (initialList.get(index) == 0) {
                emptyCell = index;
                button.setVisible(false);
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void initializeFunctionalities() {

        JLabel movesLabel = new JLabel("Moves: 0");
        JPanel movesQuotePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        movesQuotePanel.add(movesLabel);
        quotePanel.setBorder(BorderFactory.createEmptyBorder(0, 450, 0, 0));
        movesQuotePanel.add(quotePanel);

        JLabel timerLabel = new JLabel();
        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.add(timerLabel, BorderLayout.CENTER);

        // Initialize and start the timer
        scoreTracker = new ScoreTracker(movesLabel);
        timeTracker = new TimeTracker(timerLabel);
        undoMoves = new UndoMoves();

        // Create menu for sound options
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Sound Option");
        JMenuItem toggleSoundMenuItem = new JMenuItem(getSoundMenuItemText());
        
        toggleSoundMenuItem.addActionListener(e -> {

            toggleSoundEffects();
            toggleSoundMenuItem.setText(getSoundMenuItemText());

        });

        optionsMenu.add(toggleSoundMenuItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Update timer label and add components to the frame
        timer = new Timer(1000, e -> {

            timeTracker.updateTimerLabel();

        });
        
        timer.start();

        frame.add(timePanel, BorderLayout.SOUTH);
        frame.add(movesQuotePanel, BorderLayout.NORTH);
        createUndoButton();
        createRestartButton();

    }

    // Method to initialize the game
    public void initializeGame() {
        
        if (!tutorialShown) {

            HelpSystem helpSystem = new HelpSystem();
            helpSystem.showTutorial();
            tutorialShown = true;
        }

        int boardSize = 4;
        initializeBoard(boardSize);
        initializeFunctionalities();
        quoteManager.displayNextQuote();
        colorSelector.setVisible(true);

    }

    // Method to restart the game
    public void restartGame() {

        panel.removeAll();
        frame.getContentPane().removeAll();
        frame.dispose();
        initializeGame();
        quoteManager.displayNextQuote();

    }

    // Constructor to initialize the game components
    public FifteenPuzzle() {

        // Initialize winning configuration
        for (int i = 1; i < SIZE; i++) {
            WIN[i - 1] = Integer.toString(i);
        }

        // Initialize goal configuration
        int counter = 1;

        for (int i = 0; i < DIM; i++) {

            for (int j = 0; j < DIM; j++) {

                if (counter == SIZE) {
                    GOAL[i][j] = "0";
                } else {
                    GOAL[i][j] = Integer.toString(counter++);
                }

            }
        }

        // Initialize quote manager, quote panel, sound effects, and color selector
        quoteManager = new QuoteManager();
        quotePanel = new JPanel(new BorderLayout());
        quotePanel.add(quoteManager.getQuoteLabel());
        soundEffects = new SoundEffects();
        colorSelector = new ColorSelector(this);
    }

    // Main method to start the game
    public static void main(String[] args) {
        
        FifteenPuzzle game = new FifteenPuzzle();
        game.initializeGame();

    }
}
