package br.unisc.pdm.trabalhodispositivos;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.unisc.pdm.designcrud.R;
import br.unisc.pdm.trabalhodispositivos.dao.EventoDAO;
import br.unisc.pdm.trabalhodispositivos.dao.PessoaDAO;
import br.unisc.pdm.trabalhodispositivos.vo.EventoVO;
import br.unisc.pdm.trabalhodispositivos.vo.PessoaVO;

public class FormPessoa extends ActionBarActivity implements PessoaTela ,AdapterView.OnItemSelectedListener,BlankFragment.OnFragmentInteractionListener{
    private PessoaDAO dao;
    private EventoDAO daoEvent;
    // Spinner element
    Spinner spinner;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bundle foto;
    private BlankFragment fragment;
    static String path;       //string com caminho da imagem salva no filesystem do device
    private File arquivo;
    byte[] byteArray=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoa);

        fragment = new BlankFragment();

        android.app.FragmentManager fm = this.getFragmentManager();

        FragmentTransaction _trans = fm.beginTransaction();
        _trans.replace(R.id.FrameLayoutFragment, fragment);
        _trans.commit();


        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Log.d("DC","buscou o id "+id );

        dao = new PessoaDAO(this);
        dao.open();

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner_p);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Loading spinner data from database
        loadSpinnerData();


        if (id > 0) {
            if (PessoaVO.STORE_MODE.equals("DB")) {
                PessoaVO p = dao.getPessoaById(id);
                populaTela(p);
            }
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();


    }

    public void TirarFoto(View view)
    {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            foto = data.getExtras();
            Bitmap imageBitmap = (Bitmap) foto.get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

            fragment.SetImage(imageBitmap);

            /*try {

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                arquivo = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "ft.jpg");

                if (arquivo.exists())
                {
                    arquivo.delete();
                }

                arquivo.createNewFile();

                FileOutputStream fo = new FileOutputStream(arquivo);
                fo.write(bytes.toByteArray());
                fo.close();


            }
            catch (IOException e) {
                e.printStackTrace();
            }
*/
        }
    }

    private void loadSpinnerData() {
        daoEvent = new EventoDAO(this);
        daoEvent.open();
        List<EventoVO> lables = daoEvent.getAllEventos();

        // Creating adapter for spinner
        ArrayAdapter<EventoVO> dataAdapter = new ArrayAdapter<EventoVO>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void populaTela(PessoaVO p){
    //    EditText edit_id = (EditText) findViewById(R.id.edit_id);
        EditText edit_nome = (EditText) findViewById(R.id.edit_nome);
        EditText edit_email = (EditText) findViewById(R.id.edit_email);
        EditText edit_idade = (EditText) findViewById(R.id.edit_idade);
        EditText edit_matricula = (EditText) findViewById(R.id.edit_matricula);
       // edit_id.setText(String.valueOf(p.getId_pessoa()));
        edit_nome.setText(p.getNome());
        edit_email.setText(p.getEmail());
        edit_idade.setText(p.getIdade());
        edit_matricula.setText(p.getMatricula());
        //if(p.getFoto()!=null) {
          //  Bitmap bmp = BitmapFactory.decodeByteArray(p.getFoto(), 0, p.getFoto().length);
           // if(bmp!=null){
           //     fragment.SetImage(bmp);

           // }
      //  }
    }

    public void insertOrEditPerson(){
        //Buscando dados de entrada digitados pelo usuário
      //  EditText edit_id = (EditText) findViewById(R.id.edit_id);
        EditText edit_nome = (EditText) findViewById(R.id.edit_nome);
        EditText edit_email = (EditText) findViewById(R.id.edit_email);
        EditText edit_idade = (EditText) findViewById(R.id.edit_idade);
        EditText edit_matricula = (EditText) findViewById(R.id.edit_matricula);

        PessoaVO person = new PessoaVO();
      //  if(edit_id.getText().toString().length() > 0)
         //   person.setId_pessoa(Integer.parseInt(edit_id.getText().toString()));
        person.setNome(edit_nome.getText().toString());
        person.setEmail(edit_email.getText().toString());
        person.setIdade(edit_idade.getText().toString());
        person.setMatricula(edit_matricula.getText().toString());
        if(byteArray!=null) {
            Log.d("DC", "foto diferente de null");
            person.setFoto(byteArray);
        }
        if(PessoaVO.STORE_MODE.equals("DB")){
            if(person.getId_pessoa() > 0)
                dao.updatePessoa(person);
            else
                dao.insertPessoa(person);
        }
        byteArray=null;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
