package com.ris.trgovina;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ItemAdapter extends BaseAdapter{
    private Context mContext;
    private List<Artikel> mProductList;

    public ItemAdapter(Context mContext, List<Artikel> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, final ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.list_item, null);
        TextView imeIzdelka = (TextView) v.findViewById(R.id.imeArtikla);
        TextView cenaIzdelka = (TextView) v.findViewById(R.id.cenaArtikla);
        TextView kolicinaIzdelka = (TextView) v.findViewById(R.id.kolicinaArtikla);
        ImageView slikaIzdelka = (ImageView) v.findViewById(R.id.slikaArtikla);

        //Set text
        float calcCena = Float.valueOf(mProductList.get(position).getKolicina()) * Float.valueOf(mProductList.get(position).getPrice());
        imeIzdelka.setText(mProductList.get(position).getName());
        cenaIzdelka.setText(String.format("%.2f",calcCena)+" €");
        kolicinaIzdelka.setText(mProductList.get(position).getKolicina());

        int drawableResourceId = slikaIzdelka.getContext().getResources().getIdentifier(mProductList.get(position).getId(),"drawable",
                slikaIzdelka.getContext().getPackageName());
        slikaIzdelka.setImageResource(drawableResourceId);
        //slikaIzdelka.setImageResource(R.drawable.img1);
        slikaIzdelka.setAdjustViewBounds(true);
        slikaIzdelka.setMaxHeight(100);
        slikaIzdelka.setMaxWidth(100);

        //Save product id to tag
        v.setTag(mProductList.get(position).getId());
        return v;
    }
}
