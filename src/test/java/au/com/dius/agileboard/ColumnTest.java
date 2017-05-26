package au.com.dius.agileboard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ColumnTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void shouldCreateColumnWithGivenName() {
    Column column = new Column("Starting");

    assertThat("au.com.dius.agileboard.Column name should be starting", column.getName(), is("Starting"));
  }

  @Test
  public void shouldSetWorkInProgressLimit() {
    Column column = new Column("InProgress");
    column.setWorkInProgressLimit(21);

    assertThat("work in progress limit is wrong", column.getWorkInProgressLimit(), is(21));
  }

  @Test
  public void shouldNotAddCardWhenWorkInProgressLimitIsReached() {
    expectedException.expect(WorkInProgressLimitExceededException.class);
    expectedException.expectMessage("Adding this card will breach the work in progress limit [limit=8]");

    Column column = new Column("InProgress");
    column.setWorkInProgressLimit(8);
    Card firstCard = new Card("Title", "Description", 5);
    Card secondCard = new Card("Title", "Description", 8);

    column.addCard(firstCard);
    column.addCard(secondCard);
  }

  @Test
  public void shouldReturnTrueWhenTwoColumnsAreEqual() {
    Column firstColumn = new Column("Starting");
    Column secondColumn = new Column("Starting");

    boolean isEqual = firstColumn.equals(secondColumn);

    assertTrue(isEqual);
  }

  @Test
  public void shouldReturnFalseWhenSecondColumnIsNull() {
    Column column = new Column("InProgress");

    boolean isEqual = column.equals(null);

    assertFalse(isEqual);
  }

  @Test
  public void shouldReturnFalseWhenBothTheObjectsAreOfDifferentClass() {
    Column column = new Column("InProgress");
    Card card = new Card("card-title", "card-description", 1);

    boolean isEqual = column.equals(card);

    assertFalse(isEqual);
  }

  @Test
  public void shouldReturnFalseWhenColumnNamesAreDifferent() {
    Column firstColumn = new Column("Starting");
    Column secondColumn = new Column("Done");

    boolean isEqual = firstColumn.equals(secondColumn);

    assertFalse(isEqual);
  }

  @Test
  public void shouldReturnFalseWhenColumnNameIsNull() {
    Column firstColumn = new Column(null);
    Column secondColumn = new Column("Done");

    boolean isEqual = firstColumn.equals(secondColumn);

    assertFalse(isEqual);
  }

  @Test
  public void shouldReturnTrueWhenBothTheColumnNamesAreNull() {
    Column firstColumn = new Column(null);
    Column secondColumn = new Column(null);

    boolean isEqual = firstColumn.equals(secondColumn);

    assertTrue(isEqual);
  }

  @Test
  public void shouldReturnHashCode() {
    Column column = new Column("Starting");

    int hashCode = column.hashCode();

    assertThat("hascode is incorrect", hashCode, is(1381450848));
  }

  @Test
  public void shouldReturn0AsHashCodeWhenColumnNameIsNull() {
    Column column = new Column(null);

    int hashCode = column.hashCode();

    assertThat("hascode is incorrect", hashCode, is(0));
  }

  @Test
  public void shouldSerializeCardToString() {
    Column column = new Column("Starting");
    column.setWorkInProgressLimit(8);

    String stringValueOfColumn = column.toString();

    assertThat("toString is not returning correct value", stringValueOfColumn,
        is("Column{name='Starting', workInProgressLimit=8}"));
  }
}