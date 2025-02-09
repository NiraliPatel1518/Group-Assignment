/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

import java.util.Scanner;
import java.util.Random;

/**
 * The class that models your game. You should create a more specific child of this class and instantiate the methods
 * given.
 * @author Omkumar Dalsaniya  02-09-2025
 * @author Archi Ramoliya     02-09-2025
 * @author Rutvi Panchal      02-09-2025
 * @author Nirali Patel       02-09-2025
 */

public class Game {
    private Deck deck;
    private Player[] players;
    private String trumpSuit;
    private int trickWinner;
    private int currentPlayerIndex;

    public Game() {
        deck = new Deck();
        deck.shuffle();
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player();
        }
        dealCards();
        setTrumpSuit();// For simplicity, set trump suit to Hearts
        trickWinner = 0;
        currentPlayerIndex = 0;
    }

    private void dealCards() {
        for (int i = 0; i < 13; i++) {
            for (Player player : players) {
                player.addCard(deck.dealCard());
            }
        }
    }

     public void setTrumpSuit() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        Random random = new Random();
        this.trumpSuit = suits[random.nextInt(suits.length)];
        System.out.println("Trump suit set to: " + trumpSuit);
    }

    private boolean isValidPlay(Card card, Card firstCard) {
        if (firstCard == null) {
            return true; // Any card can be played on the first move of a trick
        }
        if (card.getSuit().equals(firstCard.getSuit())) {
            return true; // Following suit
        }
        if (card.getSuit().equals(trumpSuit)) {
            return true; // Playing a trump card
        }
        return false; // Not following suit and not playing a trump card
    }

    private int determineTrickWinner(Card[] trickCards) {
        Card firstCard = trickCards[0];
        Card winningCard = firstCard;
        int winningIndex = 0;

        for (int i = 1; i < trickCards.length; i++) {
            Card card = trickCards[i];
            if (card.getSuit().equals(trumpSuit) && !winningCard.getSuit().equals(trumpSuit)) {
                winningCard = card;
                winningIndex = i;
            } else if (card.getSuit().equals(firstCard.getSuit()) && card.getRank().compareTo(winningCard.getRank()) > 0) {
                winningCard = card;
                winningIndex = i;
            }
        }

        return winningIndex;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        Card[] trickCards = new Card[4];

        while (true) {
            Player currentPlayer = players[currentPlayerIndex];
            Card firstCard = trickCards[trickWinner];
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn. Hand: " + currentPlayer.getHand());
            System.out.print("Enter the card to play: ");
            String cardToPlay = scanner.nextLine();
            Card playedCard = currentPlayer.playCard(cardToPlay);

            if (playedCard == null) {
                System.out.println("Invalid card. Try again.");
                continue;
            }

            if (!isValidPlay(playedCard, firstCard)) {
                System.out.println("Invalid play. You must follow suit or play a trump card.");
                currentPlayer.addCard(playedCard); // Return the card to the player's hand
                continue;
            }

            trickCards[currentPlayerIndex] = playedCard;
            System.out.println("Player " + (currentPlayerIndex + 1) + " played: " + playedCard);

            // Check if the trick is complete
            if (currentPlayerIndex == 3) {
                trickWinner = determineTrickWinner(trickCards);
                System.out.println("Player " + (trickWinner + 1) + " wins the trick!");
                // Clear the trick cards
                for (int i = 0; i < 4; i++) {
                    trickCards[i] = null;
                }
            }

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % 4;

            // Check if the game is over (all players have played all their cards)
            if (currentPlayer.getHand().isEmpty()) {
                break;
            }
        }

        System.out.println("Game over!");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}