package br.edu.ifspsaocarlos.agenda.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Calendar;

import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.Util.Mask;


public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private ContatoDAO cDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //EXERCICO DATA DE NASCIMETNO
        //ADICION UM LISTERNET QUE IRÁ EXIBIR O CALENDÁRIO AO CLICAR NO EDIT TEXT
        final EditText dtNascText = (EditText) findViewById(R.id.editTexDtNasc);
        dtNascText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DetalheActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                DecimalFormat df = new DecimalFormat("00");
                                dtNascText.setText(df.format(day)  + "/" + df.format(month + 1));
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

            }
        });


        if (getIntent().hasExtra("contato"))
        {
            this.c = (Contato) getIntent().getSerializableExtra("contato");
            EditText nameText = (EditText)findViewById(R.id.editTextNome);
            nameText.setText(c.getNome());
            EditText foneText = (EditText)findViewById(R.id.editTextFone);
            foneText.setText(c.getFone());
            EditText foneText2 = (EditText)findViewById(R.id.editTextFone2);
            foneText2.setText(c.getFone2());
            EditText emailText = (EditText)findViewById(R.id.editTextEmail);
            emailText.setText(c.getEmail());
            dtNascText.setText(c.getDtNasc());


            int pos =c.getNome().indexOf(" ");
            if (pos==-1)
                pos=c.getNome().length();
            setTitle(c.getNome().substring(0,pos));
        }
        cDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato"))
        {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar()
    {
        cDAO.apagaContato(c);

        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        String name = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String dtNasc = ((EditText) findViewById(R.id.editTexDtNasc)).getText().toString();

        if (c==null)
            c = new Contato();


        c.setNome(name);
        c.setFone(fone);
        c.setFone2(fone2);
        c.setEmail(email);
        c.setDtNasc(dtNasc);

        cDAO.salvaContato(c);
        //c.setId(10);
        //ContatoAdapter.Adiciona(c);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}

