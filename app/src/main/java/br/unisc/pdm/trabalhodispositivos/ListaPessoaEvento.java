package br.unisc.pdm.trabalhodispositivos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.dao.EventoPessoaDAO;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaEventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;
import br.unisc.pdm.trabalhodispositivos.vo.Variaveis;

public class ListaPessoaEvento extends ActionBarActivity implements PessoaTela{
    private PessoaDAO dao;
    private EventoPessoaDAO evdao;
    Button getChoice;
    static int idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa_evento);

        Intent intent = getIntent();
        int idev = intent.getIntExtra("codigo", 0);
        idEvento = idev;
        // String nome = intent.getStringExtra("nome");
        Log.d("DC", "buscou o id " + idEvento);

        popularList();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        popularList();
    }

    private void popularList(){
        if(PessoaVO.STORE_MODE.equals("DB"))
        {
            popularListFromDB();
        }
    }

    private void popularListFromDB(){
        dao = new PessoaDAO(this);
        dao.open();

        List<PessoaVO> values = dao.getAllPessoas();
        popularView(values);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_pessoa_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popularView(List<PessoaVO> values) {

        ArrayAdapter<PessoaVO> adapter = new ArrayAdapter<PessoaVO>(this, android.R.layout.simple_list_item_multiple_choice, values);

        final ListView lista = (ListView) findViewById(R.id.listView1);

        getChoice = (Button)findViewById(R.id.getchoice);

        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lista.setAdapter(adapter);

        getChoice.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                int selected = 0;

                int cntChoice = lista.getCount();

                SparseBooleanArray sparseBooleanArray = lista.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)) {

                       // selected += lista.getItemAtPosition(i) + "\n";

                        salvaEventoPessoa(selected);

                    }
                }
                Toast.makeText(ListaPessoaEvento.this,

                        selected,

                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void salvaEventoPessoa(int selected){
        evdao = new EventoPessoaDAO(this);
        evdao.open();
        PessoaEventoVO pvo = new PessoaEventoVO();
        pvo.setFk_pessoa_id(selected);
        pvo.setFk_evento_id(idEvento);
        evdao.insertEvento(pvo);
    }

}
