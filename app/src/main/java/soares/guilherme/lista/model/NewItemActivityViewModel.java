package soares.guilherme.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;
public class NewItemActivityViewModel extends ViewModel {
    Uri selectPhotoLocation = null;
    //método getter para obter a lista de itens
    public Uri getSelectPhotoLocation() {

        return selectPhotoLocation;
    }
    //método de setter para setar o
//endereço URI dentro do ViewModel
    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
