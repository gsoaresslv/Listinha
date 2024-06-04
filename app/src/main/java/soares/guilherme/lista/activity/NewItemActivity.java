package soares.guilherme.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import soares.guilherme.lista.R;
import soares.guilherme.lista.model.NewActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;
    //guarda o endereço dos documentos android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NewActivityViewModel vm = new ViewModelProvider(this).get(NewActivityViewModel.class);

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if(selectPhotoLocation != null) {
            ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
            imvPhotoPreview.setImageURI(selectPhotoLocation);
        }

        //obtendo botao e setando o listener
        ImageButton imgCI = findViewById(R.id.imbCI);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override

            //abre a galeria do usuário
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //recebe apenas documentos do tipo “image/*”
                photoPickerIntent.setType("image/*");
                //executa o intent
                startActivityForResult(photoPickerIntent,PHOTO_PICKER_REQUEST);
            }

        });
        //obtem botao btnAddItem
        Button btnAddItem = findViewById(R.id.btnAddItem);
        //setando o listener
        btnAddItem.setOnClickListener(new View.OnClickListener(){
            @Override
            //verifica se os campos estão preenchidos
            public  void onClick(View v){

                NewActivityViewModel vm = new ViewModelProvider(NewItemActivity.this).get(New.class);
                Uri photoSelected = vm.getSelectPhotoLocation();

                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!" , Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                //exibe mensagem de erro se os campos estiverem vazios
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

                //retornando dados para a activity
                Intent i = new Intent(); //guarda os dados pra retornar
                i.setData(photoSelected); //setando o Uri da imagem escolhida dentro do intent
                //setando titulo e descricao
                i.putExtra("title",title);
                i.putExtra("description",description);
                //indica o resultado
                setResult(Activity.RESULT_OK, i);// indica que há dados de retorno
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //referente ao fornecido na chamada com id
        if(requestCode == PHOTO_PICKER_REQUEST) {
            //codigo de sucesso
            if(resultCode == Activity.RESULT_OK) {
                Uri photoSelected = data.getData();
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);

                imvPhotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectPhotoLocation(photoSelected);

            }
        }
    }
}