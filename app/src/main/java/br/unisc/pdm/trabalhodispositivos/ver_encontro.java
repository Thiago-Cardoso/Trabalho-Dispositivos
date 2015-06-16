package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EncontroDAO;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EncontroVO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;

public class ver_encontro extends ActionBarActivity implements EncontroTela{
    private EncontroDAO dao;
    static EncontroVO v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_encontro);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC", "buscou o id " + id);

        dao = new EncontroDAO(this);
        dao.open();

        if (id > 0) {
            if(EncontroVO.STORE_MODE.equals("DB")) {
                v = dao.getEncontroId(id);
                populaTela();
            }
        }
    }

    private void populaTela() {
        TextView ve_data = (TextView) findViewById(R.id.ve_data);
        TextView ve_hora = (TextView) findViewById(R.id.ve_hora);
        TextView ve_descricao = (TextView) findViewById(R.id.ve_descricao);
        TextView ve_id = (TextView) findViewById(R.id.ve_id);
        ve_id.setText(String.valueOf(v.getId_encontro()));
        ve_data.setText(String.valueOf(v.getData()));
        ve_hora.setText(v.getHora());
        ve_descricao.setText(v.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_encontro, menu);
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
    public void popularView(List<EncontroVO> values) {
        this.v = values.get(0);
        populaTela();
        Log.d("WBS", "Populando tela" + values.toString());
    }
}
