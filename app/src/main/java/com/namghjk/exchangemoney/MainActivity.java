package com.namghjk.exchangemoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import Model.History;

public class MainActivity extends AppCompatActivity {

    Spinner sp_From,sp_To;
    ArrayList<String> arrayTitle;
    ArrayAdapter adapter;
    Button btn_Convert;
    EditText edt_enter;
    String currency_name_1="";
    String currency_name_2="";
    TextInputEditText edt_result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControlls();
        addEvents();

        new ReadRSS().execute("https://aud.fxexchangerate.com/rss.xml");



    }

    private void addControlls(){

        btn_Convert = findViewById(R.id.button);
        edt_enter = findViewById(R.id.currency_to_be_converted);
        edt_result = findViewById(R.id.currency_converted);




        sp_From = findViewById(R.id.from);
        sp_To = findViewById(R.id.to);
        arrayTitle = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayTitle);
        sp_To.setAdapter(adapter);
        sp_From.setAdapter(adapter);



    }

    private void addEvents(){



        sp_From.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currentName = sp_From.getSelectedItem().toString();

                String[] seperate =  currentName.split("\\(");

                String currentName1 = seperate[1];

                String[] seperate1 = currentName1.split("\\)");

                currency_name_1 = seperate1[0];


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_To.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String currentName = sp_To.getSelectedItem().toString();

                String[] seperate =  currentName.split("\\(");

                String currentName1 = seperate[1];

                String[] seperate1 = currentName1.split("\\)");

                currency_name_2 = seperate1[0];


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Converte converter = new Converte();
                converter.execute();


            }
        });



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



        }
    }

    private class Converte extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder content = new StringBuilder();
            String URL1 ="";

            if(currency_name_1 != null && currency_name_2 != null && currency_name_1.equals(currency_name_2) == false) {

                URL1 = "https://" + currency_name_1 + ".fxexchangerate.com/" + currency_name_2 + ".xml";
                try {

                    URL url = new URL(URL1.toLowerCase());
                    InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {

                return "123";


            }

            return content.toString();
        }

        public String formatDecimal(float number) {
            float epsilon = 0.004f; // 4 tenths of a cent
            if (Math.abs(Math.round(number) - number) < epsilon) {
                return String.format("%10.0f", number); // sdb
            } else {
                return String.format("%10.2f", number); // dj_segfault
            }

        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("123")){
                DecimalFormat dcf = new DecimalFormat("###,###,###.##");
                edt_result.setText(dcf.format(Float.parseFloat(edt_enter.getText().toString())));
            }else {
                try {
                    if (edt_enter.getText().toString().isEmpty() || edt_enter.getText().toString() == "") {
                        edt_enter.setText("");
                        return;
                    }
                    XMLDOMParser parser = new XMLDOMParser();
                    Document document = parser.getDocument(s);
                    NodeList nodeList = document.getElementsByTagName("item");
                    String tygia = "";
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element element = (Element) nodeList.item(i);
                        NodeList nodeListDescription = element.getElementsByTagName("description");
                        Element DecriptionEle = (Element) nodeListDescription.item(i);
                        tygia = Html.fromHtml(DecriptionEle.getFirstChild().getNodeValue().trim()).toString();
                    }

                    String[] arr = tygia.split("\n");
                    String currency = arr[0];

                    String[] arrcurency = currency.split("=");

                    String currency1 = arrcurency[1];
                    String[] arrcurency1 = currency1.split(" ");

                    Float value = Float.parseFloat(edt_enter.getText().toString().trim());

                    Float b = Float.parseFloat(arrcurency1[1].trim());


                    Float c = value * b;


                    edt_result.setText(Float.toString(c));



                    super.onPostExecute(s);


                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Có lỗi xảy ra").setMessage("Vui lòng chọn lại quốc gia");
                    builder.setCancelable(true);
                }
            }



        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_menu){

            History history = new History(currency_name_1,edt_enter.getText().toString(),currency_name_2,edt_result.getText().toString());
            Log.e("history ",history.toString());


            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("history",history);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}