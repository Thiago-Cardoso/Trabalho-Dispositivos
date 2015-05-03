package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;

public class VerEvento extends ActionBarActivity implements EventoTela{
    private EventoDAO dao;
    private EventoVO v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_evento);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC", "buscou o id " + id);

        dao = new EventoDAO(this);
        dao.open();

        if (id > 0) {
            if(EventoVO.STORE_MODE.equals("DB")) {
                v = dao.getEventoById(id);
                populaTela();
            }
        }
    }

    private void populaTela() {
        TextView ve_id = (TextView) findViewById(R.id.ve_id);
        TextView ve_nome = (TextView) findViewById(R.id.ve_nome);
        ve_id.setText(String.valueOf(v.getId_evento()));
        ve_nome.setText(v.getNome());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_evento, menu);
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
        if (id == R.id.action_edit_evento) {
            Intent intent = new Intent(this,FormEvento.class);
            intent.putExtra("ID", v.getId_evento());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popularView(List<EventoVO> values) {
        this.v = values.get(0);
        populaTela();
        Log.d("WBS", "Populando tela" + values.toString());
    }
}