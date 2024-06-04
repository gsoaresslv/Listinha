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
        setContentView(R.layout.activity_main);
        //Obtendo o botão FAB e setando o listener
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cria uma intent para navegar para NewItemActivity
                Intent i = new Intent(MainActivity.this,NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);

            }
        });
        //obtem o RecycleView
        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        myAdapter = new MyAdapter(this,itens);
        //criando o MyAdapter e setando no RecycleView
        rvItens.setAdapter(myAdapter);
        //o metodo  indica que nao ha variacao de tamanho entre os itens
        rvItens.setHasFixedSize(true);
        //criando um gerenciador de layout do tipo linear e o setando no RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);
        //criamos um decorador para a lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verificando se as condicoes de retorno foram cumpridas
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                //se foram cumprida, criamos uma instancia, para guardar dados do item
                MyItem myItem = new MyItem();
                //obtemos os dados retornados r NewItemActivity e os guardamos dentro de myItem
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    //Essa função carrega a imagem e a guarda dentro de um Bitmap
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    // guardamos o Bitmap da imagem dentro de um objeto do tipo MyItem
                    myItem.photo = photo;
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();
                //adicionando item a uma lista de itens
                itens.add(myItem);
                // notifica o my adapter
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}