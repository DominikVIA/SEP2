import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.teacher.CreateCourseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertTrue;

public class CreateCourseViewModelTest {
  private Model model;
  private CreateCourseViewModel viewModel;
  private StringProperty code, semester, title, description, additionalTeacher, student;
  private ObjectProperty<ObservableList<Student>> studentsList;


  @BeforeEach

  void setUp() {
    model = Mockito.mock(Model.class);
    viewModel = new CreateCourseViewModel(model);
    this.code = new SimpleStringProperty("");
    this.semester = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.additionalTeacher = new SimpleStringProperty("");
    this.student = new SimpleStringProperty("");
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    viewModel.bindCode(code);
    viewModel.bindSemester(semester);
    viewModel.bindTitle(title);
    viewModel.bindDescription(description);
    viewModel.bindAdditionalTeacher(additionalTeacher);
    viewModel.bindStudent(student);
    viewModel.bindStudents(studentsList);
  }

  @Test
  void createCourse_withValidData_createsSuccessfully() throws IOException {
    code.set("DBS1");
    semester.set("2");
    title.set("Database");
    description.set("Introduction to Database");
    additionalTeacher.set("XYZ");

    viewModel.createCourse();

    Mockito.verify(model).createCourse("DBS1", 2, "Database", "Introduction to Database", "XYZ", studentsList.get());
  }

  @Test
  void duplicateStudentId_setsError() throws IOException {
    Student student = new Student(1, "Dominik", "kiel");
    Mockito.when(model.getStudent(1)).thenReturn(student);

    this.student.set("1");
    viewModel.addStudent();

    this.student.set("1");
    viewModel.addStudent();

    assertEquals(1, studentsList.get().size());
    Mockito.verify(model, Mockito.times(2)).getStudent(1);
  }


  @Test
  void createCourse_withEmptyCode_doesNotCreateCourse() throws IOException {
    code.set("");
    semester.set("1");
    title.set("Data Structures");
    description.set("Introduction to Data Structures");
    additionalTeacher.set("DEF");

    viewModel.createCourse();

    Mockito.verify(model, Mockito.never()).createCourse(anyString(), anyInt(), anyString(), anyString(), anyString(), anyList());
  }
  @Test
  void addStudent_withMissingData_doesNotAddStudent() throws IOException {
    student.set("");

    viewModel.addStudent();
    assertTrue(studentsList.get().isEmpty());

    Mockito.verify(model, never()).getStudent(anyInt());

  }

}