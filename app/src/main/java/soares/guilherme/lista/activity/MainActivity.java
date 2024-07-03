package soares.guilherme.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import soares.guilherme.lista.R;
import soares.guilherme.lista.adapter.MyAdapter;
import soares.guilherme.lista.model.MainActivityViewModel;
import soares.guilherme.lista.model.MyItem;
import soares.guilherme.lista.util.Util;


public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>(); 

    MyAdapter myAdapter; 
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); //seta o layout da activity
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem); //obtem o botao 
        fabAddItem.setOnClickListener(new View.OnClickListener() { //cria um listener para o botao 
            @Override
            public void onClick(View v) { //quando o botao for clicado
                Intent i = new Intent(MainActivity.this,NewItemActivity.class); //cria uma nova intent
                startActivityForResult(i, NEW_ITEM_REQUEST); //inicia uma nova activity

            }
        });
        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens(); //transformando o recicleview em uma lista

        myAdapter = new MyAdapter(this,itens); //criando um adapter para o recicleview
        rvItens.setAdapter(myAdapter); 
        rvItens.setHasFixedSize(true); //setando o tamanho do recicleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); 
        rvItens.setLayoutManager(layoutManager); //criando e setando o layout do recicleview
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),DividerItemDecoration.VERTICAL); //criando uma linha divisoria
        rvItens.addItemDecoration(dividerItemDecoration); 
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //verificando se as condicoes de retorno foram cumpridas
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK){ //se o resultado for ok
                MyItem myItem = new MyItem(); //cria um novo item
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description"); 
                Uri selectedPhotoURI = data.getData(); //pegando os dados do item
                try {
                    myItem.photo = Util.getBitmap(this, selectedPhotoURI, 100, 100); //pegando e setando a foto do item
                } catch (FileNotFoundException e){ //criando erro para caso não haja imagem
                    e.printStackTrace(); 
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); 
                List<MyItem> itens = vm.getItens(); //transformando o recicleview em uma lista
                itens.add(myItem); //adicionando o item a lista
                myAdapter.notifyItemInserted(itens.size()-1); //notificando o adapter
            }
        }
    }
}