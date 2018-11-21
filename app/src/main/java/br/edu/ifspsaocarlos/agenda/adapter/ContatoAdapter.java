package br.edu.ifspsaocarlos.agenda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;

import java.util.List;


public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private static List<Contato> contatos;
    private Context context;


    private static ItemClickListener clickListener;

    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
       holder.nome.setText(contatos.get(position).getNome());
       //Exec favorito
        holder.chkFavorito.setChecked(contatos.get(position).getFlgFavorito());

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    public  class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView nome;
        final CheckBox chkFavorito;

        ContatoViewHolder(View view) {
            super(view);
            nome = (TextView)view.findViewById(R.id.nome);
            chkFavorito = (CheckBox)view.findViewById(R.id.chkFav);
            view.setOnClickListener(this);
            chkFavorito.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       new ContatoDAO(context).atualizaFlgFavorito(chkFavorito.isChecked(), contatos.get(getAdapterPosition()).getId());
                       Toast.makeText(context, chkFavorito.isChecked() ? "Adicionado aos favoritos" : "Removido dos favoritos", Toast.LENGTH_SHORT).show();
                   }
               }

            );
            /*chkFavorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.getId() == chkFavorito.getId())
                    {
                        new ContatoDAO(context).atualizaFlgFavorito(isChecked, contatos.get(getAdapterPosition()).getId());
                        Toast.makeText(context, isChecked ? "Adicionado aos favoritos" : "Removido dos favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

}


