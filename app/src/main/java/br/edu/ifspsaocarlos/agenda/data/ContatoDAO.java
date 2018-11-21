package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.agenda.adapter.ContatoAdapter;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import java.util.ArrayList;
import java.util.List;


public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public  List<Contato> buscaTodosContatos()
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2, SQLiteHelper.KEY_DTNASC };

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null , null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFlgFavorito(cursor.getInt(4) == 1);
            contato.setFone2(cursor.getString(5));
            contato.setDtNasc(cursor.getString(6));
            contatos.add(contato);
        }
        cursor.close();

        database.close();
        return contatos;
    }

    public  List<Contato> buscaContato(String nome)
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2, SQLiteHelper.KEY_DTNASC };

        //b) Permita que a pesquisa por contatos seja realizada tanto por nome quanto por email
        String where=SQLiteHelper.KEY_NAME + " like ? OR " + SQLiteHelper.KEY_EMAIL + " like ?";
        String[] argWhere=new String[]{nome + "%", nome + "%"};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFlgFavorito(cursor.getInt(4) == 1);
            contato.setFone2(cursor.getString(5));
            contato.setDtNasc(cursor.getString(6));
            contatos.add(contato);
        }
        cursor.close();

        database.close();
        return contatos;
    }

    public List<Contato> buscaFavoritos()
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2, SQLiteHelper.KEY_DTNASC };

        String where=SQLiteHelper.KEY_FAVORITO + " = ?";
        String[] argWhere=new String[]{"1"};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null,SQLiteHelper.KEY_NAME );


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFlgFavorito(cursor.getInt(4) == 1);
            contato.setFone2(cursor.getString(5));
            contato.setDtNasc(cursor.getString(6));
            contatos.add(contato);


        }
        cursor.close();

        database.close();
        return contatos;
    }


    public void salvaContato(Contato c) {

        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FAVORITO, c.getFlgFavorito());
        values.put(SQLiteHelper.KEY_FONE2, c.getFone2());
        values.put(SQLiteHelper.KEY_DTNASC, c.getDtNasc());

       if (c.getId()>0)
          database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        else
           database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();

    }

    public  void atualizaFlgFavorito(Boolean pFlgFavorito, long pIdContato)
    {
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_FAVORITO, pFlgFavorito);
        database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "=" + pIdContato, null);
        database.close();
    }




    public void apagaContato(Contato c)
    {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);

        database.close();
    }
}
