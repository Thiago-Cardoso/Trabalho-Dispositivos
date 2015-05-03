package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;

public class ListaEvento extends ActionBarActivity implements EventoTela {
    private EventoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_evento);

        popularList();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        popularList();
    }

    private void popularList(){
        if(EventoVO.STORE_MODE.equals("DB"))
        {
            popularListFromDB();
        }
    }

    public void popularView(List<EventoVO> values){
        ArrayAdapter<EventoVO> adapter = new ArrayAdapter<EventoVO>(this, android.R.layout.simple_list_item_1, values);

        final ListView lista = (ListView) findViewById(R.id.lista_eventos);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                EventoVO v = (EventoVO) lista.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(),VerEvento.class);
                intent.putExtra("ID", v.getId_evento());
                Toast.makeText(getBaseContext(), "Selecionado " + v.getNome(), Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }
        });


        lista.setAdapter(adapter);
    }

    private void popularListFromDB(){
        dao = new EventoDAO(this);
        dao.open();

        List<EventoVO> values = dao.getAllEventos();
        popularView(values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_new_evento) {
            startActivity(new Intent(this, FormEvento.class));
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
