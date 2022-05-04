package com.namghjk.exchangemoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner sp_From,sp_To;
    ArrayList<String> arrayTitle;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControlls();
        addEvents();

        new ReadRSS().execute("https://aud.fxexchangerate.com/rss.xml");
    }

    private void addControlls(){
        sp_From = findViewById(R.id.from);
        sp_To = findViewById(R.id.to);
        arrayTitle = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayTitle);
        sp_To.setAdapter(adapter);
        sp_From.setAdapter(adapter);



    }

    private void addEvents(){

    }

    private class ReadRSS extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {



                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader =  new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                String line ="";

                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);

            NodeList nodeList = document.getElementsByTagName("item");

            String title = "";
            String currentString = "";

            for (int i = 0; i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i);
                currentString = parser.getValue(element, "title") + "\n" ;

                String[] seperated = currentString.split("/");
                title = seperated[1] ;
                arrayTitle.add(title);
            }

            adapter.notifyDataSetChanged();

            Log.e("title:",title);

        }
    }
}