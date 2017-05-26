package au.com.dius.agileboard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;

public class BoardTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Set<String> columnNames = new HashSet<>();

  @Test
  public void shouldThrowNotSetNumberOfColumnsLessThanTwo() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Minimum number of columns should be two.");

    columnNames.add("Start");

    new Board(columnNames);
  }

  @Test
  public void shouldThrowExceptionIfStartingAndDoneColumnAreNotSpecified() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Must have 'Starting' and 'Done' columns");

    columnNames.addAll(asList("Starting", "InProgress"));

    new Board(columnNames);
  }

  @Test
  public void shouldCreateBoardWhenValidColumnsAreSpecified() {
    columnNames.addAll(asList("Starting", "Done"));

    Board board = new Board(columnNames);

    assertNotNull("Board should not be null", board);
    assertNotNull("Iteration should not be null", board.getIteration());
  }
}