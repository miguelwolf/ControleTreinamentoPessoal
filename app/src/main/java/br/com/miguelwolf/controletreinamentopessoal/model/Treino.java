package br.com.miguelwolf.controletreinamentopessoal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Treino implements Parcelable, Cloneable {

    private String nome;
    private String descricao;
    private String passoAPasso;
    private String tempoRepeticoes;
    private int tipoExercícios;
    private int seg, ter, qua, qui, sex, sab, dom;

    public static String[] EXERCICIOS_ARRAY = { "Treino Funcional",
            "Corrida",
            "Spinning",
            "Bike Outdoor",
            "HIIT",
            "Musculação",
            "Dança"};

    public Treino() {
    }

    public Treino(String nome, String descricao, String passoAPasso, String tempoRepeticoes, int tipoExercícios, int seg, int ter, int qua, int qui, int sex, int sab, int dom) {
        this.nome = nome;
        this.descricao = descricao;
        this.passoAPasso = passoAPasso;
        this.tempoRepeticoes = tempoRepeticoes;
        this.tipoExercícios = tipoExercícios;
        this.seg = seg;
        this.ter = ter;
        this.qua = qua;
        this.qui = qui;
        this.sex = sex;
        this.sab = sab;
        this.dom = dom;
    }


    public static ArrayList<Treino> getList(int cont) {

        ArrayList<Treino> mList = new ArrayList<>();

        for (int i = 0; i < cont; i++) {
            Treino t = new Treino();

            List<String> mListNomes = Arrays.asList("Treino 1", "Treino 2", "Treino 3", "Treino 4", "Treino 5", "Treino 6", "Treino 7", "Treino 8", "Treino 9", "Treino 10", "Treino 11", "Treino 12", "Treino 13", "Treino 14", "Treino 15", "Treino 16", "Treino 17", "Treino 18", "Treino 19", "Treino 20");
            Collections.shuffle(mListNomes);
            t.setNome(mListNomes.get(0));

            List<String> mListDescricao = Arrays.asList("Em casa, ", "Academia", "Ar livre");
            Collections.shuffle(mListDescricao);
            t.setDescricao(mListDescricao.get(0));

            List<Integer> mListExercicios = Arrays.asList(0,1,2,3,4,5,6);
            Collections.shuffle(mListExercicios);
            t.setTipoExercícios(mListExercicios.get(0));

            List<Integer> mListSeg = Arrays.asList(1, 0);
            Collections.shuffle(mListSeg);
            t.setSeg(mListSeg.get(0));

            List<Integer> mListTer = Arrays.asList(1, 0);
            Collections.shuffle(mListTer);
            t.setTer(mListTer.get(0));

            List<Integer> mListQua = Arrays.asList(1, 0);
            Collections.shuffle(mListQua);
            t.setQua(mListQua.get(0));

            List<Integer> mListQui = Arrays.asList(1, 0);
            Collections.shuffle(mListQui);
            t.setQui(mListQui.get(0));

            List<Integer> mListSex = Arrays.asList(1, 0);
            Collections.shuffle(mListSex);
            t.setSex(mListSex.get(0));

            List<Integer> mListSab = Arrays.asList(1, 0);
            Collections.shuffle(mListSab);
            t.setSab(mListSab.get(0));

            t.setDom(0);

            mList.add(t);
        }

        return mList;

    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPassoAPasso() {
        return passoAPasso;
    }

    public void setPassoAPasso(String passoAPasso) {
        this.passoAPasso = passoAPasso;
    }

    public String getTempoRepeticoes() {
        return tempoRepeticoes;
    }

    public void setTempoRepeticoes(String tempoRepeticoes) {
        this.tempoRepeticoes = tempoRepeticoes;
    }

    public int getTipoExercicios() {
        return tipoExercícios;
    }

    public void setTipoExercícios(int tipoExercícios) {
        this.tipoExercícios = tipoExercícios;
    }

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getTer() {
        return ter;
    }

    public void setTer(int ter) {
        this.ter = ter;
    }

    public int getQua() {
        return qua;
    }

    public void setQua(int qua) {
        this.qua = qua;
    }

    public int getQui() {
        return qui;
    }

    public void setQui(int qui) {
        this.qui = qui;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSab() {
        return sab;
    }

    public void setSab(int sab) {
        this.sab = sab;
    }

    public int getDom() {
        return dom;
    }

    public void setDom(int dom) {
        this.dom = dom;
    }

    @Override
    public Object clone() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();

        Parcel parcel2 = Parcel.obtain();
        parcel2.unmarshall(bytes, 0, bytes.length);
        parcel2.setDataPosition(0);

        return Treino.CREATOR.createFromParcel(parcel2);
    }

    protected Treino(Parcel in) {
        nome = in.readString();
        descricao = in.readString();
        passoAPasso = in.readString();
        tempoRepeticoes = in.readString();
        tipoExercícios = in.readInt();
        seg = in.readInt();
        ter = in.readInt();
        qua = in.readInt();
        qui = in.readInt();
        sex = in.readInt();
        sab = in.readInt();
        dom = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeString(passoAPasso);
        dest.writeString(tempoRepeticoes);
        dest.writeInt(tipoExercícios);
        dest.writeInt(seg);
        dest.writeInt(ter);
        dest.writeInt(qua);
        dest.writeInt(qui);
        dest.writeInt(sex);
        dest.writeInt(sab);
        dest.writeInt(dom);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Treino> CREATOR = new Parcelable.Creator<Treino>() {
        @Override
        public Treino createFromParcel(Parcel in) {
            return new Treino(in);
        }

        @Override
        public Treino[] newArray(int size) {
            return new Treino[size];
        }
    };
}
