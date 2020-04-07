package br.com.miguelwolf.controletreinamentopessoal.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import br.com.miguelwolf.controletreinamentopessoal.R;
import br.com.miguelwolf.controletreinamentopessoal.utils.AppPrefs;
import br.com.miguelwolf.controletreinamentopessoal.utils.Constants;

public class PreferenciasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private AppPrefs session;

    private ArrayAdapter aaOrdemLista, aaExibidosVez;

    private Spinner spOrdemLIsta, spExibidosVez;

    private Switch swModoEscuro, swExemploTreino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        session = new AppPrefs(this);


        setTitle(getString(R.string.preferencias));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


//        /** ORDEM DA LISTA  -------------------------------------------------------------------- **/
//        spOrdemLIsta = findViewById(R.id.preferencias_sp_ordem_lista);
//        spOrdemLIsta.setOnItemSelectedListener(this);
//
//        //Creating the ArrayAdapter instance having the country list
//        aaOrdemLista = new ArrayAdapter(PreferenciasActivity.this, android.R.layout.simple_spinner_item, Constants.ORDEM_LISTA_PARAMS);
//        aaOrdemLista.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spOrdemLIsta.setAdapter(aaOrdemLista);
//
//        spOrdemLIsta.setSelection(session.getOrdemLista());


        /** ITENS EXIBIDOS POR VEZ  ------------------------------------------------------------ **/
        spExibidosVez = findViewById(R.id.preferencias_sp_exibicao_vez);
        spExibidosVez.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        aaExibidosVez = new ArrayAdapter(PreferenciasActivity.this, android.R.layout.simple_spinner_item, Constants.ITENS_EXIBICAO_VEZ_PARAMS);
        aaExibidosVez.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spExibidosVez.setAdapter(aaExibidosVez);

        spExibidosVez.setSelection(session.getItensExibicaoVez());


        /** MODO ESCURO ------------------------------------------------------------------------ **/
        swModoEscuro = findViewById(R.id.preferencias_sw_dark);
        swModoEscuro.setOnClickListener(this);
        if (session.isDark())
            swModoEscuro.setChecked(true);
        else
            swModoEscuro.setChecked(false);

        /** FECHAR AO SALVAR ----------------------------------------------------------------------- **/
        swExemploTreino = findViewById(R.id.preferencias_sw_exemplo_treino);
        swExemploTreino.setOnClickListener(this);
        swExemploTreino.setChecked(session.isExemploTreino());


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {

//            case R.id.preferencias_sp_ordem_lista:
//                session.setOrdemLista(i);
//                break;

            case R.id.preferencias_sp_exibicao_vez:
                session.setItensExibicaoVez(i);
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.preferencias_sw_dark:
                if (swModoEscuro.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    session.setDark(true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    session.setDark(false);
                }
                break;

            case R.id.preferencias_sw_exemplo_treino:
                session.setExemploTreino(swExemploTreino.isChecked());
                break;

            default:
                break;

        }
    }
}
