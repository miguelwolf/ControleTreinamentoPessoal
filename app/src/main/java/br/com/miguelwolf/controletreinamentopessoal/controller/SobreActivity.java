package br.com.miguelwolf.controletreinamentopessoal.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.miguelwolf.controletreinamentopessoal.R;

public class SobreActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        ((TextView) findViewById(R.id.toolbar_tv_titulo)).setText(getString(R.string.sobre));
        findViewById(R.id.toolbar_iv_voltar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_iv_voltar:
                onBackPressed();
                break;

            default:
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
