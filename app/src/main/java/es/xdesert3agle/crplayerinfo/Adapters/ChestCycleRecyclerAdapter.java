package es.xdesert3agle.crplayerinfo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.xdesert3agle.crplayerinfo.R;

public class ChestCycleRecyclerAdapter extends RecyclerView.Adapter<ChestCycleRecyclerAdapter.ViewHolder> {
    private List<String> upcomingChests;
    private Picasso picasso;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivChest;

        public ViewHolder(View itemView) {
            super(itemView);

            ivChest = itemView.findViewById(R.id.ivChest);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern_chestcycle, parent, false);

        return new ViewHolder(v);
    }

    public ChestCycleRecyclerAdapter(List<String> upcomingChests, Picasso picasso){
        this.upcomingChests = upcomingChests;
        this.picasso = picasso;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (upcomingChests.get(position)){
            case "silver":
                picasso.load("https://royaleapi.com/static/img/chests/chest-silver.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "gold":
                picasso.load("https://royaleapi.com/static/img/chests/chest-golden.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "giant":
                picasso.load("https://royaleapi.com/static/img/chests/chest-giant.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "magical":
                picasso.load("https://royaleapi.com/static/img/chests/chest-magical.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "superMagical":
                picasso.load("https://royaleapi.com/static/img/chests/chest-supermagical.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "legendary":
                picasso.load("https://royaleapi.com/static/img/chests/chest-legendary.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;
            case "epic":
                picasso.load("https://royaleapi.com/static/img/chests/chest-epic.png").networkPolicy(NetworkPolicy.OFFLINE).into(holder.ivChest);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return upcomingChests.size();
    }
}
