package com.example.meinprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.*;
import android.content.*;

import android.os.Bundle;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private TextView Nachrichtenstatus;
    Client client = new Client("192.168.0.80", 8000, "Tim");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openHelloActivity();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                client.sendMessage("Hi");
            }
        });

        Nachrichtenstatus = (TextView)findViewById(R.id.textView2);
    }

    public void openHelloActivity(){
        Intent intent = new Intent(this, HalloActivity.class);
        startActivity(intent);
    }

    public class Client{
        private InetSocketAddress address;
        private String name;

        public Client(String hostname, int port, String name) {
            address = new InetSocketAddress(hostname, port);
            this.name = name;
        }

        public void sendMessage(final String msg){
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Socket socket = new Socket();
                        socket.connect(address, 5000);

                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        pw.print(name + ": " + msg);
                        pw.flush();

                        pw.close();
                        socket.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
