package au.com.dius.agileboard;

public class Card {

  private String title;
  private String description;
  private int estimate;
  private Column column;

  public Card(String title, String description, int estimate) {
    if (estimate < 1) throw new IllegalArgumentException("Estimate cannot be less than 1");
    this.title = title;
    this.description = description;
    this.estimate = estimate;
  }

  String getTitle() {
    return title;
  }

  String getDescription() {
    return description;
  }

  int getEstimate() {
    return estimate;
  }

  void setColumn(Column column) {
    this.column = column;
  }

  public Column getColumn() {
    return this.column;
  }

  @Override
  public String toString() {
    return "Card{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", estimate=" + estimate +
        ", column=" + (null != column ? column.getName() : "Unassigned") +
        '}';
  }
}