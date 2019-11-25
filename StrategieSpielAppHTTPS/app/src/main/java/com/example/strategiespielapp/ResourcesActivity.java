package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class ResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        //testHTTPrequest();
        testHTTPSrequest();
    }

    public void testHTTPSrequest(){
/*        InputStream caInput;
        Certificate ca = null;
        KeyStore keyStore = null;
        SSLContext context = null;
        BufferedReader in = null;
        String body = null;
        HttpsURLConnection urlConnection = null;*/

        String body = null;
        HttpsURLConnection urlConnection = null;

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            AssetFileDescriptor fileDescriptor = this.getAssets().openFd("localhost_key.key");
            FileInputStream stream = fileDescriptor.createInputStream();
            InputStream caInput = new BufferedInputStream(stream);
            Certificate ca = cf.generateCertificate(caInput);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            URL url = new URL("https://127.0.0.1:8000/?type=refresh");
            urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            body = content.toString();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        /*try {
            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        try {
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL("https://127.0.0.1:8000/?type=refresh");
            urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            body = content.toString();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Gson g = new Gson();

        Test test = g.fromJson(body, Test.class);


        TextView id = findViewById(R.id.acc_id);
        id.append("id: " + test.id);
        TextView email = findViewById(R.id.acc_mail);
        email.append("mail: " + test.email);
        TextView username = findViewById(R.id.acc_username);
        username.append("username: " + test.username);
        TextView password = findViewById(R.id.acc_password);
        password.append("password: " + test.password);
        TextView date = findViewById(R.id.acc_date);
        date.append("date: " + test.date);

        urlConnection.disconnect();


/*        String https_url="https://127.0.0.1:8000/?type=refresh";
        URL url;
        try {
            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            String body = content.toString();
            in.close();

            Gson g = new Gson();

            Test test = g.fromJson(body, Test.class);


            TextView id = findViewById(R.id.acc_id);
            id.append("id: " + test.id);
            TextView email = findViewById(R.id.acc_mail);
            email.append("mail: " + test.email);
            TextView username = findViewById(R.id.acc_username);
            username.append("username: " + test.username);
            TextView password = findViewById(R.id.acc_password);
            password.append("password: " + test.password);
            TextView date = findViewById(R.id.acc_date);
            date.append("date: " + test.date);

            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


   /* public void testHTTPrequest() {
        HttpURLConnection connection;
        try {
            //https?
            URL url = new URL("http://127.0.0.1:8000");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes("/test");
            out.flush();
            out.close();

//            int status = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            String body = content.toString();
            in.close();

            Gson g = new Gson();

            Planet planet = g.fromJson(body, Planet.class);


            TextView name = findViewById(R.id.planet_name);
            name.append("Planet Name: " + planet.name);
            TextView size = findViewById(R.id.planet_size);
            size.append("Planet Größe: " + planet.size);

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
