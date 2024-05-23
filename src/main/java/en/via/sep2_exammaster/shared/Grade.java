package en.via.sep2_exammaster.shared;

import java.io.Serializable;

/**
 * The Grade enum represents different ECTS grades along with their equivalent values of the Danish grading system (from 12 to -3),
 * along with 2 <i>utility grades</i>, representing Sick or Null.
 */
public enum Grade implements Serializable {

  /** The ECTS grade A with a corresponding Danish grade value of 12. */
  A(12),

  /** The ECTS grade B with a corresponding Danish grade value of 10. */
  B(10),

  /** The ECTS grade C with a corresponding Danish grade value of 7. */
  C(7),

  /** The ECTS grade D with a corresponding Danish grade value of 4. */
  D(4),

  /** The ECTS grade E with a corresponding Danish grade value of 2. */
  E(2),

  /** The ECTS grade Fx with a corresponding Danish grade value of 0. */
  Fx(0),

  /** The ECTS grade F with a corresponding Danish grade value of -3. */
  F(-3),

  /** The grade Sick with a special value of -1, used to represent when a student couldn't attend an exam due to illness. */
  Sick(-1),

  /** The Null grade used when no grade is assigned, with a special value of -2. */
  Null(-2);

  /** The Danish grade value associated with each ECTS grade and each special utility grade. */
  private final int grade;

  /**
   * Constructs a new ECTS Grade with the specified Danish grade value.
   *
   * @param grade the numerical Danish grade value associated with the ECTS grade
   */
  Grade(int grade) {
    this.grade = grade;
  }

  /**
   * Returns the numerical Danish grade value associated with the ECTS grade.
   *
   * @return the numerical Danish grade value associated with the ECTS grade
   */
  public int getGrade() {
    return grade;
  }

  /**
   * Finds and returns the Grade enum constant corresponding to the specified numerical Danish grade.
   *
   * @param grade the numerical Danish grade to be turned into an ECTS Grade
   * @return the ECTS Grade corresponding to the provided numerical Danish grade
   */
  public static Grade findGrade(int grade) {
    switch (grade) {
      case 12 -> {
        return A;
      }
      case 10 -> {
        return B;
      }
      case 7 -> {
        return C;
      }
      case 4 -> {
        return D;
      }
      case 2 -> {
        return E;
      }
      case 0 -> {
        return Fx;
      }
      case -3 -> {
        return F;
      }
      case -1 -> {
        return Sick;
      }
      default -> {
        return Null;
      }
    }
  }

  /**
   * Returns a string representation of the Grade object.
   * <p>
   * The string representation consists of the ECTS value followed by the grade name in parentheses.
   * Additionally, for the grade Sick, the string "Sick" is returned instead of the ECTS value.
   *
   * @return a string representation of the Grade object
   */
  @Override
  public String toString() {
    if (grade == -1) return "Sick";
    else if (grade == -2) return "SELECT GRADE";
    else return getGrade() + " (ECTS: " + name() + ")";
  }
}
