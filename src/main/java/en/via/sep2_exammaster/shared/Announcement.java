package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Announcement class represents an announcement with a unique identifier, title, content,
 * date, and time.
 * <p>
 * Announcements are typically used to convey important information to users within an
 * application.
 */
public class Announcement implements Serializable {

  /** Unique identifier. */
  private int id;

  /** Announcement title. */
  private String title;

  /** Announcement content. */
  private String content;

  /** Announcement date. */
  private LocalDate date;

  /** Announcement time. */
  private LocalTime time;

  /**
   * Constructs a new Announcement with the specified id, title, content, date, and time.
   *
   * @param id      the unique identifier of the announcement
   * @param title   the title of the announcement
   * @param content the content of the announcement
   * @param date    the date of the announcement
   * @param time    the time of the announcement
   */
  public Announcement(int id, String title, String content, LocalDate date, LocalTime time) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.date = date;
    this.time = time;
  }

  /**
   * Returns the title of the announcement.
   *
   * @return the title of the announcement
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content of the announcement.
   *
   * @return the content of the announcement
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the date of the announcement.
   *
   * @return the date of the announcement
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Returns the time of the announcement.
   *
   * @return the time of the announcement
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * Returns a string representation of the Announcement object.
   *
   * @return a string representation of the Announcement object consisting of a date and title.
   */
  @Override
  public String toString() {
    return date + " - " + title;
  }
}

