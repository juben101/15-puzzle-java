import java.awt.*;
import javax.swing.border.*;

public class RoundedBorder implements Border {
    
    private int radius; 

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        Graphics2D g2d = (Graphics2D) g.create(); 
    
        g2d.setColor(Color.black);

        int borderThickness = radius / 2; 
    
        int adjustedWidth = width - borderThickness * 2;

        int adjustedHeight = height - borderThickness * 2;
    
        int arcDiameter = radius;
    
        g2d.setStroke(new BasicStroke(borderThickness)); 
        
        g2d.drawRoundRect(x + borderThickness / 2, y + borderThickness / 2, adjustedWidth, adjustedHeight, arcDiameter, arcDiameter);
    
        g2d.dispose(); 
    }
}
