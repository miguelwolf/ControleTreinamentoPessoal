package br.com.miguelwolf.controletreinamentopessoal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.miguelwolf.controletreinamentopessoal.R;
import br.com.miguelwolf.controletreinamentopessoal.interfaces.RecyclerViewOnClickListenerHack;
import br.com.miguelwolf.controletreinamentopessoal.model.Treino;

public class TreinosAdapter extends RecyclerView.Adapter<TreinosAdapter.MyViewHolder> {

    private Context context;

    private int selecionado = 0;

    private List<Treino> mList;

    private LayoutInflater mLayoutInflater;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

//    private int selecionado = -1; //variável responsável por guardar a posição do último item selecionado.



    public TreinosAdapter(Context c, List<Treino> l) {
        context = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_treinos, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }



    public int getAdapterPosition(){
        return selecionado;
    }


    public void addListItem(Treino r, int position) {
        mList.add(r);
        notifyItemInserted(position);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String repeticoes = "";
        repeticoes+=mList.get(position).getSeg() == 1 ? "Seg, " : "";
        repeticoes+=mList.get(position).getTer() == 1 ? "Ter, " : "";
        repeticoes+=mList.get(position).getQua() == 1 ? "Qua, " : "";
        repeticoes+=mList.get(position).getQui() == 1 ? "Qui, " : "";
        repeticoes+=mList.get(position).getSex() == 1 ? "Sex, " : "";
        repeticoes+=mList.get(position).getSab() == 1 ? "Sab, " : "";
        repeticoes+=mList.get(position).getDom() == 1 ? "Dom, " : "";

        if (!repeticoes.isEmpty())
            repeticoes = repeticoes.substring(0, repeticoes.length()-2);

        holder.tvFrete.setText(context.getString(R.string.treinos_info, mList.get(position).getNome(),
                mList.get(position).getDescricao(),
                Treino.EXERCICIOS_ARRAY[mList.get(position).getTipoExercicios()],
                repeticoes));


//        if (mList.get(position).isSelecionado()) {
//            holder.ivFrete.setVisibility(View.VISIBLE);
//            holder.tvFrete.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
//        } else {
//            holder.ivFrete.setVisibility(View.INVISIBLE);
//            holder.tvFrete.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
//        }

    }



//    public void updateItemChecked(int position) {
//
//        if (position != selecionado) {
//
//            Frete f = mList.get(selecionado);
//            f.setSelecionado(false);
//            notifyItemChanged(selecionado);
//
//            f = mList.get(position);
//            f.setSelecionado(true);
//            notifyItemChanged(position);
//
//            this.selecionado = position;
//            mList.set(position, f);
//
//        }
//
//        notifyDataSetChanged();
//    }



    @Override
    public int getItemCount() {
        return mList.size();
    }



    public void removeItem(int position) {
        mList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

//    public void restoreItem(Frete item, int position) {
//        mList.add(position, item);
//        // notify item added by position
//        notifyItemInserted(position);
//    }
//
//
//
//    public void addListItem(Frete f, int position) {
//        mList.add(f);
//        notifyItemInserted(position);
//    }




    /**
     * Retorna um objeto do tipo Frete.
     * @param position posição da lista de fretes.
     * @return
     */
    public Treino getPesquisaProduto(int position) {
        return mList.get(position);
    }




    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }



    public RecyclerViewOnClickListenerHack getmRecyclerViewOnClickListenerHack() {
        return mRecyclerViewOnClickListenerHack;
    }



    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ConstraintLayout clFrete;
        private TextView tvFrete;


        public MyViewHolder(View itemView) {
            super(itemView);

            clFrete = itemView.findViewById(R.id.item_treinos_cl);
            tvFrete = itemView.findViewById(R.id.item_treinos_tv);

            itemView.setOnClickListener(this);
            clFrete.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(view, getLayoutPosition());
            }
        }
    };
}
