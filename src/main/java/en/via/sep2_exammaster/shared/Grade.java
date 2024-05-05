package en.via.sep2_exammaster.shared;

public enum Grade {
  A (12), B(10) , C(7), D(4), E(2), Fx(0), F(-3);

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
      default -> {
        return F;
      }
    }
  }
}
