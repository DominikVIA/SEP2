package en.via.sep2_exammaster.shared;

import java.io.Serializable;

/**
 * The User class represents a generic user with a name and password.
 * This class serves as a base for the Teacher and Student classes and cannot exist on its own,
 * must be either a Teacher or a Student.
 */
public abstract class User implements Serializable {

  /** The full name of the Teacher or Student. */
  private final String name;

  /** The password of the user. */
  private final String password;

  /**
   * Constructs a new User with the specified name and password.
   *
   * @param name     the name of the user
   * @param password the password of the user
   */
  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  /**
   * Returns the name of the user.
   *
   * @return the name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns a string representation of the User object.
   *
   * @return a string consisting of just the name of the user.
   */
  @Override
  public String toString() {
    return "(" + name + ")";
  }

  /**
   * Indicates whether some other object is equal to <b>this</b> one.
   * Two User objects are considered equal if they have the same password.
   *
   * @param obj the reference object that <b>this</b> is compared to
   * @return true if this object has the same password as the provided one; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) return false;
    User temp = (User) obj;
    return temp.getPassword().equals(getPassword());
  }
}