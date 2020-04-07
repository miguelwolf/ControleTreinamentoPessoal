package br.com.miguelwolf.controletreinamentopessoal.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public AppPrefs(Context context){
        //this.ctx = context;
        prefs = context.getSharedPreferences("ControleTreinamentoPessoal", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }




//    private String nome;

    private int ordemLista = 0, itensExibicaoVez = Constants.ITENS_EXIBICAO_VEZ_PARAMS[0];

    private boolean dark, exemploTreino;

//    public String getNome() {
//        return prefs.getString(Constants.login_nome, nome);
//    }
//
//    public void setNome(String nome) {
//        editor.putString(Constants.login_nome, nome);
//        editor.commit();
//    }

    public boolean isDark(){
        return prefs.getBoolean(Constants.DARK, dark);
    }

    public void setDark(boolean dark) {
        editor.putBoolean(Constants.DARK, dark);
        editor.commit();
    }

    public boolean isExemploTreino(){
        return prefs.getBoolean(Constants.EXEMPLO_TREINO, exemploTreino);
    }

    public void setExemploTreino(boolean exemploTreino) {
        editor.putBoolean(Constants.EXEMPLO_TREINO, exemploTreino);
        editor.commit();
    }

    public int getOrdemLista() {
        return prefs.getInt(Constants.ORDEM_LISTA, ordemLista);
    }

    public void setOrdemLista(int ordemLista) {
        editor.putInt(Constants.ORDEM_LISTA, ordemLista);
        editor.commit();
    }

    public int getItensExibicaoVez() {
        return prefs.getInt(Constants.ITENS_EXIBICAO_VEZ, itensExibicaoVez);
    }

    public void setItensExibicaoVez(int itensExibicaoVez) {
        editor.putInt(Constants.ITENS_EXIBICAO_VEZ, itensExibicaoVez);
        editor.commit();
    }

}