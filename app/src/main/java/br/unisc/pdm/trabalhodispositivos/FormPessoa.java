package br.unisc.pdm.trabalhodispositivos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;


public class FormPessoa extends ActionBarActivity implements PessoaTela {
    private PessoaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoa);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC","buscou o id "+id );

        dao = new PessoaDAO(this);
        dao.open();


        if (id > 0) {
            if (PessoaVO.STORE_MODE.equals("DB")) {
                PessoaVO p = dao.getPessoaById(id);
                populaTela(p);
            }
        }
    }

    public void populaTela(PessoaVO p){
        EditText edit_id = (EditText) findViewById(R.id.edit_id);
        EditText edit_nome = (EditText) findViewById(R.id.edit_nome);
        EditText edit_email = (EditText) findViewById(R.id.edit_email);
        EditText edit_idade = (EditText) findViewById(R.id.edit_idade);
        EditText edit_matricula = (EditText) findViewById(R.id.edit_matricula);
        edit_id.setText(String.valueOf(p.getId_pessoa()));
        edit_nome.setText(p.getNome());
        edit_email.setText(p.getEmail());
        edit_idade.setText(p.getIdade());
        edit_matricula.setText(p.getMatricula());

    }

    public void insertOrEditPerson(){
        //Buscando dados de entrada digitados pelo usuário
        EditText edit_id = (EditText) findViewById(R.id.edit_id);
        EditText edit_nome = (EditText) findViewById(R.id.edit_nome);
        EditText edit_email = (EditText) findViewById(R.id.edit_email);
        EditText edit_idade = (EditText) findViewById(R.id.edit_idade);
        EditText edit_matricula = (EditText) findViewById(R.id.edit_matricula);

        PessoaVO person = new PessoaVO();
        if(edit_id.getText().toString().length() > 0)
            person.setId_pessoa(Integer.parseInt(edit_id.getText().toString()));
        person.setNome(edit_nome.getText().toString());
        person.setEmail(edit_email.getText().toString());
        person.setIdade(edit_idade.getText().toString());
        person.setMatricula(edit_matricula.getText().toString());

        if(PessoaVO.STORE_MODE.equals("DB")){
            if(person.getId_pessoa() > 0)
                dao.updatePessoa(person);
            else
                dao.insertPessoa(person);
        }
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_pessoa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_accept_pessoa){
            insertOrEditPerson();
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void popularView(List<PessoaVO> values) {
        populaTela(values.get(0));
        Log.d("WBS", values.toString());
        Toast.makeText(this,"Voltou.. populando!",Toast.LENGTH_SHORT).show();
    }
}