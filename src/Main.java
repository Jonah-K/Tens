import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Main extends JFrame implements WindowListener, ActionListener, MouseListener {

    //INSTANCE VARIALBES
    JPanel pan = new JPanel(new GridLayout(3, 5));

    Button b1;

    Button b2;

    Button b3;

    Deck d;

    ArrayList<String> cardsArr = new ArrayList<>();

    ArrayList<JLabel> boardImages = new ArrayList<>();

    ArrayList<Card> cardsOnBoard = new ArrayList<>();

    ArrayList<Card> selectedCards = new ArrayList<>();


    int gamesPlayed = 0;

    private Icon Icon = new ImageIcon(getClass().getResource("3ofAll.gif"));

    int gamesWon = 0;



    //MAIN METHOD
    public static void main(String[] args) throws IOException {
        Main myWindow = new Main("Tens");
        myWindow.setSize(910, 900);
        myWindow.setVisible(true);
    }

    //CONSTRCUTOR
    public Main(String title) throws IOException {
        super(title);
        JOptionPane.showMessageDialog(null, "Welcome to Tens. In this game, your goal is to remove all cards from the board. In order to do this,\nclick 2 cards that add up to 10 or 4 cards of the same face card or 4 ten cards. After you've clicked\nthe cards you want then click the remove button to check if they can be gotten rid of. If you lose or just\nwant to start over, just click the restart button. Good Luck", "Welcome", -1, this.Icon);
        createDeck();
        System.out.println(this.d);
        setUpPhotos();
        addToPanel();
        checkLost();
        setLayout(new FlowLayout());
        addWindowListener(this);
        this.b1 = new Button("Remove");
        this.b3 = new Button("Exit");
        this.b2 = new Button("Restart");
        add(this.pan);
        add(this.b1);
        add(this.b2);
        add(this.b3);
        this.b1.addMouseListener(this);
        this.b2.addMouseListener(this);
        this.b3.addMouseListener(this);
        getContentPane().setBackground(Color.ORANGE);
    }


    //CREATES A STANDARD 52 CARD DECK
    public void createDeck() {
        String[] ranks = {
                "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "jack", "queen", "king" };
        String[] suits = { "spades", "hearts", "diamonds", "clubs" };
        int[] points = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                0, 0, 0 };
        this.d = new Deck(ranks, suits, points);
    }


    //ADDS THE IMAGES OF THE CARD TO THE DECK
    public void addToPanel() throws IOException {
        int i;
        for (i = 0; i < 13; i++) {
            String rank = ((Card)this.cardsOnBoard.get(i)).rank();
            String suit = ((Card)this.cardsOnBoard.get(i)).suit();
            BufferedImage wPic = ImageIO.read(getClass().getResource(rank + suit + ".GIF"));
            JLabel wIcon = new JLabel(new ImageIcon(wPic));
            this.boardImages.add(wIcon);
            this.pan.add(wIcon);
        }
        for (i = 0; i < this.boardImages.size(); i++)
            ((JLabel)this.boardImages.get(i)).addMouseListener(this);
        JLabel text = new JLabel("You've won " + this.gamesWon + " of " + this.gamesPlayed + " games played");
        text.setBackground(Color.GREEN);
        this.pan.add(text);
    }

    //CHECKS IF ANOTHER PLAY IS POSSILBE BASED ON HTE CONDITIONS GIVEN
    public boolean noMorePlays() {
        int foundJack = 0;
        int foundQueen = 0;
        int foundKing = 0;
        int foundTen = 0;
        for (int i = 0; i < 13; i++) {
            for (int j = 1; j < 13; j++) {
                if (((Card)this.cardsOnBoard.get(i)).pointValue() + ((Card)this.cardsOnBoard.get(j)).pointValue() == 10 && !((Card)this.cardsOnBoard.get(i)).matches(this.cardsOnBoard.get(j))) {
                    System.out.println(this.cardsOnBoard.get(i));
                    System.out.println(this.cardsOnBoard.get(j));
                    return false;
                }
                if (((Card)this.cardsOnBoard.get(i)).rank().equals("King"))
                    foundKing++;
                if (((Card)this.cardsOnBoard.get(i)).rank().equals("Queen"))
                    foundQueen++;
                if (((Card)this.cardsOnBoard.get(i)).rank().equals("Jack"))
                    foundJack++;
                if (((Card)this.cardsOnBoard.get(i)).rank().equals("10"))
                    foundTen++;
            }
        }
        if (foundJack == 4 || foundKing == 4 || foundQueen == 4 || foundTen == 4)
            return false;
        return true;
    }


    //CREATES THE STRING MESSAGE FOR EACH CARD, USING THE RANK, SUIT AND A "gif" SUFFIX
    public void setUpPhotos() throws IOException {
        for (int i = 0; i < this.d.size(); i++) {
            String rank = this.d.get(i).rank();
            String suit = this.d.get(i).suit();
            int pointValue = this.d.get(i).pointValue();
            this.cardsArr.add(rank + suit + ".GIF");
            this.cardsOnBoard.add(new Card(rank, suit, pointValue));
        }
    }

    //CHECKS IF ANOTHER PLAY IS POSSIBLE
    public boolean anotherPlayIsPossible() {
        return (containsPairSum10(this.selectedCards) || containsJQKT(this.selectedCards));
    }

    //CHECKS IF THE CARDS SELECTED BY THE PLAYER ARE A JQKT
    private boolean containsJQKT(ArrayList<Card> selectedCards) {
        int foundJack = 0;
        int foundQueen = 0;
        int foundKing = 0;
        int foundTen = 0;
        for (Card kObj : selectedCards) {
            if (kObj.rank().equals("jack")) {
                foundJack++;
                continue;
            }
            if (kObj.rank().equals("queen")) {
                foundQueen++;
                continue;
            }
            if (kObj.rank().equals("king")) {
                foundKing++;
                continue;
            }
            if (kObj.rank().equals("10"))
                foundTen++;
        }
        return (foundJack == 4 || foundKing == 4 || foundQueen == 4 || foundTen == 4);
    }


    //CHECKS IF THE SUM OF ALL CARDS SELECTED IS 10
    private boolean containsPairSum10(ArrayList<Card> selectedCards) {
        for (int sk1 = 0; sk1 < selectedCards.size(); sk1++) {
            int k1 = ((Card)selectedCards.get(sk1)).pointValue();
            for (int sk2 = sk1 + 1; sk2 < selectedCards.size(); sk2++) {
                int k2 = ((Card)selectedCards.get(sk2)).pointValue();
                if (k1 + k2 == 10) {
                    System.out.println("hi");
                    return true;
                }
            }
        }
        return false;
    }

    //CHECK IF THE GAME IS LOST IF NO MORE PLAYS ARE POSSIBLE
    public void checkLost() {
        String[] arrayOfString = { "Yes", "No" };
        if (noMorePlays())
            JOptionPane.showMessageDialog(null, "You Lost, press the restart button to begin again");
    }

    //CHECKS IF THERE ARE NO MORE CARDS ON THE BOARD
    public void checkWin() {
        if (this.cardsOnBoard.size() == 0)
            JOptionPane.showMessageDialog(null, "You Win, press the restart button to begin again");
    }


    //REMOVES A CARD FROM THE PANEL AS LONG AS THE CONDIITONS ARE MET AND REPLACES IT WITH ANOTER
    public void remove() {
        int cnt = 0;
        for (int i = 0; i < this.cardsOnBoard.size(); i++) {
            if (cnt < this.selectedCards.size() && (
                    (Card)this.selectedCards.get(cnt)).matches(this.cardsOnBoard.get(i))) {
                this.selectedCards.remove(cnt);
                System.out.println(i);
                this.boardImages.removeAll(this.boardImages);
                this.cardsOnBoard.remove(i);
                this.pan.removeAll();
                i = 0;
                try {
                    addToPanel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                repaint();
                revalidate();
                System.out.println(this.cardsOnBoard);
            }
        }
        checkLost();
        checkWin();
    }

    //REQUIRED ACTIONLISTENER METHODS
    public void actionPerformed(ActionEvent e) {}

    public void windowClosing(WindowEvent e) {}

    public void windowOpened(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    //CHECKS WHEN THE MOUSE IS PRESSED ON A CARD, IF THE PLAY IS POSSIBLE, REMOVE THE CARD , OTHERWISE TELL THE PLAYER THE MOVE DOESNT WORK
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this.b1) {
            if (anotherPlayIsPossible()) {
                remove();
            } else {
                for (int i = 0; i < this.boardImages.size(); i++)
                    ((JLabel)this.boardImages.get(i)).setVisible(true);
                JOptionPane.showMessageDialog(null, "That's not a possible move, try again");
            }
            System.out.println(this.selectedCards);
            System.out.println(noMorePlays());
        } else if (e.getSource() == this.b2) {
            this.cardsOnBoard.removeAll(this.cardsOnBoard);
            this.selectedCards.removeAll(this.selectedCards);
            this.boardImages.removeAll(this.boardImages);
            this.pan.removeAll();
            revalidate();
            createDeck();
            try {
                setUpPhotos();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.gamesPlayed++;
            try {
                addToPanel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            revalidate();
            System.out.println(this.gamesPlayed);
            System.out.println(this.cardsArr.get(0));
        } else if (e.getSource() == this.b3) {
            System.exit(0);
        }
        for (int k = 0; k < this.boardImages.size(); k++) {
            if (e.getSource().equals(this.boardImages.get(k))) {
                this.selectedCards.add(this.cardsOnBoard.get(k));
                ((JLabel)this.boardImages.get(k)).setVisible(false);
                repaint();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void reveal(MouseEvent m) {}
}
