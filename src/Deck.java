import java.util.ArrayList;
import java.util.List;

public class Deck {

    //INSTANCE VARIALBES
    private List<Card> cards = new ArrayList<>();

    private int size;

    //CONSTRUCTOR
    public Deck(String[] ranks, String[] suits, int[] values) {
        for (int j = 0; j < ranks.length; j++) {
            for (String suitString : suits)
                this.cards.add(new Card(ranks[j], suitString, values[j]));
        }
        this.size = this.cards.size();
        shuffle();
    }


    //GETTERS
    public boolean isEmpty() {
        return (this.size == 0);
    }

    public Card get(int i) {
        return this.cards.get(i);
    }

    public int size() {
        return this.size;
    }


    //method that loops backwards through and shuffles
    public void shuffle() {
        for (int k = this.cards.size() - 1; k > 0; k--) {
            int howMany = k + 1;
            int start = 0;
            int randPos = (int)(Math.random() * howMany) + start;
            Card temp = this.cards.get(k);
            this.cards.set(k, this.cards.get(randPos));
            this.cards.set(randPos, temp);
        }
        this.size = this.cards.size();
    }


    //deals the card
    public Card deal() {
        if (isEmpty())
            return null;
        this.size--;
        Card c = this.cards.get(this.size);
        return c;
    }

    //Turns each deck into a strong with undealt and dealt cards
    public String toString() {
        String rtn = "size = " + this.size + "\nUndealt cards: \n";
        int k;
        for (k = this.size - 1; k >= 0; k--) {
            rtn = rtn + this.cards.get(k);
            if (k != 0)
                rtn = rtn + ", ";
            if ((this.size - k) % 2 == 0)
                rtn = rtn + "\n";
        }
        rtn = rtn + "\nDealt cards: \n";
        for (k = this.cards.size() - 1; k >= this.size; k--) {
            rtn = rtn + this.cards.get(k);
            if (k != this.size)
                rtn = rtn + ", ";
            if ((k - this.cards.size()) % 2 == 0)
                rtn = rtn + "\n";
        }
        rtn = rtn + "\n";
        return rtn;
    }
}
