package es.xdesert3agle.crplayerinfo.apiclasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChestCycle implements Parcelable {
    private List<String> upcoming;
    private int superMagical;
    private int magical;
    private int legendary;
    private int epic;
    private int giant;

    public ChestCycle(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        upcoming = in.createStringArrayList();
        superMagical = in.readInt();
        magical = in.readInt();
        legendary = in.readInt();
        epic = in.readInt();
        giant = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(upcoming);
        parcel.writeInt(superMagical);
        parcel.writeInt(magical);
        parcel.writeInt(legendary);
        parcel.writeInt(epic);
        parcel.writeInt(giant);
    }

    public List<String> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<String> upcoming) {
        this.upcoming = upcoming;
    }

    public int getSuperMagical() {
        return superMagical;
    }

    public void setSuperMagical(int superMagical) {
        this.superMagical = superMagical;
    }

    public int getMagical() {
        return magical;
    }

    public void setMagical(int magical) {
        this.magical = magical;
    }

    public int getLegendary() {
        return legendary;
    }

    public void setLegendary(int legendary) {
        this.legendary = legendary;
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }

    public int getGiant() {
        return giant;
    }

    public void setGiant(int giant) {
        this.giant = giant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChestCycle> CREATOR = new Creator<ChestCycle>() {
        @Override
        public ChestCycle createFromParcel(Parcel in) {
            return new ChestCycle(in);
        }

        @Override
        public ChestCycle[] newArray(int size) {
            return new ChestCycle[size];
        }
    };
}
