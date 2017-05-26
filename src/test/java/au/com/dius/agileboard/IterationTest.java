package au.com.dius.agileboard;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IterationTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Set<String> columnNames = new HashSet<>();
  private Iteration iteration;

  @Before
  public void setup() {
    columnNames.addAll(asList("Starting", "Done"));
    iteration = new Iteration(columnNames);
  }

  @Test
  public void shouldSetupIterationWithColumns() {
    Set<Column> columns = iteration.getColumns();

    assertThat("Iteration should contain two columns", columns.size(), is(2));
    assertTrue("Columns should contain Starting", columns.contains(new Column("Starting")));
    assertTrue("Columns should contain Done", columns.contains(new Column("Done")));
  }

  @Test
  public void shouldAddCardToStartingColumn() {
    Card card = new Card("title", "description", 5);

    iteration.add(card);

    assertThat("", iteration.getColumn("Starting").getCards(), is(singletonList(card)));
    assertThat("", card.getColumn(), is(new Column("Starting")));
  }

  @Test
  public void shouldReturnColumnBasedOnName() {
    Column column = iteration.getColumn("Done");

    assertThat("au.com.dius.agileboard.Column is incorrect", column.getName(), is("Done"));
  }

  @Test
  public void shouldThrowExceptionWhenInvalidColumnNameSpecified() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("invalidColumn Not found");

    iteration.getColumn("invalidColumn");
  }

  @Test
  public void shouldCalculateVelocityForAnIteration() {
    Column doneColumn = iteration.getColumn("Done");
    Card firstCard = new Card("title", "description", 3);
    Card secondCard = new Card("title", "description", 5);
    Card thirdCard = new Card("title", "description", 13);
    doneColumn.addCard(firstCard);
    doneColumn.addCard(secondCard);
    doneColumn.addCard(thirdCard);

    int velocity = iteration.velocity();

    assertThat("Velocity is incorrect", velocity, is(21));
  }

  @Test
  public void shouldMoveCardToTargetColumn() {
    Card card = new Card("title", "description", 5);
    Column sourceColumn = iteration.getColumn("Starting");
    Column destinationColumn = iteration.getColumn("Done");
    sourceColumn.addCard(card);

    iteration.moveCard(card, "Done");

    assertTrue("Card moved incorrectly", destinationColumn.getCards().contains(card));
    assertFalse("Card moved incorrectly", sourceColumn.getCards().contains(card));
    assertThat("card column is incorrect", card.getColumn(), is(destinationColumn));
  }

  @Test
  public void shouldUndoLastMove() {

    Card card = new Card("title", "description", 3);
    Column sourceColumn = iteration.getColumn("Starting");
    Column destinationColumn = iteration.getColumn("Done");
    sourceColumn.addCard(card);

    iteration.moveCard(card, "Done");

    iteration.undoLastMove();

    assertTrue("Card moved incorrectly", sourceColumn.getCards().contains(card));
    assertFalse("Card moved incorrectly", destinationColumn.getCards().contains(card));
    assertThat("card column is incorrect", card.getColumn(), is(sourceColumn));
  }
}