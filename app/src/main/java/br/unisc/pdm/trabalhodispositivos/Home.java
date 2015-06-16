package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.database.DadosContract;


public class Home extends ActionBarActivity {

    private String[] colunas = {
            DadosContract.Pessoa._ID,
            DadosContract.Pessoa.NOME,
            DadosContract.Pessoa.IDADE,
            DadosContract.Pessoa.EMAIL,
            DadosContract.Pessoa.MATRICULA,
            DadosContract.Pessoa.BLOB,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_list_pessoa) {
            startActivity(new Intent(this,ListaPessoa.class));
        }
        /*
        if (id == R.id.action_list_spinner) {
            startActivity(new Intent(this,spinner.class));
        }*/

        if (id == R.id.action_list_evento) {
            startActivity(new Intent(this,ListaEvento.class));
        }
        if (id == R.id.action_form_encontro) {
            startActivity(new Intent(this,FormEncontro.class));
        }

        if (id == R.id.action_list_encontro) {
            startActivity(new Intent(this,ListaEventoEncontro.class));
        }

        if (id == R.id.action_qr) {
            startActivity(new Intent(this,EncoderActivity.class));
        }

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_list_relatorio) {
            Log.d("DC", "Inicio geracao de relatorio ");
            String FILENAME = "log.csv";
            File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File logDir = new File(directoryDownload, FILENAME);/*
            try {


                DadosDbHelper mdb = new DadosDbHelper(this);
                SQLiteDatabase database = mdb.getWritableDatabase();;
                logDir.createNewFile();
                CSVWriter csvWriter = new CSVWriter(new FileWriter(logDir));
                    Cursor curCSV = database.query(DadosContract.Pessoa.TABLE_NAME,
                colunas, null, null, null, null, null);
                csvWriter.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = { curCSV.getString(1)+ ",", curCSV.getString(2)+ ",",
                            curCSV.getString(3)+""};
                    csvWriter.writeNext(arrStr);
                }
                csvWriter.close();
                curCSV.close();
                Toast.makeText(this, "Relatorio Inserido!", Toast.LENGTH_SHORT).show();
                return true;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);

            }*/
            Log.d("DC", "Final geracao de relatorio ");

        }


        return super.onOptionsItemSelected(item);
    }
}
