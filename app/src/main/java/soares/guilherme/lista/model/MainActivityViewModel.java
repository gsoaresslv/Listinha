package soares.guilherme.lista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    List<MyItem> itens = new ArrayList<>(); // criando uma lista de itens
    public List<MyItem> getItens() { // função que retorna a lista de itens
        return itens;
    }
}