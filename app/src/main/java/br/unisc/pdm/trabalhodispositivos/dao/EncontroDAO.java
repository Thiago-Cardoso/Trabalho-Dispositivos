package br.unisc.pdm.trabalhodispositivos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unisc.pdm.trabalhodispositivos.database.DadosContract;
import br.unisc.pdm.trabalhodispositivos.database.DadosDbHelper;
import br.unisc.pdm.trabalhodispositivos.vo.EncontroVO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;

/**
 * Created by Diego on 20/05/2015.
 */
public class EncontroDAO {

    Context context;
    private DadosDbHelper mdb;
    private SQLiteDatabase database;
    private String[] colunas = {
            DadosContract.Encontro._ID,
            DadosContract.Encontro.EVENTO_ID,
            DadosContract.Encontro.DESCRICAO,
            DadosContract.Encontro.HORA,
            DadosContract.Encontro.DATA,
    };

    public EncontroDAO (Context context){
        this.mdb = new DadosDbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = mdb.getWritableDatabase();
    }

    public void close() {
        mdb.close();
    }

    public EncontroVO insertEncontro(EncontroVO v){

        ContentValues dadosEncontro = new ContentValues();
        //dadosEncontro.put(DadosContract.Encontro.EVENTO_ID, v.getId_encontro());
        dadosEncontro.put(DadosContract.Encontro.EVENTO_ID, v.getId());
        dadosEncontro.put(DadosContract.Encontro.DESCRICAO, v.getDescricao());
        dadosEncontro.put(DadosContract.Encontro.DATA, v.getData().toString());
        dadosEncontro.put(DadosContract.Encontro.HORA, v.getHora().toString());

        try {
            long newEncontroId = database.insert(
                    DadosContract.Encontro.TABLE_NAME,
                    null,   //The second argument provides the name of a column in which the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then the framework will not insert a row when there are no values).
                    dadosEncontro);

            Toast.makeText(this.context, "Encontro inserido com sucesso: " + newEncontroId, Toast.LENGTH_SHORT).show();
            return v;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteEvento(EncontroVO encontro) {
        long id = encontro.getId_encontro();
        Log.d("DESIGNCRUD", "Encontro deleted with id: " + id);
        database.delete(DadosContract.Encontro.TABLE_NAME, DadosContract.Encontro._ID + " = " + id, null);
        Toast.makeText(this.context, "Encontro deletado com sucesso: " + id, Toast.LENGTH_SHORT).show();
    }

    public List<EncontroVO> getAllEncontros() {
        List<EncontroVO> encontros = new ArrayList<EncontroVO>();

        Cursor cursor = database.query(DadosContract.Encontro.TABLE_NAME,
                colunas, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EncontroVO encontro = cursorToEncontro(cursor);
            encontros.add(encontro);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return encontros;
    }

    private EncontroVO cursorToEncontro(Cursor cursor) {
        EncontroVO encontro = new EncontroVO();
        encontro.setId_encontro(cursor.getInt(0));
        encontro.setId(cursor.getInt(1));
        encontro.setDescricao(cursor.getString(2));
        encontro.setData(cursor.getString(3));
        encontro.setHora(cursor.getString(4));
        return encontro;
    }

    public EncontroVO getEncontroId(int id) {


        //Colunas para filtrar no WHERE da query
        String selection = DadosContract.Encontro._ID + " = ?";
        //Valores para filtrar no WHERE da query, na mesma ordem das colunas
        String[] selectionArgs = {
                String.valueOf(id)
        };

        Cursor c = database.query(
                DadosContract.Encontro.TABLE_NAME,      // The table to query
                colunas,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if(c.getCount() > 0){
            c.moveToFirst();
            EncontroVO v = cursorToEncontro(c);
            return v;
        }else
            return null;

    }

    public void updateEncontro(EncontroVO v) {
        ContentValues dadosEncontro= new ContentValues();
        dadosEncontro.put(DadosContract.Encontro.EVENTO_ID, v.getId());
        dadosEncontro.put(DadosContract.Encontro.DESCRICAO,v.getDescricao());
        dadosEncontro.put(DadosContract.Encontro.DATA, v.getData().toString());
        dadosEncontro.put(DadosContract.Encontro.HORA, v.getHora().toString());

        // Which row to update, based on the ID
        String selection = DadosContract.Encontro._ID + " = ?";
        String[] selectionArgs = { String.valueOf(v.getId_encontro()) };

        try {
            int count = database.update(
                    DadosContract.Encontro.TABLE_NAME,
                    dadosEncontro,
                    selection,
                    selectionArgs);


            Toast.makeText(this.context, "Encontro atualizado com sucesso: " + count, Toast.LENGTH_SHORT).show();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}
