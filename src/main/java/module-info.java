module en.via.sep2_exammaster {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires org.postgresql.jdbc;
  requires java.rmi;
  requires java.desktop;
  requires remoteobserver;

  opens en.via.sep2_exammaster to javafx.fxml;
  opens en.via.sep2_exammaster.view to javafx.fxml;
  exports en.via.sep2_exammaster;
  exports en.via.sep2_exammaster.model;
  exports en.via.sep2_exammaster.viewmodel;
  exports en.via.sep2_exammaster.shared to java.rmi;
  exports en.via.sep2_exammaster.view to javafx.fxml;
}