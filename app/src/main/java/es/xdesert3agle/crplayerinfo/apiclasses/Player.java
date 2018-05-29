package es.xdesert3agle.crplayerinfo.apiclasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class Player implements Parcelable{
    private String tag;
    private String name;
    private int trophies;
    private int rank;
    private Clan clan;
    private ChestCycle chestCycle;
    private ArrayList<Card> cards;

    public Player(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        tag = in.readString();
        name = in.readString();
        trophies = in.readInt();
        rank = in.readInt();
        clan = in.readParcelable(Clan.class.getClassLoader());
        chestCycle = in.readParcelable(ChestCycle.class.getClassLoader());
        cards = new ArrayList<Card>();
        in.readTypedList(cards, Card.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(name);
        parcel.writeInt(trophies);
        parcel.writeInt(rank);
        parcel.writeParcelable(clan, i);
        parcel.writeParcelable(chestCycle, i);
        parcel.writeTypedList(cards);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public ChestCycle getChestCycle() {
        return chestCycle;
    }

    public void setChestCycle(ChestCycle chestCycle) {
        this.chestCycle = chestCycle;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void printPlayerInfo(){
        Log.d("Nombre", this.name);
        Log.d("Tag", "#" + this.tag);
        Log.d("Trofeos", String.valueOf(this.trophies));
    }
}
