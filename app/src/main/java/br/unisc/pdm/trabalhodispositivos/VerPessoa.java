package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;


public class VerPessoa extends ActionBarActivity implements PessoaTela {
    private PessoaDAO dao;
    private PessoaVO p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pessoa);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC", "buscou o id " + id);

        dao = new PessoaDAO(this);
        dao.open();

        if (id > 0) {
            if(PessoaVO.STORE_MODE.equals("DB")) {
                p = dao.getPessoaById(id);
                populaTela();
            }
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(PessoaVO.STORE_MODE.equals("DB")) {
            p = dao.getPessoaById(p.getId_pessoa());
            populaTela();
        }
    }
    private void populaTela() {
        TextView vw_id = (TextView) findViewById(R.id.vw_id);
        TextView vw_nome = (TextView) findViewById(R.id.vw_nome);
        TextView vw_email = (TextView) findViewById(R.id.vw_email);
        TextView vw_idade = (TextView) findViewById(R.id.vw_idade);
        TextView vw_matricula = (TextView) findViewById(R.id.vw_matricula);
        vw_id.setText(String.valueOf(p.getId_pessoa()));
        vw_nome.setText(p.getNome());
        vw_email.setText(p.getEmail());
        vw_idade.setText(p.getIdade());
        vw_matricula.setText(p.getMatricula());
    }


    public void ApagarClick (View view)
    {
        Log.d("EXCLUIR", "buscou o id " + p.getId_pessoa());
        if(p.getId_pessoa() > 0)
        {
            dao.deletePessoa(p);
            populaTela();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_pessoa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_new_pessoa) {
            startActivity(new Intent(this, FormPessoa.class));
        }
        if (id == R.id.action_edit_pessoa) {
            Intent intent = new Intent(this,FormPessoa.class);
            intent.putExtra("ID",p.getId_pessoa());
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popularView(List<PessoaVO> values) {
        this.p = values.get(0);
        populaTela();
        Log.d("WBS", "Populando tela" + values.toString());
    }
}
