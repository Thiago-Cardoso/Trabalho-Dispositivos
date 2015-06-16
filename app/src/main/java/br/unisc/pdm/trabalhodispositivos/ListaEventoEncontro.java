package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EncontroDAO;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EncontroVO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;

public class ListaEventoEncontro extends ActionBarActivity implements EncontroTela{
    private EncontroDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_evento_encontro);

        popularList();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        popularList();
    }

    private void popularList(){
        if(EncontroVO.STORE_MODE.equals("DB"))
        {
            popularListFromDB();
        }
    }

    private void popularListFromDB() {
        dao = new EncontroDAO(this);
        dao.open();

        List<EncontroVO> values = dao.getAllEncontros();
        popularView(values);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_evento_encontro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_accept_encontro) {
            startActivity(new Intent(this, FormEvento.class));
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popularView(List<EncontroVO> values) {
        ArrayAdapter<EncontroVO> adapter = new ArrayAdapter<EncontroVO>(this, android.R.layout.simple_list_item_1, values);

        final ListView lista = (ListView) findViewById(R.id.lista_eventos_encontro2);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                EncontroVO v = (EncontroVO) lista.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(),VerEvento.class);
                intent.putExtra("ID", v.getId_encontro());
                Toast.makeText(getBaseContext(), "Selecionado " + v.getDescricao(), Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }
        });
        lista.setAdapter(adapter);
    }
}
