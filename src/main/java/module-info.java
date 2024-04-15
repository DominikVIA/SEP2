module en.via.sep2_exammaster {
  requires javafx.controls;
  requires javafx.fxml;

  opens en.via.sep2_exammaster to javafx.fxml;
  exports en.via.sep2_exammaster;
}