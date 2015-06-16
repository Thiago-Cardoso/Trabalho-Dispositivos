package br.unisc.pdm.trabalhodispositivos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EncontroDAO;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EncontroVO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;

public class FormEncontro extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private EncontroDAO daoncontro;
    private EventoDAO daoEvent;
    private TextView  txtTime;
    static int idEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_encontro);

        Intent intent = getIntent();
        int idev = intent.getIntExtra("codigo", 0);
        int id = intent.getIntExtra("ID", 0);
        idEvento = idev;
       // String nome = intent.getStringExtra("nome");
        Log.d("DC", "buscou o id " + id);

        daoncontro = new EncontroDAO(this);
        daoncontro.open();

        if (id > 0) {
            if (EncontroVO.STORE_MODE.equals("DB")) {
                EncontroVO v = daoncontro.getEncontroId(id);
                populaTela(v);
            }
        }
    }

    public void populaTela(EncontroVO v){
        EditText edit_id_encontro = (EditText) findViewById(R.id.edit_id_encontro);
        EditText edit_descricao = (EditText) findViewById(R.id.edit_descricao);
        EditText edit_data = (EditText) findViewById(R.id.edit_data);
        EditText edit_time = (EditText) findViewById(R.id.edit_time);
        edit_id_encontro.setText(String.valueOf(v.getId_encontro()));
        edit_descricao.setText(String.valueOf(v.getDescricao()));
        edit_data.setText(v.getData());
        edit_time.setText(v.getHora());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_encontro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_accept_encontro){
            insertOrEditEncontro();
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void insertOrEditEncontro(){
        //Buscando dados de entrada digitados pelo usuário
        EditText edit_id_encontro = (EditText) findViewById(R.id.edit_id_encontro);
        EditText edit_descricao = (EditText) findViewById(R.id.edit_descricao);
        EditText edit_data = (EditText) findViewById(R.id.edit_data);
        EditText edit_time = (EditText) findViewById(R.id.edit_time);

        EncontroVO encontro = new EncontroVO();
        if(edit_id_encontro.getText().toString().length() > 0)
            //encontro.setId_encontro(Integer.parseInt(edit_id_encontro.getText().toString()));
        encontro.setId_encontro(Integer.parseInt(edit_id_encontro.getText().toString()));
       // evento.setId_evento(Integer.parseInt(edit_id_evento.getText().toString()));
        encontro.setId(idEvento);
        encontro.setDescricao(edit_descricao.getText().toString());
        encontro.setData(edit_data.getText().toString());
        encontro.setHora(edit_time.getText().toString());

        if(EncontroVO.STORE_MODE.equals("DB")){
            if(encontro.getId() > 0) {
                daoncontro.insertEncontro(encontro);
            }else {
                daoncontro.updateEncontro(encontro);
                //Toast.makeText(getApplicationContext(), formattedCurrentDate_inicio+"data", Toast.LENGTH_SHORT).show();
            }
        }
        finish();

    }
}
