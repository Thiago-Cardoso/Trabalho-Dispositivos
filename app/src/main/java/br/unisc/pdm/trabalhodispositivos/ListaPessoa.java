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

import java.util.Hashtable;
import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;
import br.unisc.pdm.trabalhodispositivos.vo.Variaveis;


public class ListaPessoa extends ActionBarActivity implements PessoaTela {
    private PessoaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
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

    public void popularView(List<PessoaVO> values){
                       ArrayAdapter<PessoaVO> adapter = new ArrayAdapter<PessoaVO>(this, android.R.layout.simple_list_item_1, values);

                final ListView lista = (ListView) findViewById(R.id.lista_pessoas);


                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                            PessoaVO p = (PessoaVO) lista.getItemAtPosition(position);

                            Intent intent = new Intent(getBaseContext(), VerPessoa.class);
                            intent.putExtra("ID", p.getId_pessoa());
                            Toast.makeText(getBaseContext(), "Selecionado " + p.getNome(), Toast.LENGTH_SHORT).show();

                            startActivity(intent);
            }
        });


        lista.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_lista_pessoa, menu);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
