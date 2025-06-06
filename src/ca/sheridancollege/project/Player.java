/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;
/*
 * @author Omkumar Dalsaniya 04-07-2025
 * @author Archi Ramoliya    04-07-2025
 * @author Rutvi Panchal     04-07-2025
 * @author Nirali Patel      04-07-2025
*/
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card playCard(String cardToPlay) {
        for (Card card : hand) {
            if (card.toString().equals(cardToPlay)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    }
}
