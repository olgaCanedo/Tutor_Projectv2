package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olgac on 3/23/2018.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TutorsDB.db";
    private static final int DATABASE_VERSION = 1;

    private String TABLE_CREATE_Users =
            "create table users (IDUser integer primary key autoincrement, " +
                    "Name text, Password text);";

    public static final String TABLE_TUTORS = "Tutors";
    public static final String COLUMN_ID = "tutorId";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_CAMPUS= "campus";
    public static final String COLUMN_SUBJECT= "subject";
    public static final String COLUMN_SKILL= "skill";


    private static final String TABLE_CREATE_Tutors =
            "CREATE TABLE " + TABLE_TUTORS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PHONE + " NUMERIC, " +
                    COLUMN_CAMPUS + " TEXT FOREIGN KEY('CampusID') REFERENCES 'Campus'('CampusID') " +
                    COLUMN_SUBJECT + " TEXT FOREIGN KEY('SubjectID') REFERENCES 'Subject'('SubjectID') " +
                    COLUMN_SKILL + " TEXT FOREIGN KEY('Skill_ID') REFERENCES 'Skills'('Skill_ID') " +
                    ")";

    public SQLite_OpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_Users);
        db.execSQL(TABLE_CREATE_Tutors);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "users");
        db.execSQL(TABLE_CREATE_Users);

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TUTORS);
        db.execSQL(TABLE_CREATE_Tutors);
    }

    //Method for open the database
    public void openDB(){
        this.getWritableDatabase();
    }

    //Method for close the database
    public void closeDB(){
        this.close();
    }

    //Method to insert records in the users table
    public void insertUser(String name, String password){
        ContentValues userData = new ContentValues();
        userData.put("Name", name);
        userData.put("Password", password);
        this.getWritableDatabase().insert("users",null,userData);
    }

    //Checks if the user exists
    public Cursor DataValidation(String user, String password) throws SQLException{
        Cursor cursor=null;
        cursor = this.getReadableDatabase().query("users", new String[]{"IDUser",
        "Name", "Password"}, "Name like '"+ user + "' "+
        "and Password like '"+password+"'",null,null,null,null);

        return cursor;
    }
}
