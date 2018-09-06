package delfino.messenger_ve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private TCPClient mTcpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        arrayList = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);

        //Relacionando o view do java com o do layout (q será mostrado ao usuário)
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);

        // Conectando ao servidor
        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();

                //Adiciona a mensagem ao array
                arrayList.add("Você: " + message);

                //envia a mensagem para o server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }

                //renova a lista
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }

    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //Criacao do objeto de cliente TCP
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //mensagem recebida
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //Adicionando ao array a mensagem recebida
            arrayList.add(values[0]);
            // Notificação de quando algo mudou (muda se recebeu mensagem)
            // A mensagem recebida do servidor esta no array
            mAdapter.notifyDataSetChanged();
        }
    }

}
