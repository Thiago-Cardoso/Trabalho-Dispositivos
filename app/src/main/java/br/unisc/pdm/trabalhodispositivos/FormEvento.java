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
    private TextView mDateDisplay,mDateDisplay_fim;
    private Button mPickDate,mPickDate_fim;

    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento);


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

        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);

        mDateDisplay_fim = (TextView) findViewById(R.id.dateDisplay_fim);
        mPickDate_fim = (Button) findViewById(R.id.pickDate_fim);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // add a click listener to the button
        mPickDate_fim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date (this method is below)
        updateDisplay();
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));

        mDateDisplay_fim.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };


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

    public void AdicionarPessoa(View view)
    {
        Variaveis.VENHO_TELA_EVENTO = true;
        startActivity(new Intent(this,ListaPessoa.class));
    }

    @Override
    public void popularView(List<EventoVO> values) {

        populaTela(values.get(0));
        Log.d("WBS", values.toString());
        Toast.makeText(this, "Voltou.. populando!", Toast.LENGTH_SHORT).show();
    }

    public void populaTela(EventoVO v){
        EditText edit_id_evento = (EditText) findViewById(R.id.edit_id_evento);
        EditText edit_nome_evento = (EditText) findViewById(R.id.edit_nome_evento);
        edit_id_evento.setText(String.valueOf(v.getId_evento()));
        edit_nome_evento.setText(v.getNome());
    }

    public void insertOrEditEvento(){
        //Buscando dados de entrada digitados pelo usuário
        EditText edit_id_evento = (EditText) findViewById(R.id.edit_id_evento);
        EditText edit_nome_evento = (EditText) findViewById(R.id.edit_nome_evento);

        EventoVO evento = new EventoVO();
        if(edit_id_evento.getText().toString().length() > 0)
            evento.setId_evento(Integer.parseInt(edit_id_evento.getText().toString()));
        evento.setNome(edit_nome_evento.getText().toString());
        Date data_inicio = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String formattedCurrentDate_inicio = simpleDateFormat.format(data_inicio);
        Date data_fim = Calendar.getInstance().getTime();
        simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate_fim = simpleDateFormat.format(data_fim);

        evento.setData_inicio(data_inicio);

        if(EventoVO.STORE_MODE.equals("DB")){
            if(evento.getId_evento() > 0) {
                dao.updateEvento(evento);
            }else {
                //Toast.makeText(getApplicationContext(), formattedCurrentDate+"data", Toast.LENGTH_SHORT).show();
                dao.insertEvento(evento);
            }
        }
        finish();

    }
}
