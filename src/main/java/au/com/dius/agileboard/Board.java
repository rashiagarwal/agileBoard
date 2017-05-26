package au.com.dius.agileboard;

import java.util.Set;

import static java.util.Arrays.asList;

public class Board {

  private Iteration iteration;

  public Board(Set<String> columnNames) {
    checkColumnsAreValid(columnNames);

    this.iteration = new Iteration(columnNames);
  }

  public Iteration getIteration() {
    return this.iteration;
  }

  private void checkColumnsAreValid(Set<String> columnNames) {
    isValidSize(columnNames.size());
    hasMandatoryColumns(columnNames);
  }

  private void isValidSize(int size) {
    if (size < 2) {
      throw new IllegalArgumentException("Minimum number of columns should be two.");
    }
  }

  private void hasMandatoryColumns(Set<String> columnNames) {
    if (!columnNames.containsAll(asList("Starting", "Done"))) {
      throw new IllegalArgumentException("Must have 'Starting' and 'Done' columns");
    }
  }
}