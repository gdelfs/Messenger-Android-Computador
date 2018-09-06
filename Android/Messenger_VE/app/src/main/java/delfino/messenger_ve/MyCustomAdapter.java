package delfino.messenger_ve;

/**
 * Created by Gabriel on 21/05/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter{

    private ArrayList<String> mListItems;
    private LayoutInflater mLayoutInflater;

    public MyCustomAdapter(Context context, ArrayList<String> arrayList){

        mListItems = arrayList;

        //layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //Numero de itens na lista (contador)
        return mListItems.size();
    }

    @Override
    //Buscando os dados de cada item, de acordo com a posição dele
    //posição "i"
    public Object getItem(int i) {
        return null;
    }

    @Override
    //Pegando o id da posição
    public long getItemId(int i) {
        return 0;
    }

    @Override

    public View getView(int position, View view, ViewGroup viewGroup) {

        //Verificando se há espaço. Se for null, ela tem espaço, sendo utilizada
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.list_item, null);
        }

        //Coloca a mensagem no text view (de acordo com a sua posição)
        String stringItem = mListItems.get(position);
        if (stringItem != null) {

            TextView itemName = (TextView) view.findViewById(R.id.list_item_text_view);

            if (itemName != null) {
                //setText da string
                itemName.setText(stringItem);
            }
        }

        //Retorna a view com os dados da posição selecionada
        return view;

    }
}
