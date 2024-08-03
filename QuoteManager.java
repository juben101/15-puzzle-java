import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

final class QuoteManager {

    private JLabel quoteLabel; 
    private List<String> quotes; 
    private int currentIndex; 

    private void initializeQuotes() {

        quotes.add("\"Obstacles are those frightful things you see when you take your eyes off your goal.\" - Henry Ford");

        quotes.add("\"The only way to achieve the impossible is to believe it is possible.\" - Charles Kingsleigh (Alice in Wonderland)");

        quotes.add("\"Believe you can and you're halfway there.\" - Theodore Roosevelt");

        quotes.add("\"The only way to do great work is to love what you do.\" - Steve Jobs");

        quotes.add("\"Success is not final, failure is not fatal: It is the courage to continue that counts.\" - Winston Churchill");

        quotes.add("\"The only limit to our realization of tomorrow will be our doubts of today.\" - Franklin D. Roosevelt");

        quotes.add("\"Don't watch the clock; do what it does. Keep going.\" - Sam Levenson");

        quotes.add("\"You are never too old to set another goal or to dream a new dream.\" - C.S. Lewis");

        quotes.add("\"The future belongs to those who believe in the beauty of their dreams.\" - Eleanor Roosevelt");

        quotes.add("\"It does not matter how slowly you go as long as you do not stop.\" - Confucius");

        quotes.add("\"You miss 100% of the shots you don't take.\" - Wayne Gretzky");

    }

    public QuoteManager() {

        quoteLabel = new JLabel(); 
        
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        
        quoteLabel.setFont(new Font("Arial", Font.BOLD, 13)); 

        quotes = new ArrayList<>(); 

        initializeQuotes(); 

        currentIndex = 0; 
    }

    public void displayNextQuote() {

        if (currentIndex >= quotes.size()) {
            currentIndex = 0; 
        }

        String quote = quotes.get(currentIndex); 
        quoteLabel.setText(quote); 
        currentIndex++; 

    }

    
    public JLabel getQuoteLabel() {
        return quoteLabel;
    }
}
