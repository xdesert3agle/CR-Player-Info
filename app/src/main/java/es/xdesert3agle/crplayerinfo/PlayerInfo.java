package es.xdesert3agle.crplayerinfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.xdesert3agle.crplayerinfo.apiclasses.Card;
import es.xdesert3agle.crplayerinfo.apiclasses.Player;
import es.xdesert3agle.crplayerinfo.util.CardUpgraderRecyclerAdapter;
import es.xdesert3agle.crplayerinfo.util.ChestCycleRecyclerAdapter;
import es.xdesert3agle.crplayerinfo.util.SpanningLinearLayoutManager;

public class PlayerInfo extends Fragment{
    Player mPlayer;

    @BindView(R.id.tvPlayerName) TextView tvPlayerName;
    @BindView(R.id.tvPlayerTag) TextView tvPlayerTag;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playerinfo, container, false);

        ButterKnife.bind(this, view);

        Picasso picasso = preloadChestImages();

        mPlayer = getArguments().getParcelable("playerObj");

        tvPlayerName.setText(String.format(tvPlayerName.getText().toString(), mPlayer.getName()));
        tvPlayerTag.setText(String.format(tvPlayerTag.getText().toString(), mPlayer.getTag()));

        mRecyclerView.setLayoutManager(new SpanningLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mRecyclerView.setAdapter(new ChestCycleRecyclerAdapter(mPlayer.getChestCycle().getUpcoming(), picasso));

        return view;
    }

    private Picasso preloadChestImages() {
        Picasso picasso = Picasso.get();

        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-silver.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-golden.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-giant.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-magical.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-supermagical.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-legendary.png").fetch();
        Picasso.get().load("https://royaleapi.com/static/img/chests/chest-epic.png").fetch();

        Log.d("State", "All chest images preloaded.");

        return picasso;
    }
}