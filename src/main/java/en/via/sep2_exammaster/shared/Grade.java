package en.via.sep2_exammaster.shared;

public enum Grade {
  A ((short) 12), B((short) 10) , C((short) 7), D((short) 4),
  E((short) 2), Fx((short) 0), F((short) -3);

  private final short grade;

  Grade(short grade){
    this.grade = grade;
  }
}
