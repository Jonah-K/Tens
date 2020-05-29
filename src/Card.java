public class Card {

    //INSTANCE VARIABLES
    private String suit;

    private String rank;

    private int pointValue;


    //CONSTRUCTOR
    public Card(String cardRank, String cardSuit, int cardPointValue) {
        this.rank = cardRank;
        this.suit = cardSuit;
        this.pointValue = cardPointValue;
    }


    //GETTERS
    public String suit() {
        return this.suit;
    }

    public String rank() {
        return this.rank;
    }

    public int pointValue() {
        return this.pointValue;
    }


    //CHECKS IF TWO CARDS ARE EQUAL
    public boolean matches(Card otherCard) {
        return (otherCard.suit().equals(suit()) && otherCard
                .rank().equals(rank()) && otherCard
                .pointValue() == pointValue());
    }

    //PRINTS OUT THE POINT, SUIT AND RANK
    public String toString() {
        return this.rank + " of " + this.suit + " (point value = " + this.pointValue + ")";
    }
}
