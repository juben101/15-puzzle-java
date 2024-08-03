import javax.swing.*;

public class TimeTracker {

    private long startTime; 
    private JLabel timerLabel; 

    public TimeTracker(JLabel timerLabel) {
        this.timerLabel = timerLabel; 
        startTime = System.currentTimeMillis(); 
    }

    public void updateTimerLabel() {
        long elapsedTimeMillis = System.currentTimeMillis() - startTime; 
        long minutes = (elapsedTimeMillis / 1000) / 60; 
        long seconds = (elapsedTimeMillis / 1000) % 60; 
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); 
    }

    
    public void restartTimer() {
        startTime = System.currentTimeMillis(); 
    }

    public long getStartTime() {
        return startTime; 
    }
}
