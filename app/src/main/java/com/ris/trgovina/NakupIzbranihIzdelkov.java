package com.ris.trgovina;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NakupIzbranihIzdelkov extends AppCompatActivity {
    private ItemAdapter adapter;
    private ListView myListView;
    private String[][] izdelkiVkosari;
    private List<Artikel> mProductList;
    private static final int REQUEST_CODE_NUMBER = 100;
    private String uporabnik = "Janez Novak";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nakup_izbranih_izdelkov);
        preberiIzdelkeVKosari();
        //dodajIzdelkeVKosari();
        //dodajListenerje();
    }

    private void preberiIzdelkeVKosari() {
        String imena = getResources().getString(R.string.imena_izdelkov);
        String cena = getResources().getString(R.string.cene_izdelkov);
        String id = getResources().getString(R.string.id_slike_izdelka);
        String st = getResources().getString(R.string.st_izdelkov);
        String idUporabnikaKosare = getResources().getString(R.string.id_uporabnika_kosare);
        String idUporabnika= getResources().getString(R.string.id_uporabnikov);
        String imeUporabnika= getResources().getString(R.string.ime_uporabnika);

        int stIzdelkov = imena.split(",").length;
        boolean uporabnikObstaja = false;
        String[] imenaTab = imena.split(",");
        String[] cenaTab = cena.split(",");
        String[] idTab = id.split(",");
        String[] stTab = st.split(",");
        String[] idKosareTab = idUporabnikaKosare.split(","); //id uporabnika, od katerega je artikel
        String[] idUporabnikaTab = idUporabnika.split(",");
        String[] imeUporabnikaTab = imeUporabnika.split(",");

        for(int i=0;i<idUporabnikaTab.length;i++) {
            if(uporabnik.equals(imeUporabnikaTab[i])) {
                uporabnikObstaja = true;
                idUporabnika = idUporabnikaTab[i];
            }
        }

        if (uporabnikObstaja) {
            int counter = 0;
            for (int i = 0; i < stIzdelkov; i++) {
                if(idKosareTab[i].equals(idUporabnika)) {
                    counter++;
                }
            }

            izdelkiVkosari = new String[counter][4];
            counter = 0;
            for (int i = 0; i < stIzdelkov; i++) {
                if (idKosareTab[i].equals(idUporabnika)) {
                    izdelkiVkosari[counter][0] = imenaTab[i];
                    izdelkiVkosari[counter][1] = cenaTab[i];
                    izdelkiVkosari[counter][2] = idTab[i];
                    izdelkiVkosari[counter][3] = stTab[i];
                    counter++;
                }
            }
            dodajIzdelkeVKosari();
        }
    }

    private void dodajIzdelkeVKosari() {
        myListView = (ListView) findViewById(R.id.list_view_list);
        mProductList = new ArrayList<>();

        //String[] listItems = new String[izdelkiVkosari.length];
        for(int i=0;i<izdelkiVkosari.length;i++) {
            //listItems[i] = izdelkiVkosari[i][0];
            mProductList.add(new Artikel(izdelkiVkosari[i][2],izdelkiVkosari[i][0],izdelkiVkosari[i][1],izdelkiVkosari[i][3]));
        }

        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems);
        //myListView.setAdapter(adapter);
        adapter = new ItemAdapter(getApplicationContext(), mProductList);
        myListView.setAdapter(adapter);

        dodajListenerje();
    }

    private void dodajListenerje() {
        //dodaj listenerje
        myListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //Log.d("TAG", "onItemClick: name: " + mProductList.get(position));
                final Artikel toDelete = (Artikel) parent.getItemAtPosition(position);
                //Toast.makeText(NakupIzbranihIzdelkov.this,"clicked product " + toDelete.getName(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(NakupIzbranihIzdelkov.this);
                deleteAlert.setMessage("Ali ste prepricani da zelite odstraniti "+toDelete.getName()+" iz kosare?")
                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
                                odstraniArtikel(toDelete);
                            }
                        }).setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                });
                AlertDialog alert = deleteAlert.create();
                alert.setTitle("Izbris artikla iz kosare");
                alert.show();
            }
        });
        Button btnKupi = (Button) findViewById(R.id.buttonNakup);
        btnKupi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProductList.size() != 0) {
                    String messageIme = "";
                    String messageKolicina = "";
                    String messageCena = "";
                    for (int i = 0; i < mProductList.size() - 1; i++) {
                        messageIme += mProductList.get(i).getName() + ",";
                        messageKolicina += mProductList.get(i).getKolicina() + ",";
                        messageCena += mProductList.get(i).getPrice() + ",";
                    }
                    messageIme += mProductList.get(mProductList.size() - 1).getName();
                    messageKolicina += mProductList.get(mProductList.size() - 1).getKolicina();
                    messageCena += mProductList.get(mProductList.size() - 1).getPrice();

                    Intent intent = new Intent(NakupIzbranihIzdelkov.this, SkupnaCenaIzKosarice.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("mIme", messageIme);
                    bundle.putString("mKolicina", messageKolicina);
                    bundle.putString("mCena", messageCena);
                    intent.putExtras(bundle);

                    //startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_NUMBER);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_NUMBER) {
            if(resultCode == RESULT_OK) {
                while(mProductList.size() != 0) {
                    odstraniArtikel(mProductList.get(0));
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void odstraniArtikel(Artikel toDelete) {
        mProductList.remove(toDelete);
        adapter = new ItemAdapter(getApplicationContext(), mProductList);
        myListView.setAdapter(adapter);
    }
}
