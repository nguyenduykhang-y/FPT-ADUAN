package com.example.fpt_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.fpt_app.Adapter.MessageAdapter;
import com.example.fpt_app.Models.MessageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketActivity extends AppCompatActivity {

    EditText editTextData;
    ImageView buttonSend;
    ListView list_msg;

    MessageAdapter adapter;
    List<MessageModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        editTextData = findViewById(R.id.editTextData);
        buttonSend  = findViewById(R.id.buttonSend);
        list_msg = findViewById(R.id.list_msg);
        setTitle("Chat My");
        list = new ArrayList<>();
        adapter = new MessageAdapter(list, getApplicationContext());
        list_msg.setAdapter(adapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:2046/").build();
        WebSocket socket = client.newWebSocket(request, socketListener);
        client.dispatcher().executorService().shutdown();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editTextData.getText().toString();
                MessageModel messageModel = new MessageModel(s, true);
                JSONObject object = new JSONObject();
                try {
                    object.put("message", messageModel.getMessage());
                    object.put("fromMe", messageModel.getFromMe());
                    socket.send(object.toString());

                    List<MessageModel> _list = new ArrayList<>(list);
                    _list.add(messageModel);

                    list.clear();
                    list.addAll(_list);
                    adapter.notifyDataSetChanged();
                    list_msg.setAdapter(adapter);

                    editTextData.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    WebSocketListener socketListener = new WebSocketListener() {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.i(">>>>>>>>>", text);
            text = text.replace("\\\"", "'");
            text = text.substring(1, text.length() - 1);
            try {
                JSONObject object = new JSONObject(text);
                String msg = object.getString("msg");
                MessageModel messageModel = new MessageModel(msg, false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<MessageModel> _list = new ArrayList<>(list);
                        _list.add(messageModel);

                        list.clear();
                        list.addAll(_list);
                        adapter.notifyDataSetChanged();
                        list_msg.setAdapter(adapter);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
        }
    };
}