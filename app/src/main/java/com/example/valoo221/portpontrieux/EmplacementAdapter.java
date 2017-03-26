package com.example.valoo221.portpontrieux;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import metier.Emplacement;
import metier.EmplacementViewHolder;

/**
 * Created by Valoo221 on 22/03/2017.
 */

public class EmplacementAdapter extends ArrayAdapter<Emplacement>
{
    public EmplacementAdapter(Context context, List<Emplacement> lesEmplacements)
    {
        super(context, 0, lesEmplacements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_emplacement,parent, false);
        }

        EmplacementViewHolder viewHolder = (EmplacementViewHolder) convertView.getTag();
        if(viewHolder == null)
        {
            viewHolder = new EmplacementViewHolder();
            viewHolder.IVDispo = (ImageView) convertView.findViewById(R.id.IVDispo);
            viewHolder.TVRefEmplacement = (TextView) convertView.findViewById(R.id.TVEmplacements);
            viewHolder.TVType = (TextView) convertView.findViewById(R.id.TVType);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Emplacement UnEmplacement = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.TVRefEmplacement.setText(UnEmplacement.getRefEmplacement());
        viewHolder.TVType.setText(UnEmplacement.getUnType().getSituation());
        if(UnEmplacement.getDispo())
        {
            viewHolder.IVDispo.setImageDrawable(new ColorDrawable(Color.GREEN));
        }
        else
        {
            viewHolder.IVDispo.setImageDrawable(new ColorDrawable(Color.RED));
        }
        return convertView;
    }

    private class EmplacementViewHolder
    {
        public TextView TVType;
        public TextView TVRefEmplacement;
        public ImageView IVDispo;
    }
}
