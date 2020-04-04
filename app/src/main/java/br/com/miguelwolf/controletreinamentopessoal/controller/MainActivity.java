package br.com.miguelwolf.controletreinamentopessoal.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import br.com.miguelwolf.controletreinamentopessoal.R;
import br.com.miguelwolf.controletreinamentopessoal.adapter.TreinosAdapter;
import br.com.miguelwolf.controletreinamentopessoal.interfaces.RecyclerViewOnClickListenerHack;
import br.com.miguelwolf.controletreinamentopessoal.model.Treino;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private ActionMode actionMode;

    private ArrayList<Treino> mListTreino = new ArrayList<>();
    private ArrayList<Treino> mLIstTreinoJSON = new ArrayList<>();

    private int posicaoSelecionada = -1;

    private TreinosAdapter adapterTreinos;

    private RecyclerView rvTreinos;

    private View viewSelecionada;

    public static int REQUEST_ADICIONAR = 0;


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.principal_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuItemAlterar:
                    alterarItem();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirItem();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            rvTreinos.setEnabled(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rvTreinos = findViewById(R.id.activity_rv);
        rvTreinos.setHasFixedSize(true);
        rvTreinos.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) rvTreinos.getLayoutManager();
                TreinosAdapter adapter = (TreinosAdapter) rvTreinos.getAdapter();

                if (mListTreino.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Treino> listAux = getSetTreinoList(25);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mListTreino.size());
                    }
                }
            }
        });

        adapterTreinos = new TreinosAdapter(MainActivity.this, mListTreino);
        adapterTreinos.setmRecyclerViewOnClickListenerHack(this);

        LinearLayoutManager llm2 = new LinearLayoutManager(MainActivity.this);
        llm2.setOrientation(RecyclerView.VERTICAL);
        rvTreinos.setLayoutManager(llm2);
        rvTreinos.setAdapter(adapterTreinos);


//        findViewById(R.id.main_adicionar).setOnClickListener(this);
//        findViewById(R.id.main_sobre).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_main_adicionar:
                startActivityForResult(new Intent(MainActivity.this, CadastroActivity.class), REQUEST_ADICIONAR);
                break;

            case R.id.menu_main_sobre:
                startActivity(new Intent(MainActivity.this, SobreActivity.class));
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mLIstTreinoJSON = Treino.getList(50);

        atualizarLista();

    }

    private void atualizarLista() {

        mListTreino = new ArrayList<>();
        mListTreino = getSetTreinoList(25);

        adapterTreinos = new TreinosAdapter(this, mListTreino);
        adapterTreinos.setmRecyclerViewOnClickListenerHack(this);

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(RecyclerView.VERTICAL);
        rvTreinos.setLayoutManager(llm2);
        rvTreinos.setAdapter(adapterTreinos);
        rvTreinos.getAdapter().notifyDataSetChanged();
        rvTreinos.invalidate();

    }


    /**
     * Método para geração de itens para a lista de produtos no carrinho
     *
     * @param qtd, utlizado para definir o tanto de elementos eu quero exibir por vez, 5 itens, 9 itens, 25 itens. Este valor não limita o máximo de itens da lista total
     * @return
     */
    public ArrayList<Treino> getSetTreinoList(int qtd) {

        ArrayList<Treino> listAux = new ArrayList<>();

        int posicaoPartida = 0;

        if (mListTreino.size() != 0) {
            posicaoPartida = mListTreino.size();
        }

        qtd += posicaoPartida;

        for (int i = posicaoPartida; i < qtd && i < mLIstTreinoJSON.size(); i++) {
            listAux.add(mLIstTreinoJSON.get(i));
        }

        return listAux;

    }


    @Override
    public void onClickListener(View v, int position) {

        Toast.makeText(MainActivity.this, "Treino: " + mListTreino.get(position).getNome(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onLongClick(View view, int position) {
        if (actionMode != null) {
            return false;
        }

        view.setBackgroundColor(Color.LTGRAY);

        viewSelecionada = view;

        rvTreinos.setEnabled(false);

        actionMode = startSupportActionMode(mActionModeCallback);

        posicaoSelecionada = position;

        return false;
    }

    private void excluirItem() {
        mLIstTreinoJSON.remove(posicaoSelecionada);
        atualizarLista();
    }

    private void alterarItem() {

        Treino t = mLIstTreinoJSON.get(posicaoSelecionada);

        CadastroActivity.alterarPessoa(this, t);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

//                Bundle bundle = data.getExtras();
//                Treino t = bundle.getParcelable(CadastroActivity.TREINO);

            try {
                Treino t = data.getParcelableExtra(CadastroActivity.CADASTRO_RESULTADO);

                if (requestCode == CadastroActivity.ALTERAR) {

                    mLIstTreinoJSON.set(posicaoSelecionada, t);
                    atualizarLista();
//                    Treino treino = listaPessoas.get(posicaoSelecionada);
//                    pessoa.setNome(nome);
                    posicaoSelecionada = -1;

                } else if ((requestCode == REQUEST_ADICIONAR)) {

                    if (t != null) {
                        mLIstTreinoJSON.add(t);
                        atualizarLista();
                    }

                }
            } catch (NullPointerException npe) {
            }
        }
    }
}
