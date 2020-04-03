package br.com.miguelwolf.controletreinamentopessoal.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.miguelwolf.controletreinamentopessoal.R;
import br.com.miguelwolf.controletreinamentopessoal.model.Treino;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private ArrayAdapter aaExercicios;

    private CheckBox chkSeg, chkTer, chkQua, chkQui, chkSex, chkSab, chkDom;

    private EditText etNome,
            etPassoPasso,
                        etDescricao,
                        etTempoRepeticoes;

    private Spinner spExercicios;

    private RadioGroup rg;
    private RadioButton rbTempo,
                            rbRepeticoes,
                            rbLivre;

    public static final String CADASTRO_RESULTADO = "cr";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ((TextView) findViewById(R.id.toolbar_tv_titulo)).setText(getString(R.string.cadastro));
        findViewById(R.id.toolbar_iv_voltar).setOnClickListener(this);

        findViewById(R.id.cadastro_btn_salvar).setOnClickListener(this);
        findViewById(R.id.cadastro_btn_limpar).setOnClickListener(this);

        etNome = findViewById(R.id.cadastro_et_nome);
        etPassoPasso = findViewById(R.id.cadastro_et_passo_a_passo);
        etDescricao = findViewById(R.id.cadastro_et_descricao);
        etTempoRepeticoes = findViewById(R.id.cadastro_et_tempo_repeticoes);

        rg = findViewById(R.id.cadastro_rg_forma_contar);
        rg.setOnCheckedChangeListener(this);

        rbTempo = findViewById(R.id.cadastro_rb_tempo);
        rbRepeticoes = findViewById(R.id.cadastro_rb_repeticoes);
        rbLivre = findViewById(R.id.cadastro_rb_livre);

//        rbTempo.setChecked(true);
        etTempoRepeticoes.setHint(getString(R.string.tempo_min));

        chkSeg = findViewById(R.id.cadastro_cb_seg);
        chkTer = findViewById(R.id.cadastro_cb_ter);
        chkQua = findViewById(R.id.cadastro_cb_qua);
        chkQui = findViewById(R.id.cadastro_cb_qui);
        chkSex = findViewById(R.id.cadastro_cb_sex);
        chkSab = findViewById(R.id.cadastro_cb_sab);
        chkDom = findViewById(R.id.cadastro_cb_dom);


        spExercicios = findViewById(R.id.cadastro_sp_exercicios);
        spExercicios.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        aaExercicios = new ArrayAdapter(CadastroActivity.this, android.R.layout.simple_spinner_item, Treino.EXERCICIOS_ARRAY);
        aaExercicios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spExercicios.setAdapter(aaExercicios);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.toolbar_iv_voltar:

                onBackPressed();

                break;

            case R.id.cadastro_btn_salvar:

                if (verificarCampos()) {

                    Treino t = new Treino();

                    t.setNome(etNome.getText().toString());
                    t.setPassoAPasso(etPassoPasso.getText().toString());
                    t.setDescricao(etDescricao.getText().toString());
                    t.setTempoRepeticoes(etTempoRepeticoes.getText().toString());

                    t.setTipoExercícios(spExercicios.getSelectedItemPosition());

                    t.setSeg(chkSeg.isChecked() ? 1 : 0);
                    t.setTer(chkTer.isChecked() ? 1 : 0);
                    t.setQua(chkQua.isChecked() ? 1 : 0);
                    t.setQui(chkQui.isChecked() ? 1 : 0);
                    t.setSex(chkSex.isChecked() ? 1 : 0);
                    t.setSab(chkSab.isChecked() ? 1 : 0);
                    t.setDom(chkDom.isChecked() ? 1 : 0);

                    Intent resultIntent = new Intent();
                    Bundle b = new Bundle();
                    b.putParcelable(CADASTRO_RESULTADO, t);
                    resultIntent.putExtras(b);
                    setResult(RESULT_OK, resultIntent);
                    finish();

//                    mListReserva.add(r);

                    Toast.makeText(CadastroActivity.this, getString(R.string.dados_salvos), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroActivity.this, getString(R.string.campos_obrigatorios_vazios), Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.cadastro_btn_limpar:

                etNome.setText("");
                etDescricao.setText("");
                etPassoPasso.setText("");
                etTempoRepeticoes.setText("");

                rbLivre.setChecked(false);
                rbRepeticoes.setChecked(false);
                rbTempo.setChecked(false);

                etTempoRepeticoes.setHint(getString(R.string.tempo_min));

                chkSeg.setChecked(false);
                chkTer.setChecked(false);
                chkQua.setChecked(false);
                chkQui.setChecked(false);
                chkSex.setChecked(false);
                chkSab.setChecked(false);
                chkDom.setChecked(false);

                spExercicios.setSelection(0);

                Toast.makeText(CadastroActivity.this, getString(R.string.campos_limpados), Toast.LENGTH_SHORT).show();

                break;

            default:
                break;

        }

    }


    private boolean verificarCampos(){

        boolean valido = true;

        if (etNome.getText().toString().trim().isEmpty()) {
            etNome.requestFocus();
            etNome.setError(getString(R.string.campo_obrigatório));
            valido = false;
        }

        if (etPassoPasso.getText().toString().trim().isEmpty()) {
            etPassoPasso.requestFocus();
            etPassoPasso.setError(getString(R.string.campo_obrigatório));
            valido = false;
        }

        if (etDescricao.getText().toString().trim().isEmpty()) {
            etDescricao.requestFocus();
            etDescricao.setError(getString(R.string.campo_obrigatório));
            valido = false;
        }

        if (etTempoRepeticoes.getText().toString().trim().isEmpty()) {
            etTempoRepeticoes.requestFocus();
            etTempoRepeticoes.setError(getString(R.string.campo_obrigatório));
            valido = false;
        }

        if (!rbTempo.isChecked() && !rbRepeticoes.isChecked() && !rbLivre.isChecked()) {
            valido = false;
        }

        return valido;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (i == R.id.cadastro_rb_tempo) {
            etTempoRepeticoes.setHint(getString(R.string.tempo_min));

        } else if (i == R.id.cadastro_rb_repeticoes) {
            etTempoRepeticoes.setHint(getString(R.string.repeticoes));

        } else if (i == R.id.cadastro_rb_livre) {
            etTempoRepeticoes.setHint("");
        }


    }
}
