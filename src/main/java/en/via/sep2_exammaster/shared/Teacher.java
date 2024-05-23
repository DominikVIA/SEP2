package en.via.sep2_exammaster.shared;

/**
 * The Teacher class represents a teacher object, extending the User class.
 * A Teacher object adds onto the User class by requiring and saving the teacher "initials".
 */
public class Teacher extends User {

  /** The teacher initials. */
  private final String initials;

  /**
   * Constructs a new Teacher with the specified initials, name, and password.
   *
   * @param initials the initials of the teacher
   * @param name     the name of the teacher
   * @param password the password of the teacher
   */
  public Teacher(String initials, String name, String password) {
    super(name, password);
    this.initials = initials;
  }

  /**
   * Returns the initials of the teacher.
   *
   * @return the initials of the teacher
   */
  public String getInitials() {
    return initials;
  }

  /**
   * Returns a string representation of the Teacher object.
   * The returned string is made up of the teacher's initials followed by the result of calling
   * the toString method of the superclass (User's full name).
   *
   * @return a string representation of the Teacher object
   */
  @Override
  public String toString() {
    return initials + " " + super.toString();
  }

  /**
   * Indicates whether some other object is equal to <b>this</b> one.
   * Two Teacher objects are considered equal if they have the same initials and password.
   *
   * @param obj the reference object that <b>this</b> is compared to
   * @return true if this object has the same password and initials as the provided one; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) && ((Teacher) obj).initials.equals(initials);
  }
}

