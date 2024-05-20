package en.via.sep2_exammaster.shared;

import java.io.Serializable;

public enum Grade implements Serializable
{
  A (12), B(10) , C(7), D(4), E(2), Fx(0), F(-3), Sick(-1), Null(-2);

  private final int grade;

  Grade(int grade){
    this.grade = grade;
  }

  public int getGrade(){
    return grade;
  }

  public static Grade findGrade(int grade){
    switch (grade){
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

  @Override public String toString() {
    if(grade == -1) return "Sick";
    else if(grade == -2) return "SELECT GRADE";
    else return getGrade() + " (ECTS: " + name() + ")";

  }
}
