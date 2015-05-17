package br.unisc.pdm.trabalhodispositivos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pdm.trabalhodispositivos.database.DadosContract;
import br.unisc.pdm.trabalhodispositivos.database.DadosDbHelper;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaEventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;


/**
 * Created by Diego on 17/05/2015.
 */
public class EventoPessoaDAO {
    Context context;
    private DadosDbHelper mdb;
    private SQLiteDatabase database;
    private String[] colunas = {
            DadosContract.PessoaEvento.FK_EVENTO_ID,
            DadosContract.PessoaEvento.FK_PESSOA_ID,
    };

    public EventoPessoaDAO (Context context){
        this.mdb = new DadosDbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = mdb.getWritableDatabase();
    }

    public void close() {
        mdb.close();
    }

    public PessoaEventoVO insertEvento(PessoaEventoVO v){

        ContentValues dadosEvento = new ContentValues();
        dadosEvento.put(DadosContract.PessoaEvento.FK_EVENTO_ID, v.getFk_evento_id());
        dadosEvento.put(DadosContract.PessoaEvento.FK_PESSOA_ID, v.getFk_pessoa_id());

        try {
            long newEventoId = database.insert(
                    DadosContract.PessoaEvento.TABLE_NAME,
                    null,   //The second argument provides the name of a column in which the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then the framework will not insert a row when there are no values).
                    dadosEvento);

            Toast.makeText(this.context, "Evento_Pessoa inserido com sucesso: " + newEventoId, Toast.LENGTH_SHORT).show();
            return v;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteEvento(PessoaEventoVO evento) {
        String id = evento.getFk_evento_id();
        Log.d("DESIGNCRUD", "EventoPessoa deleted with id: " + id);
        database.delete(DadosContract.PessoaEvento.TABLE_NAME, DadosContract.PessoaEvento._ID + " = " + id, null);
        Toast.makeText(this.context, "Pessoa_Evento deletado com sucesso: " + id, Toast.LENGTH_SHORT).show();
    }

    public PessoaEventoVO getPessoaById(int id) {


        //Colunas para filtrar no WHERE da query
        String selection = DadosContract.Pessoa._ID + " = ?";
        //Valores para filtrar no WHERE da query, na mesma ordem das colunas
        String[] selectionArgs = {
                String.valueOf(id)
        };

        Cursor c = database.query(
                DadosContract.Pessoa.TABLE_NAME,      // The table to query
                colunas,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if(c.getCount() > 0){
            c.moveToFirst();
            PessoaEventoVO p = cursorToEvento(c);
            return p;
        }else
            return null;

    }

    private PessoaEventoVO cursorToEvento(Cursor cursor) {
        PessoaEventoVO evento = new PessoaEventoVO();
        evento.setFk_evento_id(cursor.getString(0));
        return evento;
    }
/*
    public List<PessoaEventoVO> getAllEventos() {
        List<PessoaEventoVO> eventosPessoa = new ArrayList<PessoaEventoVO>();

        Cursor cursor = database.query(DadosContract.Evento.TABLE_NAME,
                colunas, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PessoaEventoVO evento = cursorToEvento(cursor);
            evento.add(eventosPessoa);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return eventos;
    }

    private PessoaEventoVO cursorToEvento(Cursor cursor) {
        PessoaEventoVO evento = new PessoaEventoVO();
        evento.setFk_evento_id(cursor.getString(0));
        evento.setFk_pessoa_id(cursor.getString(1));
        return evento;
    }


*/
}
