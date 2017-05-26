import au.com.dius.agileboard.Board;
import au.com.dius.agileboard.Card;
import au.com.dius.agileboard.Iteration;

import java.util.HashSet;

import static java.util.Arrays.asList;

public class Demo {

  public static void main(String[] args) {
    HashSet<String> columnNames = new HashSet<>();
    columnNames.addAll(asList("Starting", "InProgress", "Done"));

    System.out.println("----- Creating new Board -----");
    Board board = new Board(columnNames);

    Iteration iteration = board.getIteration();

    System.out.println("New Board created. Current iteration has following columns :");
    iteration.getColumns().forEach(column -> System.out.println(column.getName()));

    System.out.println(System.lineSeparator() + "----- Adding cards to current iteration -----");

    Card firstCard = new Card("Setup deployment pipeline",
        "Configure jenkins job to build and deploy application", 3);
    Card secondCard = new Card("Spike MySQL for storage",
        "Evaluate MySQL for storage compared to Oracle", 8);
    Card thirdCard = new Card("lorem ipsum", "lorem ipsum", 8);

    iteration.add(firstCard);
    iteration.add(secondCard);
    iteration.add(thirdCard);

    System.out.println(System.lineSeparator() + "Added " + firstCard);
    System.out.println(System.lineSeparator() + "Added " + secondCard);
    System.out.println(System.lineSeparator() + "Added " + thirdCard);

    System.out.println(System.lineSeparator() + "----- Moving first and third card to Done. " +
        "Moving second card to InProgress -----");

    iteration.moveCard(firstCard, "Done");
    iteration.moveCard(secondCard, "InProgress");
    iteration.moveCard(thirdCard, "Done");
    System.out.println(System.lineSeparator() + "Current velocity is " + iteration.velocity());

    System.out.println(System.lineSeparator() + "----- Undo last move. " +
        "Third card should get moved to Starting Column from Done Column. -----");
    iteration.undoLastMove();
    System.out.println(System.lineSeparator() + "Third card moved back to " + thirdCard.getColumn().getName() +
        " Column and velocity got dropped to " + iteration.velocity());

    System.out.println(System.lineSeparator() + "----- Setting work in progress limit to 13 for " +
        "InProgress Column. -----");
    iteration.getColumn("InProgress").setWorkInProgressLimit(13);

    System.out.println(System.lineSeparator() + "----- Moving third card to InProgress Column. " +
        "It will throw exception as it is beyond the column limit -----");
    iteration.moveCard(thirdCard, "InProgress");
  }
}