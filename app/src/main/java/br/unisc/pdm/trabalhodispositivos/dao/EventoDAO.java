package br.unisc.pdm.trabalhodispositivos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import br.unisc.pdm.trabalhodispositivos.database.DadosContract;
import br.unisc.pdm.trabalhodispositivos.database.DadosDbHelper;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;

/**
 * Created by Thiago Cardoso on 03/05/2015.
 */
public class EventoDAO {
    Context context;
    private DadosDbHelper mdb;
    private SQLiteDatabase database;
    private String[] colunas = {
            DadosContract.Evento._ID,
            DadosContract.Evento.NOME,
            DadosContract.Evento.DATA_FIM,
            DadosContract.Evento.DATA_INICIO
    };

    public EventoDAO (Context context){
        this.mdb = new DadosDbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = mdb.getWritableDatabase();
    }

    public void close() {
        mdb.close();
    }

    public EventoVO insertEvento(EventoVO v){

        ContentValues dadosEvento = new ContentValues();
        dadosEvento.put(DadosContract.Evento.NOME, v.getNome());
        dadosEvento.put(DadosContract.Evento.DATA_INICIO, v.getData_inicio());
        dadosEvento.put(DadosContract.Evento.DATA_FIM, v.getData_fim());

        try {
            long newEventoId = database.insert(
                    DadosContract.Evento.TABLE_NAME,
                    null,   //The second argument provides the name of a column in which the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then the framework will not insert a row when there are no values).
                    dadosEvento);

            Toast.makeText(this.context, "Evento inserido com sucesso: " + newEventoId, Toast.LENGTH_SHORT).show();
            return v;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteEvento(EventoVO evento) {
        long id = evento.getId_evento();
        Log.d("DESIGNCRUD", "Evento deleted with id: " + id);
        database.delete(DadosContract.Evento.TABLE_NAME, DadosContract.Evento._ID + " = " + id, null);
        Toast.makeText(this.context, "Evento deletado com sucesso: " + id, Toast.LENGTH_SHORT).show();
    }

    public List<EventoVO> getAllEventos() {
        List<EventoVO> eventos = new ArrayList<EventoVO>();

        Cursor cursor = database.query(DadosContract.Evento.TABLE_NAME,
                colunas, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EventoVO evento = cursorToEvento(cursor);
            eventos.add(evento);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return eventos;
    }

    private EventoVO cursorToEvento(Cursor cursor) {
        EventoVO evento = new EventoVO();
        evento.setId_evento(cursor.getInt(0));
        evento.setNome(cursor.getString(1));
        evento.setData_fim(cursor.getString(2));
        evento.setData_inicio(cursor.getString(3));
        return evento;
    }

    public EventoVO getEventoById(int id) {


        //Colunas para filtrar no WHERE da query
        String selection = DadosContract.Evento._ID + " = ?";
        //Valores para filtrar no WHERE da query, na mesma ordem das colunas
        String[] selectionArgs = {
                String.valueOf(id)
        };

        Cursor c = database.query(
                DadosContract.Evento.TABLE_NAME,      // The table to query
                colunas,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        if(c.getCount() > 0){
            c.moveToFirst();
            EventoVO v = cursorToEvento(c);
            return v;
        }else
            return null;

    }

    public void deletevento(EventoVO evento) {
        long id = evento.getId_evento();
        Log.d("DESIGNCRUD", "evento deleted with id: " + id);
        database.delete(DadosContract.Evento.TABLE_NAME, DadosContract.Evento._ID + " = " + id, null);
        Toast.makeText(this.context, "evento deletado com sucesso: " + id, Toast.LENGTH_SHORT).show();
    }


    public void updateEvento(EventoVO v) {
        ContentValues dadosEvento= new ContentValues();
        dadosEvento.put(DadosContract.Evento.NOME,v.getNome());
        dadosEvento.put(DadosContract.Evento.DATA_INICIO, v.getData_inicio());
        dadosEvento.put(DadosContract.Evento.DATA_FIM, v.getData_fim());

        // Which row to update, based on the ID
        String selection = DadosContract.Evento._ID + " = ?";
        String[] selectionArgs = { String.valueOf(v.getId_evento()) };

        try {
            int count = database.update(
                    DadosContract.Evento.TABLE_NAME,
                    dadosEvento,
                    selection,
                    selectionArgs);


            Toast.makeText(this.context, "Evento atualizado com sucesso: " + count, Toast.LENGTH_SHORT).show();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
