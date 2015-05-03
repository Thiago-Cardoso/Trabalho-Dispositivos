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
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;

/**
 * Created by jossandro on 15/04/15.
 */
public class PessoaDAO {
    Context context;
    private DadosDbHelper mdb;
    private SQLiteDatabase database;
    private String[] colunas = {
            DadosContract.Pessoa._ID,
            DadosContract.Pessoa.NOME,
            DadosContract.Pessoa.IDADE,
            DadosContract.Pessoa.EMAIL,
            DadosContract.Pessoa.MATRICULA,
            DadosContract.Pessoa.BLOB,
    };

    public PessoaDAO (Context context){
        this.mdb = new DadosDbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = mdb.getWritableDatabase();
    }

    public void close() {
        mdb.close();
    }

    public PessoaVO insertPessoa(PessoaVO p){

        ContentValues dadosPessoa = new ContentValues();
        dadosPessoa.put(DadosContract.Pessoa.NOME,p.getNome());
        dadosPessoa.put(DadosContract.Pessoa.IDADE,p.getIdade());
        dadosPessoa.put(DadosContract.Pessoa.EMAIL,p.getEmail());
        dadosPessoa.put(DadosContract.Pessoa.MATRICULA, p.getMatricula());

        try {
            long newPessoaId = database.insert(
                    DadosContract.Pessoa.TABLE_NAME,
                    null,   //The second argument provides the name of a column in which the framework can insert NULL in the event that the ContentValues is empty (if you instead set this to "null", then the framework will not insert a row when there are no values).
                    dadosPessoa);

            Toast.makeText(this.context, "Pessoa inserida com sucesso: " + newPessoaId, Toast.LENGTH_SHORT).show();
            return p;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void deletePessoa(PessoaVO pessoa) {
        long id = pessoa.getId_pessoa();
        Log.d("DESIGNCRUD", "Pessoa deleted with id: " + id);
        database.delete(DadosContract.Pessoa.TABLE_NAME, DadosContract.Pessoa._ID + " = " + id, null);
        Toast.makeText(this.context, "Pessoa deletada com sucesso: " + id, Toast.LENGTH_SHORT).show();
    }

    public List<PessoaVO> getAllPessoas() {
        List<PessoaVO> pessoas = new ArrayList<PessoaVO>();

        Cursor cursor = database.query(DadosContract.Pessoa.TABLE_NAME,
                colunas, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PessoaVO pessoa = cursorToPessoa(cursor);
            pessoas.add(pessoa);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return pessoas;
    }

    private PessoaVO cursorToPessoa(Cursor cursor) {
        PessoaVO pessoa = new PessoaVO();
        pessoa.setId_pessoa(cursor.getInt(0));
        pessoa.setNome(cursor.getString(1));
        pessoa.setIdade(cursor.getString(2));
        pessoa.setEmail(cursor.getString(3));
        pessoa.setMatricula(cursor.getString(4));
        return pessoa;
    }

    public PessoaVO getPessoaById(int id) {


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
            PessoaVO p = cursorToPessoa(c);
            return p;
        }else
            return null;

    }

    public void updatePessoa(PessoaVO p) {
        ContentValues dadosPessoa = new ContentValues();
        dadosPessoa.put(DadosContract.Pessoa.NOME,p.getNome());
        dadosPessoa.put(DadosContract.Pessoa.IDADE,p.getIdade());
        dadosPessoa.put(DadosContract.Pessoa.EMAIL,p.getEmail());
        dadosPessoa.put(DadosContract.Pessoa.MATRICULA,p.getMatricula());

        // Which row to update, based on the ID
        String selection = DadosContract.Pessoa._ID + " = ?";
        String[] selectionArgs = { String.valueOf(p.getId_pessoa()) };

        try {
            int count = database.update(
                    DadosContract.Pessoa.TABLE_NAME,
                    dadosPessoa,
                    selection,
                    selectionArgs);


            Toast.makeText(this.context, "Pessoa atualizada com sucesso: " + count, Toast.LENGTH_SHORT).show();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
