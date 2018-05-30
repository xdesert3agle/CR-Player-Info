package es.xdesert3agle.crplayerinfo.Screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.xdesert3agle.crplayerinfo.API_classes.Card;
import es.xdesert3agle.crplayerinfo.API_classes.Player;
import es.xdesert3agle.crplayerinfo.R;
import es.xdesert3agle.crplayerinfo.Adapters.CardUpgraderRecyclerAdapter;
import es.xdesert3agle.crplayerinfo.Adapters.ActvAdapter;

import static java.util.Collections.sort;

public class CardUpgrader extends Fragment{

    Player mPlayer;

    AutoCompleteTextView actvCards;

    List<Card> mUpgradeList = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mApdater;
    RecyclerView.LayoutManager mLayoutManager;

    Picasso picasso;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getActivity().getFragmentManager()));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cardupgrader, container, false);

        // Hides the keyboard when the screen is touched
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (actvCards.isFocused()){
                    hideKeyboard();
                    actvCards.clearFocus();
                }
                return true;
            }
        });

        // Save the player from intent to an object
        mPlayer = getArguments().getParcelable("playerObj");

        // Fill AutoCompleteTextView with all the card list the player has
        actvCards = view.findViewById(R.id.actvCards);
        fillAutoCompleteTextView();

        // Precache all card images
        picasso = preloadImages();
        mRecyclerView = view.findViewById(R.id.recyclerView);

        Button btnCheck = view.findViewById(R.id.btnCheckTag);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSelectedCard() != null){
                    showUpgradeInfo();
                    updateUIElement(actvCards, "");
                    actvCards.clearFocus();
                }
            }
        });

        // Hides keyboard when a dropdown item is chosen
        actvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyboard();
            }
        });
/*
        // Shows dropdown when gains focus
        actvCards.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    actvCards.showDropDown();
            }
        });*/

        // Shows dropdown when is clicked
        actvCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actvCards.showDropDown();
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUpgradeInfo(){
        mUpgradeList.add(0, getSelectedCard());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mApdater = new CardUpgraderRecyclerAdapter(mUpgradeList, picasso);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mApdater);
    }

    private List<String> getCardNames(){

        List<String> cartas = new ArrayList<String>();

        for (Card card : mPlayer.getCards()){
            cartas.add(card.getName());
        }

        sort(cartas);
        return cartas;
    }

    private void fillAutoCompleteTextView(){
        List<String> cards = getCardNames();
        ActvAdapter spAdapter = new ActvAdapter(getActivity(), android.R.layout.simple_list_item_1, cards);

        actvCards.setAdapter(spAdapter);
    }

    private Card getSelectedCard(){
        String chosenCard = actvCards.getText().toString();

        for (Card c : mPlayer.getCards()){
            if (c.getName().equals(chosenCard)){
                return c;
            }
        }
        return null;
    }

    private Picasso preloadImages() {
        Picasso picasso = Picasso.get();

        for(Card c : mPlayer.getCards()) {
            Picasso.get().load(c.getIcon()).fetch();
        }

        Log.d("State", "All images preloaded.");

        return picasso;
    }

    // Update UI element (AutoCompleteTextView variant)
    private void updateUIElement(final AutoCompleteTextView actv, final String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                actv.setText(msg);
            }
        });
    }

    /*public void clearFocus(){
        actvCards.clearFocus();
        getActivity().findViewById(R.id.etThief).requestFocus();
    }*/

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
