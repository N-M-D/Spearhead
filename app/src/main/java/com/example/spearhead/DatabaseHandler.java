package com.example.spearhead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "spearheadDB";

    //Setup Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MUSIC = "music";
    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String TABLE_PLAYLISTMUSIC = "playlistmusc";

    //Setup User table fields
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    //Setup Music table fields
    private static final String KEY_MUSIC_ID = "musicid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_THUMBNAIL = "thumbnail";
    private static final String KEY_FILE = "file";

    //Setup Playlist table fields
    private static final String KEY_PLAYLIST_ID = "playlistid";
    private static final String KEY_PLAYLIST_NAME = "playlistname";
    private static final String KEY_PLAYLIST_USER = "playlistuser";

    //Setup PlaylistUser Fields
    private static final String KEY_PLAYLISTMUSIC_ID = "playlistmusicid";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT"
                + ")";
        String CREATE_MUSIC_TABLE = "CREATE TABLE " + TABLE_MUSIC + "("
                + KEY_MUSIC_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_ARTIST + " TEXT,"
                + KEY_THUMBNAIL + " INTEGER,"
                + KEY_FILE + " INTEGER"
                + ")";
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLISTS + "("
                + KEY_PLAYLIST_ID + " INTEGER PRIMARY KEY,"
                + KEY_PLAYLIST_NAME + " TEXT,"
                + KEY_PLAYLIST_USER + " INTEGER,"
                + "FOREIGN KEY(" + KEY_PLAYLIST_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_ID + ")"
                + ")";
        String CREATE_PLAYLIST_MUSIC_TABLE = "CREATE TABLE " + TABLE_PLAYLISTMUSIC + "("
                + KEY_PLAYLISTMUSIC_ID + " INTEGER PRIMARY KEY,"
                + KEY_PLAYLIST_ID + " INTEGER,"
                + KEY_MUSIC_ID + " INTEGER" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_MUSIC_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
        db.execSQL(CREATE_PLAYLIST_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLISTMUSIC);

        // Create tables again
        onCreate(db);
    }

    Boolean addUser(User user) throws Exception{
        Boolean success = false;
        EncryptorDecryptor encryptorDecryptor = new EncryptorDecryptor();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String encryptedPassword = encryptorDecryptor.encrypt(user.getPassword());

        if(!emailExists(user.getEmail())){
            //values.put(KEY_USER_ID, user.getUserID());
            values.put(KEY_EMAIL, user.getEmail());
            values.put(KEY_PASSWORD, encryptedPassword);
            values.put(KEY_NAME, user.getName());

            db.insert(TABLE_USERS, null, values);
            success = true;
        }

        db.close();
        return success;
    }

    int userLogin(String email, String password) throws Exception{
        Boolean verified = false;
        int userID = -1;
        EncryptorDecryptor encryptorDecryptor = new EncryptorDecryptor();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(selectQuery,  new String[] {email});

        if(cursor.moveToFirst()){
            String encryptedPassword = cursor.getString(3);
            String normalPassword = encryptorDecryptor.decrypt(encryptedPassword);
            if(password.equals(normalPassword)){
                userID = Integer.parseInt(cursor.getString(0));
            }
        }
        cursor.close();

        return userID;
    }

    Boolean emailExists(String email) throws Exception{
        Boolean exists = false;
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {email});

        if(cursor.moveToFirst()){
            exists = true;
        }
        cursor.close();

        return exists;
    }



    //Music Functions
    void addMusic(Music music){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, music.getName());
        values.put(KEY_THUMBNAIL, music.getImg());
        values.put(KEY_ARTIST, music.getArtist());
        values.put(KEY_FILE, music.getFile());

        // Inserting Row
        db.insert(TABLE_MUSIC, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public Music getMusicByID(int ID){
        Music music = new Music();
        SQLiteDatabase db = this.getWritableDatabase();
        String GET_MUSIC_BY_ID_QUERY = "SELECT * FROM " + TABLE_MUSIC + " WHERE " + KEY_MUSIC_ID + "=?";
        Cursor cursor = db.rawQuery(GET_MUSIC_BY_ID_QUERY, new String[]{ID + ""});
        if(cursor.moveToFirst()){
            String title = cursor.getString(1);
            String artist = cursor.getString(2);
            int img = cursor.getInt(3);
            int file = cursor.getInt(4);

            music.setID(ID);
            music.setName(title);
            music.setArtist(artist);
            music.setImg(img);
            music.setFile(file);
        }
        return music;
    }

    public List<Music> getAllMusic(){
        List<Music> musicList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MUSIC;

        Cursor cursor =db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Music music = new Music();
                music.setID(Integer.parseInt(cursor.getString(0)));
                music.setName(cursor.getString(1));
                music.setArtist(cursor.getString(2));
                music.setImg(Integer.parseInt(cursor.getString(3)));
                music.setFile(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                musicList.add(music);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return contact list
        return musicList;
    }

    public void addPlaylist(int uid, List<Music> musicList, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYLIST_NAME, name);
        values.put(KEY_PLAYLIST_USER, uid);

        long id = db.insert(TABLE_PLAYLISTS, null, values);
        Log.d("ID", id + "");

        for(int i = 0; i < musicList.size(); i++){
            ContentValues playlistMusicValues = new ContentValues();
            playlistMusicValues.put(KEY_PLAYLIST_ID, id);
            playlistMusicValues.put(KEY_MUSIC_ID, musicList.get(i).getID());
            db.insert(TABLE_PLAYLISTMUSIC, null, playlistMusicValues);
        }

        db.close();
    }

    public List<Playlist> getPlaylists(int uid){
        List<Playlist> playlists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String SELECT_PLAYLISTS_QUERY = "SELECT * FROM " + TABLE_PLAYLISTS + " WHERE " + KEY_PLAYLIST_USER + "=?";
        Cursor cursor = db.rawQuery(SELECT_PLAYLISTS_QUERY, new String[]{uid + ""});
        if(cursor.moveToFirst()){//Loop through Each playlist under the user
            do{
                List<Music> musicList = new ArrayList<>();
                int playlistID = cursor.getInt(0);
                String playlistName = cursor.getString(1);
                int userID = cursor.getInt(2);
                String SELECT_PLAYLIST_MUSIC_QUERY = "SELECT * FROM " + TABLE_PLAYLISTMUSIC + " WHERE " + KEY_PLAYLIST_ID + "=?";
                Cursor musicListCursor = db.rawQuery(SELECT_PLAYLIST_MUSIC_QUERY, new String[]{playlistID + ""});

                if(musicListCursor.moveToFirst()){//Loop through each music in the playlist
                    do{
                        Music music = getMusicByID(musicListCursor.getInt(2));
                        musicList.add(music);
                    }while (musicListCursor.moveToNext());
                }
                Playlist playlist = new Playlist(playlistName, userID, musicList);
                playlists.add(playlist);
            }while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("Num Playlists", playlists.size() + "");

        return playlists;
    }
}
