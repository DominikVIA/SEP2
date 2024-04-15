package en.via.sep2_exammaster.shared;

public enum Grade {
  A (12), B(10) , C(7), D(4), E(2), Fx(0), F(-3);

  private final int grade;

  Grade(int grade){
    this.grade = grade;
  }
}
