import en.via.sep2_exammaster.server.database.CourseDAOImpl;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseDAOImplTest {
  @Mock
  private Connection mockConnection;
  @Mock
  private PreparedStatement mockStatement;
  @Mock
  private ResultSet mockResultSet;

  private CourseDAOImpl courseDAO;

  @BeforeEach
  void setUp() throws SQLException {
    MockitoAnnotations.openMocks(this);
    courseDAO = CourseDAOImpl.getInstance();

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
    when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    doNothing().when(mockStatement).setString(anyInt(), anyString());
    doNothing().when(mockStatement).setInt(anyInt(), anyInt());
    when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
    when(mockStatement.executeUpdate()).thenReturn(1);
    when(mockStatement.execute()).thenReturn(true);

    mockStatic(DriverManager.class);
    when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);
  }

  @Test
  void createCourse_withValidData_returnsCourse() throws SQLException {
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getString(anyInt())).thenReturn("DBS1");
    when(mockResultSet.getInt(anyInt())).thenReturn(2);
    when(mockResultSet.getString(anyInt())).thenReturn("Database Systems");

    List<Student> students = new ArrayList<>();
    Teacher teacher = new Teacher("XYZ", "Teacher XYZ", "Professor");
    Course expected = new Course("DBS1", 2, "Database Systems", "Advanced topics in databases", teacher);

    Course result = courseDAO.createCourse("DBS1", 2, "Database Systems", "Advanced topics in databases", teacher, null, students);

    assertNotNull(result);
    assertEquals(expected.getCode(), result.getCode());
  }

  @Test
  void createCourse_withDuplicateCode_throwsSQLException() throws SQLException {
    when(mockResultSet.next()).thenReturn(true); // Code already exists
    when(mockResultSet.getInt(1)).thenReturn(1);

    Teacher teacher = new Teacher("XYZ", "Teacher XYZ", "Professor");
    List<Student> students = new ArrayList<>();

    assertThrows(IllegalArgumentException.class, () -> {
      courseDAO.createCourse("DBS1", 2, "Database Systems", "Advanced topics in databases", teacher, null, students);
    });
  }

  @Test
  void deleteCourse_withValidCode_executesWithoutError() throws SQLException {
    courseDAO.deleteCourse("DBS1");
    verify(mockStatement, times(1)).execute();
  }
}

