package br.com.miguelwolf.controletreinamentopessoal.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.miguelwolf.controletreinamentopessoal.R;
import br.com.miguelwolf.controletreinamentopessoal.model.Treino;
import br.com.miguelwolf.controletreinamentopessoal.persistencia.TreinoDatabase;
import br.com.miguelwolf.controletreinamentopessoal.utils.AppPrefs;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private AppPrefs session;

    private ArrayAdapter aaExercicios;

    private CheckBox chkSeg, chkTer, chkQua, chkQui, chkSex, chkSab, chkDom;

    private EditText etNome,
            etPassoPasso,
            etDescricao,
            etTempoRepeticoes;

    private int modo;

    private Spinner spExercicios;

    private RadioGroup rg;
    private RadioButton rbTempo,
            rbRepeticoes,
            rbLivre;

    private Treino treino;

    public static final String CADASTRO_RESULTADO = "cr";
    public static final String MODO = "MODO";
    public static final String TREINO = "TRE";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        session = new AppPrefs(this);

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


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {

                if (session.isExemploTreino())
                    inserirExemplo();

                setTitle(getString(R.string.cadastro));

            } else if (modo == ALTERAR) {
                treino = bundle.getParcelable(TREINO);

                try {
                    etNome.setText(treino.getNome());
                    etPassoPasso.setText(treino.getPassoAPasso());
                    etDescricao.setText(treino.getDescricao());
                    etTempoRepeticoes.setText(treino.getTempoRepeticoes());

                    rbTempo.setChecked(treino.getTipoTempo() == Treino.TIPO_TEMPO_TEMPO);
                    rbRepeticoes.setChecked(treino.getTipoTempo() == Treino.TIPO_TEMPO_REPETICOES);
                    rbLivre.setChecked(treino.getTipoTempo() == Treino.TIPO_TEMPO_LIVRE);

                    etTempoRepeticoes.setText(treino.getTempoRepeticoes());

                    spExercicios.setSelection(treino.getTipoExercicios());

                    chkSeg.setChecked(treino.getSeg() == 1);
                    chkTer.setChecked(treino.getTer() == 1);
                    chkQua.setChecked(treino.getQua() == 1);
                    chkQui.setChecked(treino.getQui() == 1);
                    chkSex.setChecked(treino.getSex() == 1);
                    chkSab.setChecked(treino.getSab() == 1);
                    chkDom.setChecked(treino.getDom() == 1);


                    spExercicios = findViewById(R.id.cadastro_sp_exercicios);

                    setTitle(getString(R.string.alterar_treino));

                } catch (NullPointerException npe) {
                    finish();
                }
            } else {
                finish();
            }
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    private void inserirExemplo() {
        etNome.setText(getString(R.string.exemplo_nome));
        etPassoPasso.setText(getString(R.string.exemplo_passo_passo));
        etDescricao.setText(getString(R.string.exemplo_descricao));
        etTempoRepeticoes.setText(getString(R.string.exemplo_tempo_repeticoes));
        rbTempo.setChecked(true);

        spExercicios.setSelection(0);

        chkSeg.setChecked(true);
        chkQua.setChecked(true);
        chkSex.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_cadastro_salvar:
                if (verificarCampos()) {

                    Treino t = new Treino();

                    if (treino != null)
                        t.setId(treino.getId());

                    t.setNome(etNome.getText().toString());
                    t.setPassoAPasso(etPassoPasso.getText().toString());
                    t.setDescricao(etDescricao.getText().toString());
                    t.setTempoRepeticoes(etTempoRepeticoes.getText().toString());

                    if (rbTempo.isChecked())
                        t.setTipoTempo(Treino.TIPO_TEMPO_TEMPO);
                    else if (rbRepeticoes.isChecked())
                        t.setTipoTempo(Treino.TIPO_TEMPO_REPETICOES);
                    else
                        t.setTipoTempo(Treino.TIPO_TEMPO_LIVRE);

                    t.setTipoExercicios(spExercicios.getSelectedItemPosition());

                    t.setSeg(chkSeg.isChecked() ? 1 : 0);
                    t.setTer(chkTer.isChecked() ? 1 : 0);
                    t.setQua(chkQua.isChecked() ? 1 : 0);
                    t.setQui(chkQui.isChecked() ? 1 : 0);
                    t.setSex(chkSex.isChecked() ? 1 : 0);
                    t.setSab(chkSab.isChecked() ? 1 : 0);
                    t.setDom(chkDom.isChecked() ? 1 : 0);

                    if (modo == NOVO) {
                        TreinoDatabase.getDatabase(this).treinoDAO().insert(t);
                    } else {
                        TreinoDatabase.getDatabase(this).treinoDAO().update(t);
                    }

                    Intent resultIntent = new Intent();
                    Bundle b = new Bundle();
                    b.putParcelable(CADASTRO_RESULTADO, t);
                    resultIntent.putExtras(b);
                    setResult(RESULT_OK, resultIntent);

                    if (session.isExemploTreino())
                        finish();

//                    mListReserva.add(r);

                    Toast.makeText(CadastroActivity.this, getString(R.string.dados_salvos), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroActivity.this, getString(R.string.campos_obrigatorios_vazios), Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.menu_cadastro_limpar:

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

            case android.R.id.home:
                setResult(RESULT_CANCELED);
                super.onBackPressed();
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public static void alterarTreino(AppCompatActivity activity, Treino treino) {

        Intent intent = new Intent(activity, CadastroActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(TREINO, treino);

        activity.startActivityForResult(intent, ALTERAR);
    }


    private boolean verificarCampos() {

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
