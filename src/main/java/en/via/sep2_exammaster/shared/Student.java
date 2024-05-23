package en.via.sep2_exammaster.shared;

/**
 * The Student class represents a student object, extending the User class.
 * A Student object adds onto the User object by requiring and saving a "studentID".
 */
public class Student extends User {

  /** The student's unique ID number. */
  private final int studentID;

  /**
   * Constructs a new Student with the specified student ID, name, and password.
   *
   * @param studentID student's unique ID number
   * @param name      student's name
   * @param password  student's password
   */
  public Student(int studentID, String name, String password) {
    super(name, password);
    this.studentID = studentID;
  }

  /**
   * Returns the student ID of the student.
   *
   * @return the student's ID number
   */
  public int getStudentNo() {
    return studentID;
  }

  /**
   * Returns a string representation of the Student object.
   * The returned string is made up of the student's ID number, followed by the result of calling
   * the toString method of the superclass (User's full name).
   *
   * @return a string representation of the Student object
   */
  @Override
  public String toString() {
    return studentID + " " + super.toString();
  }

  /**
   * Indicates whether some other object is equal to <b>this</b> one.
   * Two Student objects are considered equal if they have the same ID and password.
   *
   * @param obj the reference object that <b>this</b> is compared to
   * @return true if this object has the same password and ID as the provided one; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) && ((Student) obj).studentID == studentID;
  }
}
