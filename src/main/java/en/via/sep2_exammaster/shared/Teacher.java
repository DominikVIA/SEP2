package en.via.sep2_exammaster.shared;

public class Teacher {
  private String initials;
  private String name;

  public Teacher(String initials, String name){
    this.initials = initials;
    this.name = name;
  }

  public String toString(){
    return initials + " - " + name;
  }
}
