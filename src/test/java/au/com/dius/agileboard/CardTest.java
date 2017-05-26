package au.com.dius.agileboard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void shouldThrowExceptionWhenEstimateIsLessThanOne() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Estimate cannot be less than 1");

    new Card("card-title", "card-description", 0);
  }

  @Test
  public void shouldCreateCard() {
    Card card = new Card("card-title", "card-description", 1);

    assertThat("Card title is incorrect", card.getTitle(), is("card-title"));
    assertThat("Card description is incorrect", card.getDescription(), is("card-description"));
    assertThat("Card estimate is incorrect", card.getEstimate(), is(1));
  }

  @Test
  public void shouldSerializeCardToStringWithUnassignedColumn() {
    Card card = new Card("card-title", "card-description", 1);

    String stringValueOfCard = card.toString();

    assertThat("toString is not returning correct value", stringValueOfCard, is("Card{title='card-title', " +
        "description='card-description', estimate=1, column=Unassigned}"));
  }

  @Test
  public void shouldSerializeCardToStringWithAssignedColumn() {
    Card card = new Card("card-title", "card-description", 1);
    card.setColumn(new Column("test-column"));

    String stringValueOfCard = card.toString();

    assertThat("toString is not returning correct value", stringValueOfCard, is("Card{title='card-title', " +
        "description='card-description', estimate=1, column=test-column}"));
  }
}