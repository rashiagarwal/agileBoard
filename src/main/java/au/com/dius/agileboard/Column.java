package au.com.dius.agileboard;

import java.util.ArrayList;
import java.util.List;

public class Column {

  private String name;
  private List<Card> cards = new ArrayList<>();
  private int workInProgressLimit;

  Column(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  List<Card> getCards() {
    return this.cards;
  }

  void addCard(Card card) {
    if (workInProgressLimit > 0 && getColumnSize() + card.getEstimate() > workInProgressLimit) {
      throw new WorkInProgressLimitExceededException(String.format("Adding this card will breach the work in progress limit [limit=%d]", this.workInProgressLimit));
    }
    card.setColumn(this);
    this.cards.add(card);
  }

  int getColumnSize() {
    return getCards().stream().mapToInt(Card::getEstimate).sum();
  }

  void removeCard(Card card) {
    cards.remove(card);
  }

  public void setWorkInProgressLimit(int workInProgressLimit) {
    this.workInProgressLimit = workInProgressLimit;
  }

  int getWorkInProgressLimit() {
    return this.workInProgressLimit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Column column = (Column) o;

    return name != null ? name.equals(column.name) : column.name == null;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Column{" +
        "name='" + name + '\'' +
        ", workInProgressLimit=" + workInProgressLimit +
        '}';
  }
}