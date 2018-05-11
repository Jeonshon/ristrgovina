package com.ris.trgovina;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class SkupnaCenaIzKosarice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skupna_cena_iz_kosarice);

        readStreamData();
    }

    private void readStreamData() {
        Bundle bundle = getIntent().getExtras();
        String dataIme = bundle.getString("mIme");
        String dataKolicina = bundle.getString("mKolicina");
        String dataCena = bundle.getString("mCena");
        TextView racun = (TextView) findViewById(R.id.tvRacun);
        String[] imenaTab = dataIme.split(",");
        String[] kolicinaTab = dataKolicina.split(",");
        String[] cenaTab = dataCena.split(",");
        Date currentTime = Calendar.getInstance().getTime();

        float cenaSkupaj = 0;
        for(int i=0;i<imenaTab.length;i++) {
            cenaSkupaj += (Float.valueOf(kolicinaTab[i]) * Float.valueOf(cenaTab[i]));
        }

        racun.setText("Podjetje oblekeETC\nLjubljanska Ulica 5\n1000 Ljubljana\n\n");
        racun.append("RACUN\n\n");
        racun.append(currentTime.toString()+"\n");
        racun.append(String.format("%1$-10s|%2$-10s|%3$-10s\n","IME","KOLICINA","CENA"));
        racun.append("----------------------------------------------\n");
        for(int i=0;i<imenaTab.length;i++) {
            racun.append(String.format("%1$-15s %2$10sx  %3$-10s\n",imenaTab[i],kolicinaTab[i],cenaTab[i]));
        }
        racun.append("----------------------------------------------\n");
        racun.append(String.format("ZA PLACILO %.2f",cenaSkupaj)+" €");
        Button btnPotrdiNakup = (Button) findViewById(R.id.btnPotrdiNakup);

        btnPotrdiNakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buyAlert = new AlertDialog.Builder(SkupnaCenaIzKosarice.this);
                buyAlert.setMessage("Ali ste prepricani, da zelite kupiti izbrane izdelke?")
                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //klici zunanje akterje
                                SV_Banka_SIM();
                                SV_sistemZaEposto_SIM();
                                setResult(RESULT_OK);
                                finish();
                            }
                        }).setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = buyAlert.create();
                alert.setTitle("POTRDILO NAKUPA");
                alert.show();
            }
        });
    }

    private void SV_Banka_SIM() {

    }

    private void SV_sistemZaEposto_SIM() {

    }
}
