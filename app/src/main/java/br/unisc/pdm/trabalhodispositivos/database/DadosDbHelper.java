package br.unisc.pdm.trabalhodispositivos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 */
public class DadosDbHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String BLOB_TYPE = " BLOB";
    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    /**
     * Criação da tabela Pessoa
     */
    private static final String SQL_CREATE_TABLE_PESSOA =
            "CREATE TABLE " + DadosContract.Pessoa.TABLE_NAME + " (" +
                    DadosContract.Pessoa._ID + " INTEGER PRIMARY KEY, " +
                    DadosContract.Pessoa.EVENTO_ID + " INT " + COMMA_SEP +
                    DadosContract.Pessoa.NOME + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Pessoa.IDADE + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Pessoa.MATRICULA + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Pessoa.EMAIL + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Pessoa.BLOB + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY (" + DadosContract.Pessoa.EVENTO_ID + ") " +
                    "REFERENCES " + DadosContract.Evento.TABLE_NAME + " (" +
                    DadosContract.Evento._ID + ") "+
            " )";


    /**
     * Criação da tabela PESSOAEVENTO
     */
    private static final String SQL_CREATE_TABLE_PESSOAEVENTO =
            "CREATE TABLE " + DadosContract.Pessoa.TABLE_NAME + " (" +
                    " FOREIGN KEY (" + DadosContract.PessoaEvento.FK_EVENTO_ID + ") " +
                    "REFERENCES " + DadosContract.Evento.TABLE_NAME + " (" +
                    DadosContract.Evento._ID + ") "+
                    " FOREIGN KEY (" + DadosContract.PessoaEvento.FK_PESSOA_ID + ") " +
                    "REFERENCES " + DadosContract.Pessoa.TABLE_NAME + " (" +
                    DadosContract.Pessoa._ID + ") "+
                    " )";

    /**
     * Criação da tabela Evento
     */
         private static final String SQL_CREATE_TABLE_EVENTO =
            "CREATE TABLE " + DadosContract.Evento.TABLE_NAME + " (" +
                    DadosContract.Evento._ID + " INTEGER PRIMARY KEY, " +
                    DadosContract.Evento.NOME + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Evento.DATA_INICIO + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Evento.DATA_FIM + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Encontro._ID + ") "+
                " )";

    /**
     * Criação da tabela Encontro
     */
    private static final String SQL_CREATE_TABLE_ENCONTRO =
            "CREATE TABLE " + DadosContract.Encontro.TABLE_NAME + " (" +
                    DadosContract.Encontro._ID + " INTEGER PRIMARY KEY, " +
                    DadosContract.Encontro.EVENTO_ID + " INT " + COMMA_SEP +
                    DadosContract.Encontro.DATA + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Encontro.HORA + TEXT_TYPE + COMMA_SEP +
                    DadosContract.Encontro.DESCRICAO + TEXT_TYPE +
                    " FOREIGN KEY (" + DadosContract.Encontro.EVENTO_ID + ") " +
                    "REFERENCES " + DadosContract.Evento.TABLE_NAME + " (" +
                    " )";

    /**
     * Criação da tabela intermediaria PessoaEncontro
     */
    private static final String SQL_CREATE_TABLE_PESSOAENCONTRO =
            "CREATE TABLE " + DadosContract.PessoaEncontro.TABLE_NAME + " (" +
                    DadosContract.PessoaEncontro._ID + " INTEGER PRIMARY KEY, " +
                    DadosContract.PessoaEncontro.EVENTO_ID + " INT " + COMMA_SEP +
                    DadosContract.PessoaEncontro.HORA_CHEGADA + TEXT_TYPE + COMMA_SEP +
                    DadosContract.PessoaEncontro.HORA_SAIDA + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY (" + DadosContract.PessoaEncontro.EVENTO_ID + ") " +
                    "REFERENCES " + DadosContract.Evento.TABLE_NAME + " (" +
                    DadosContract.Evento._ID + ") "+
        " )";

    /**
     * Delete das tabelas
     */
    private static final String SQL_DELETE_TABLE_PESSOA =
           "DROP TABLE IF EXISTS " + DadosContract.Pessoa.TABLE_NAME;
    private static final String SQL_DELETE_TABLE_EVENTO =
            "DROP TABLE IF EXISTS " + DadosContract.Evento.TABLE_NAME;
    private static final String SQL_DELETE_TABLE_ENCONTRO =
            "DROP TABLE IF EXISTS " + DadosContract.Encontro.TABLE_NAME;
    private static final String SQL_DELETE_TABLE_PESSOAENCONTRO=
            "DROP TABLE IF EXISTS " + DadosContract.PessoaEncontro.TABLE_NAME;


    // Se você modificar o schema do banco, você deve incrementar a versão do software.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TRABALHO.db";


    public DadosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Criação das tabelas
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PESSOA);
        db.execSQL(SQL_CREATE_TABLE_EVENTO);
        db.execSQL(SQL_CREATE_TABLE_ENCONTRO);
        db.execSQL(SQL_CREATE_TABLE_PESSOAENCONTRO);
        //db.execSQL(SQL_DELETE_TABLE_PESSOAENCONTRO);
       // db.execSQL(SQL_DELETE_TABLE_EVENTO);
       // db.execSQL(SQL_DELETE_TABLE_PESSOA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_PESSOAENCONTRO);
        db.execSQL(SQL_DELETE_TABLE_EVENTO);
        db.execSQL(SQL_DELETE_TABLE_PESSOA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
