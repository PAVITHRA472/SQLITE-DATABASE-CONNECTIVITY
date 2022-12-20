PROGRAM:
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHandler extends SQLiteOpenHelper {
private static final String DB_NAME = "coursedb";
private static final int DB_VERSION = 1;
private static final String TABLE_NAME = "mycourses";
private static final String ID_COL = "id";
private static final String NAME_COL = "name";
private static final String DURATION_COL = "duration";
private static final String DESCRIPTION_COL = "description";
private static final String TRACKS_COL = "tracks";
public DBHandler(Context context) {
super(context, DB_NAME, null, DB_VERSION);
}
@Override
public void onCreate(SQLiteDatabase db) {
String query = "CREATE TABLE " + TABLE_NAME + " ("
+ ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
+ NAME_COL + " TEXT,"
+ DURATION_COL + " TEXT,"
+ DESCRIPTION_COL + " TEXT,"
+ TRACKS_COL + " TEXT)";
db.execSQL(query);
}
public void addNewCourse(String courseName, String courseDuration, String 
courseDescription, String courseTracks) {
SQLiteDatabase db = this.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(NAME_COL, courseName);
values.put(DURATION_COL, courseDuration);
values.put(DESCRIPTION_COL, courseDescription);
values.put(TRACKS_COL, courseTracks);
db.insert(TABLE_NAME, null, values);
db.close();
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(db);
}
}
MainActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, 
courseDescriptionEdt;
private Button addCourseBtn;
private DBHandler dbHandler;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
courseNameEdt = findViewById(R.id.idEdtCourseName);
courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
addCourseBtn = findViewById(R.id.idBtnAddCourse);
dbHandler = new DBHandler(MainActivity.this);
addCourseBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
String courseName = courseNameEdt.getText().toString();
String courseTracks = courseTracksEdt.getText().toString();
String courseDuration = courseDurationEdt.getText().toString();
String courseDescription = courseDescriptionEdt.getText().toString();
if (courseName.isEmpty() && courseTracks.isEmpty() && 
courseDuration.isEmpty() && courseDescription.isEmpty()) {
Toast.makeText(MainActivity.this, "Please enter all the 
data..", Toast.LENGTH_SHORT).show();
return;
}
dbHandler.addNewCourse(courseName, courseDuration, 
courseDescription, courseTracks);
Toast.makeText(MainActivity.this, "Course has been added.", 
Toast.LENGTH_SHORT).show();
courseNameEdt.setText("");
courseDurationEdt.setText("");
courseTracksEdt.setText("");
courseDescriptionEdt.setText("");
}
});
}
}
