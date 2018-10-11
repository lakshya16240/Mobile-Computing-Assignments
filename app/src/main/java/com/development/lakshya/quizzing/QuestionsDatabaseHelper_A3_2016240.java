package com.development.lakshya.quizzing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestionsDatabaseHelper_A3_2016240 extends SQLiteOpenHelper {

    private int DB_VERSION;
    public static final String TAG = "Database";

    public QuestionsDatabaseHelper_A3_2016240(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB_VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        cursor = db.query(null,null,null,null,null,null,null);
        Log.d(TAG, "onCreate: " + db.getPath());
        updateDatabase(db,0,DB_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db,oldVersion,newVersion);



    }

    private void updateDatabase(SQLiteDatabase db, int oldVer, int newVer) {

        if(oldVer<1){
            db.execSQL("CREATE TABLE QUESTIONS(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "QUESTION TEXT," +
                                                    "OPTION1 TEXT," +
                                                    "OPTION2 TEXT," +
                                                    "ANSWER TEXT," +
                                                    "SELECTED_ANSWER TEXT)");

            insertQuestion(db,"LCW is an Olympic gold medalist in Badminton","True","False","False");
            insertQuestion(db, "Australia has the most World Cup wins in Cricket","True","False", "True");
            insertQuestion(db,"Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.","True","False","True");
            insertQuestion(db,"Freeware is software that is available for use at no monetary cost","True","False","True");
            insertQuestion(db,"IPv6 Internet Protocol address is represented as eight groups of four Octal digits.","True","False","False");
            insertQuestion(db,"The hexadecimal number system contains digits from 1 - 15.","True","False","False");
            insertQuestion(db,"MS Word is a hardware.","True","False","False");
            insertQuestion(db,"Octal number system contains digits from 0 - 7.","True","False","True");
            insertQuestion(db,"CPU controls only input data of computer.","True","False","False");
            insertQuestion(db,"CPU stands for Central Performance Unit.","True","False","True");
            insertQuestion(db,"The Language that the computer can understand is called Machine Language.","True","False","True");
            insertQuestion(db,"Magnetic Tape used random access method.","True","False","False");
            insertQuestion(db,"Twitter is an online social networking and blogging service.","True","False","False");
            insertQuestion(db,"Worms and trojan horses are easily detected and eliminated by antivirus software.","True","False","True");
            insertQuestion(db,"Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.","True","False","True");
            insertQuestion(db,"GNU / Linux is a open source operating system.","True","False","True");
            insertQuestion(db,"When you include multiple addresses in a message, you should separate each address with a period (.)","True","False","False");
            insertQuestion(db,"You cannot format text in an e-mail message.","True","False","False");
            insertQuestion(db,"You must include a subject in any mail message you compose.","True","False","False");
            insertQuestion(db,"If you want to respond to the sender of a message, click the Respond button","True","False","False");
            insertQuestion(db,"When you reply to a message, you need to enter the text in the Subject: field","True","False","False");
            insertQuestion(db,"You can only print one copy of a selected message.","True","False","False");
            insertQuestion(db,"You cannot preview a message before you print it.","True","False","False");
            insertQuestion(db,"There is only one way to print a message","True","False","False");
            insertQuestion(db,"You type the body of a reply the same way you would type the body of a new message.","True","False","True");
            insertQuestion(db,"Your e-mail address must be unique.","True","False","True");
            insertQuestion(db,"You can sign up for Web-based e-mail without accepting the Web site's terms and conditions.","True","False","False");
            insertQuestion(db,"Web-based e-mail accounts do not required passwords","True","False","False");
            insertQuestion(db,"You can delete e-mails from a Web-based e-mail account","True","False","True");
            insertQuestion(db,"You can store Web-based e-mail messages in online folders.","True","False","True");

        }
//        else if(newVer==2){
//            db.execSQL("alter table QUESTIONS add column SELECTED_ANSWER TEXT");
//        }
//        else if(newVer==3){
//            Log.d(TAG, "updateDatabase: ");
//            insertQuestion(db,"Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.","True","False","True");
//            insertQuestion(db,"Freeware is software that is available for use at no monetary cost","True","False","True");
//            insertQuestion(db,"IPv6 Internet Protocol address is represented as eight groups of four Octal digits.","True","False","False");
//            insertQuestion(db,"The hexadecimal number system contains digits from 1 - 15.","True","False","False");
//            insertQuestion(db,"MS Word is a hardware.","True","False","False");
//            insertQuestion(db,"Octal number system contains digits from 0 - 7.","True","False","True");
//            insertQuestion(db,"CPU controls only input data of computer.","True","False","False");
//            insertQuestion(db,"CPU stands for Central Performance Unit.","True","False","True");
//            insertQuestion(db,"The Language that the computer can understand is called Machine Language.","True","False","True");
//            insertQuestion(db,"Magnetic Tape used random access method.","True","False","False");
//            insertQuestion(db,"Twitter is an online social networking and blogging service.","True","False","False");
//            insertQuestion(db,"Worms and trojan horses are easily detected and eliminated by antivirus software.","True","False","True");
//            insertQuestion(db,"Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.","True","False","True");
//            insertQuestion(db,"GNU / Linux is a open source operating system.","True","False","True");
//            insertQuestion(db,"When you include multiple addresses in a message, you should separate each address with a period (.)","True","False","False");
//            insertQuestion(db,"You cannot format text in an e-mail message.","True","False","False");
//            insertQuestion(db,"You must include a subject in any mail message you compose.","True","False","False");
//            insertQuestion(db,"If you want to respond to the sender of a message, click the Respond button","True","False","False");
//            insertQuestion(db,"When you reply to a message, you need to enter the text in the Subject: field","True","False","False");
//            insertQuestion(db,"You can only print one copy of a selected message.","True","False","False");
//            insertQuestion(db,"You cannot preview a message before you print it.","True","False","False");
//            insertQuestion(db,"There is only one way to print a message","True","False","False");
//            insertQuestion(db,"You type the body of a reply the same way you would type the body of a new message.","True","False","True");
//            insertQuestion(db,"Your e-mail address must be unique.","True","False","True");
//            insertQuestion(db,"You can sign up for Web-based e-mail without accepting the Web site's terms and conditions.","True","False","False");
//            insertQuestion(db,"Web-based e-mail accounts do not required passwords","True","False","False");
//            insertQuestion(db,"You can delete e-mails from a Web-based e-mail account","True","False","True");
//            insertQuestion(db,"You can store Web-based e-mail messages in online folders.","True","False","True");

//        }
    }

    private void insertQuestion(SQLiteDatabase db, String question, String option1, String option2, String answer)
    {

        ContentValues cv = new ContentValues();
        cv.put("QUESTION",question);
        cv.put("OPTION1",option1);
        cv.put("OPTION2",option2);
        cv.put("ANSWER",answer);
        cv.put("SELECTED_ANSWER", "");
        db.insert("QUESTIONS",null,cv);
    }
}
