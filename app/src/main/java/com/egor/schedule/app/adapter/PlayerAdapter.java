package com.egor.schedule.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eb.schedule.shared.bean.Item;
import com.eb.schedule.shared.bean.Player;
import com.egor.schedule.app.R;
import com.egor.schedule.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Егор on 06.02.2016.
 */
//// TODO: 15.03.2017  fix hero - remove from app and get it by name from image service

public class PlayerAdapter extends ArrayAdapter<Player> {

    private Activity context;
    private List<Player> players;


    public PlayerAdapter(Activity context) {
        super(context, R.layout.schedule_list_item);
        this.context = context;
        this.players = new ArrayList<Player>();

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.player, null, true);

        ImageView heroImage = (ImageView) rowView.findViewById(R.id.hero_log);
        Player player = players.get(position);
        int heroId = player.getHero().getId();
        if(heroId!=0){
            int drawableId = context.getResources().getIdentifier("h_" + heroId, "drawable", context.getPackageName());
            Picasso.with(context).load(drawableId).into(heroImage);
        }

        TextView playerName = (TextView) rowView.findViewById(R.id.player_name);
        playerName.setText(player.getName());

        TextView level = (TextView) rowView.findViewById(R.id.level_status);
        level.setText("Lvl " + player.getLevel() + ": " + player.getHero().getName());

        TextView kda = (TextView) rowView.findViewById(R.id.kda);
        kda.setText("K/D/A: " + player.getKills() + "/" + player.getDeaths() + "/" + player.getAssists());

        List<Item> items = player.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageView itemImage = (ImageView) rowView.findViewById(context.getResources().getIdentifier("item_" + (i + 1), "id", context.getPackageName()));
            Item item = items.get(i);
            if (item.getName().contains("recipe")) {
                Picasso.with(context).load(R.drawable.i_recipe).resize(30, 30).centerCrop().into(itemImage);
            }else{
                Picasso.with(context).load(ImageUtils.getItemUrl(item.getName())).resize(30, 30).centerCrop().into(itemImage);
            }
        }

        return rowView;
    }

    @Override
    public void addAll(Collection<? extends Player> collection) {
        players.addAll(collection);
        super.addAll(collection);
    }

    @Override
    public void clear() {
        players.clear();
        super.clear();
    }
}