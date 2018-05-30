package es.xdesert3agle.crplayerinfo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.xdesert3agle.crplayerinfo.R;
import es.xdesert3agle.crplayerinfo.API_classes.Card;

public class CardUpgraderRecyclerAdapter extends RecyclerView.Adapter<CardUpgraderRecyclerAdapter.ViewHolder> {
    private List<Card> mCardList;
    private Picasso picasso;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCardBefore) ImageView mImageViewBefore;
        @BindView(R.id.ivCardAfter) ImageView mImageViewAfter;
        @BindView(R.id.tvLevelBefore) TextView mLevelBefore;
        @BindView(R.id.tvLevelAfter) TextView mLevelAfter;
        @BindView(R.id.tvAmountBefore) TextView mAmountBefore;
        @BindView(R.id.tvAmountAfter) TextView mAmountAfter;
        @BindView(R.id.tvRequiredCardsBefore) TextView mRequiredBefore;
        @BindView(R.id.tvRequiredCardsAfter) TextView mRequiredAfter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern_cardupgrader, parent, false);

        return new ViewHolder(v);
    }

    public CardUpgraderRecyclerAdapter(List<Card> cards, Picasso picasso){
        this.mCardList = cards;
        this.picasso = picasso;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        picasso.load(mCardList.get(position).getIcon()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.mImageViewBefore);
        picasso.load(mCardList.get(position).getIcon()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.mImageViewAfter);
        holder.mLevelBefore.setText(" " + String.valueOf(mCardList.get(position).getLevel()));
        holder.mLevelAfter.setText(" " + String.valueOf(mCardList.get(position).getUpgrade().getPotentialLevel()));
        holder.mAmountBefore.setText(String.valueOf(mCardList.get(position).getCount()));
        holder.mAmountAfter.setText(String.valueOf(mCardList.get(position).getUpgrade().getRemainingCards()));
        holder.mRequiredBefore.setText(String.valueOf(getRequiredCardsArray(mCardList.get(position).getRarity())[mCardList.get(position).getLevel()]));
        holder.mRequiredAfter.setText(String.valueOf(getRequiredCardsArray(mCardList.get(position).getRarity())[mCardList.get(position).getUpgrade().getPotentialLevel()]));
    }

    public int[] getRequiredCardsArray(String rarity){
        int[] cardAmountArray = new int[0];

        switch (rarity) {
            case "Common":
                cardAmountArray = new int[]{0, 2, 4, 10, 20, 50, 100, 200, 400, 800, 1000, 2000, 5000, 101};
                break;
            case "Rare":
                cardAmountArray = new int[]{0, 2, 4, 10, 20, 50, 100, 200, 400, 800, 1000, 11};
                break;
            case "Epic":
                cardAmountArray = new int[]{0, 2, 4, 10, 20, 50, 100, 200, 6};
                break;
            case "Legendary":
                cardAmountArray = new int[]{0, 2, 4, 10, 20, 2};
                break;
        }
        return cardAmountArray;
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}
