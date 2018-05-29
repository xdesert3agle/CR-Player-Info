package es.xdesert3agle.crplayerinfo.apiclasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Clan implements Parcelable{
    private String tag;
    private String name;
    private String role;
    private int donations;
    private int donationsReceived;
    private int donationsDelta;
    private Badge badge;

    public Clan(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        tag = in.readString();
        name = in.readString();
        role = in.readString();
        donations = in.readInt();
        donationsReceived = in.readInt();
        donationsDelta = in.readInt();
        badge = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Clan> CREATOR = new Creator<Clan>() {
        @Override
        public Clan createFromParcel(Parcel in) {
            return new Clan(in);
        }

        @Override
        public Clan[] newArray(int size) {
            return new Clan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(name);
        parcel.writeString(role);
        parcel.writeInt(donations);
        parcel.writeInt(donationsReceived);
        parcel.writeInt(donationsDelta);
        parcel.writeParcelable(badge, i);
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

    public int getDonations() {
        return donations;
    }

    public void setDonations(int donations) {
        this.donations = donations;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }

    public void setDonationsReceived(int donationsReceived) {
        this.donationsReceived = donationsReceived;
    }

    public int getDonationsDelta() {
        return donationsDelta;
    }

    public void setDonationsDelta(int donationsDelta) {
        this.donationsDelta = donationsDelta;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public void printClanInfo(){
        Log.d("Nombre", this.name);
        Log.d("Tag", "#" + this.tag);
    }
}
