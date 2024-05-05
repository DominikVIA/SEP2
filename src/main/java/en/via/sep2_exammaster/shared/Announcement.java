package en.via.sep2_exammaster.shared;

public class Announcement {
  private String title, content;

  public Announcement(String title, String content){
    this.title = title;
    this.content = content;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }
}
