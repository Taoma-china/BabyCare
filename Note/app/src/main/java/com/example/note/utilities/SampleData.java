package com.example.note.utilities;

import com.example.note.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TEXT_1 = "A simple note";
    private static final String SAMPLE_TEXT_2 = "A note ff";
    private static final String SAMPLE_TEXT_3 = "A simple noteA simple noteA simple note" +
            "A simple notevA simple noteA simple noteA simple noteA simple noteA simple note" +
            "A simple noteA simple noteA simple noteA simple noteA simple noteA simple noteA simple note" +
            "A simple noteA simple noteA simple noteA simple noteA simple noteA simple noteA simple noteA simple note" +
            "A simple noteA simple noteA simple noteA simple noteA simple note";
    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }
    public static List<NoteEntity> getNotes(){
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity(getDate(0), SAMPLE_TEXT_1));
        notes.add(new NoteEntity(getDate(-1), SAMPLE_TEXT_2));
        notes.add(new NoteEntity(getDate(-2), SAMPLE_TEXT_3));
        return notes;
    }

}

