package br.edu.ifspsaocarlos.agenda.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    static final String DATABASE_TABLE = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "flgFavorito";
    static final String KEY_FONE2 = "fone2";
    static final String KEY_DTNASC = "dtNasc";


    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, "  +
            KEY_FONE2 + " TEXT, "  +
            KEY_DTNASC + " TEXT, "  +
            KEY_FAVORITO + " INTEGER NOT NULL DEFAULT 0, "  +
            KEY_EMAIL + " TEXT);";

    //FEATURE FAVORITO
    private static final String DATABASE_ALTER_VS_2 = "ALTER TABLE "
            + DATABASE_TABLE + " ADD COLUMN " + KEY_FAVORITO + " INTEGER NOT NULL DEFAULT 0;";

    //FEATURE TELEFONE 2
    private static final String DATABASE_ALTER_VS_3 = "ALTER TABLE "
           + DATABASE_TABLE + " ADD COLUMN " + KEY_FONE2 + " TEXT;";

    //FEATURE DTNASC
    private static final String DATABASE_ALTER_VS_4 = "ALTER TABLE "
            + DATABASE_TABLE + " ADD COLUMN " + KEY_DTNASC + " TEXT;";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        Log.v("LOG_AGN", "hit create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int    newVersion) {

        Log.v("LOG_AGN", "hit upgrade");
        if (oldVersion < 2) {
            //a) Incluir a funcionalidade “Favoritar”
            //Adiciono uma coluna na tabela contato que irá dizer se o contato foi favoritado ou não. Inteiro pois o SQLLite não oferece suporte para Booleanos
            database.execSQL(DATABASE_ALTER_VS_2);
        }
        if (oldVersion < 3) {
            //Os usuários do app estão solicitando a inclusão de mais um campo para armazenar outro
            //número de telefone do contato. Implemente esta funcionalidade.
            database.execSQL(DATABASE_ALTER_VS_3);
        }
        if (oldVersion < 4) {
            //a) Inclua um campo simples para armazenar a data (dia e mês) de aniversário do contato.
            database.execSQL(DATABASE_ALTER_VS_4);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("LOG_AGN", "hit downgrade");
    }
}

