package br.unisc.pdm.trabalhodispositivos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;
import br.unisc.pdm.trabalhodispositivos.vo.Variaveis;

public class FormEvento extends ActionBarActivity implements EventoTela
{
    private EventoDAO dao;
    private EventoVO v;
    private TextView mDateDisplay,mDateDisplay_fim;
    private Button mPickDate,mPickDate_fim;

    private EditText edit_id_evento;
    private int mYear;
    private int mMonth;
    private int mDay;


    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento);
         edit_id_evento = (EditText) findViewById(R.id.edit_id_evento);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC", "buscou o id " + id);

        dao = new EventoDAO(this);
        dao.open();


        if (id > 0) {
            if (EventoVO.STORE_MODE.equals("DB")) {
                EventoVO v = dao.getEventoById(id);
                populaTela(v);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_accept_evento){
            insertOrEditEvento();
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    public void AdicionarPessoa(View view)
    {
       startActivity(new Intent(this,ListaPessoaEvento.class));
        Variaveis.idEvendo=edit_id_evento.getText().toString();

    }*/

    @Override
    public void popularView(List<EventoVO> values) {

        populaTela(values.get(0));
        Log.d("WBS", values.toString());
        Toast.makeText(this, "Voltou.. populando!", Toast.LENGTH_SHORT).show();
    }

    public void populaTela(EventoVO v){
        EditText edit_id_evento = (EditText) findViewById(R.id.edit_id_evento);
        EditText edit_nome_evento = (EditText) findViewById(R.id.edit_nome_evento);
        EditText edit_data_inicio = (EditText) findViewById(R.id.edit_data_inicio);
        EditText edit_data_fim = (EditText) findViewById(R.id.edit_data_fim);
        edit_id_evento.setText(String.valueOf(v.getId_evento()));
        edit_nome_evento.setText(v.getNome());
        edit_data_inicio.setText(v.getData_inicio());
        edit_data_fim.setText(v.getData_fim());
    }

    public void insertOrEditEvento(){
        //Buscando dados de entrada digitados pelo usuário
        EditText edit_id_evento = (EditText) findViewById(R.id.edit_id_evento);
        EditText edit_nome_evento = (EditText) findViewById(R.id.edit_nome_evento);
        EditText edit_data_inicio = (EditText) findViewById(R.id.edit_data_inicio);
        EditText edit_data_fim = (EditText) findViewById(R.id.edit_data_fim);

        EventoVO evento = new EventoVO();
        if(edit_id_evento.getText().toString().length() > 0)
            evento.setId_evento(Integer.parseInt(edit_id_evento.getText().toString()));
        evento.setNome(edit_nome_evento.getText().toString());
        evento.setData_inicio(edit_data_inicio.getText().toString());
        evento.setData_fim(edit_data_fim.getText().toString());

        if(EventoVO.STORE_MODE.equals("DB")){
            if(evento.getId_evento() > 0) {
                dao.updateEvento(evento);
            }else {
                dao.insertEvento(evento);
                //Toast.makeText(getApplicationContext(), formattedCurrentDate_inicio+"data", Toast.LENGTH_SHORT).show();
            }
        }
        finish();

    }
}
