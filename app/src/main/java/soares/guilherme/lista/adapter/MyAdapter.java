package soares.guilherme.lista.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soares.guilherme.lista.R;
import soares.guilherme.lista.activity.MainActivity;
import soares.guilherme.lista.model.MyItem;
public class MyAdapter extends RecyclerView.Adapter {MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem>itens){
        this.mainActivity = mainActivity;
        this.itens = itens;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //cria um novo ViewHolder
        LayoutInflater inflater = LayoutInflater.from(mainActivity); 
        View v = inflater.inflate(R.layout.item_list,parent,false); //cria um novo item de lista e inflando o layout
        return new MyViewHolder(v); // retorna o viewholder criado
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { 
        MyItem myItem = itens.get(position); // obtendo o item da lista
        View v = holder.itemView; // obtendo a viewholder
        ImageView imvfoto = v.findViewById(R.id.imvPhoto);
        imvfoto.setImageBitmap (myItem.photo); //recebendo e setando a imagem

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.description); //recebendo e setando a descrição
    }

    @Override
    public int getItemCount() {
        return itens.size(); // função que retorna o tamanho da lista
    }
}
