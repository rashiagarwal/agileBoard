package au.com.dius.agileboard;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class Iteration {
  private Set<Column> columns = new HashSet<>();
  private State state = new State();

  Iteration(Set<String> columnNames) {
    createColumns(columnNames);
  }

  public void add(Card card) {
    Column startingColumn = getColumn("Starting");
    startingColumn.addCard(card);
  }

  public int velocity() {
    Column doneColumn = getColumn("Done");
    return doneColumn.getColumnSize();
  }

  public void moveCard(Card card, String destinationColumnName) {
    Column destinationColumn = getColumn(destinationColumnName);
    state.save(card, destinationColumn);

    Column sourceColumn = card.getColumn();
    sourceColumn.removeCard(card);
    destinationColumn.addCard(card);
  }

  public void undoLastMove() {
    state.restore();
  }

  public Column getColumn(String columnName) {
    return this.columns.stream().filter(column -> Objects.equals(column.getName(), columnName))
        .findFirst().orElseThrow((Supplier<RuntimeException>) () ->
            new IllegalArgumentException(columnName + " Not found"));
  }

  public Set<Column> getColumns() {
    return columns;
  }

  private void createColumns(Set<String> columnNames) {
    columnNames.forEach(name -> columns.add(new Column(name)));
  }

  class State {
    private Card card;
    private Column sourceColumn;
    private Column destinationColumn;

    void save(Card card, Column destinationColumn) {
      this.card = card;
      this.sourceColumn = card.getColumn();
      this.destinationColumn = destinationColumn;
    }

    void restore() {
      sourceColumn.addCard(card);
      destinationColumn.removeCard(card);
    }
  }
}