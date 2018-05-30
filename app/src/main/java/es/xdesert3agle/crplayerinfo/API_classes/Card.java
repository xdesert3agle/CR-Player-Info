package es.xdesert3agle.crplayerinfo.API_classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable{
    private String name;
    private int level;
    private int maxLevel;
    private int count;
    private String rarity;
    private String icon;
    private String key;
    private int elixir;
    private String type;
    private int arena;
    private String description;
    private int id;
    private Upgrade upgrade;

    public Card(Parcel in){
        readFromParcel(in);
        this.upgrade = new Upgrade();
        this.upgrade.performUpgrade();
    }


    private void readFromParcel(Parcel in) {
        name = in.readString();
        level = in.readInt();
        maxLevel = in.readInt();
        count = in.readInt();
        rarity = in.readString();
        icon = in.readString();
        key = in.readString();
        elixir = in.readInt();
        type = in.readString();
        arena = in.readInt();
        description = in.readString();
        id = in.readInt();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(level);
        parcel.writeInt(maxLevel);
        parcel.writeInt(count);
        parcel.writeString(rarity);
        parcel.writeString(icon);
        parcel.writeString(key);
        parcel.writeInt(elixir);
        parcel.writeString(type);
        parcel.writeInt(arena);
        parcel.writeString(description);
        parcel.writeInt(id);
    }
    public Upgrade getUpgrade(){
        return this.upgrade;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getArena() {
        return arena;
    }

    public void setArena(int arena) {
        this.arena = arena;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ============================================================= U P G R A D E =============================================================
    public class Upgrade {
        private int potentialLevel;
        private int totalCost;
        private int remainingCards;
        private int numberOfLevels;
        private double time;
        private double neededRequests;

        public Upgrade(){
            remainingCards = count;
        }

        public int getPotentialLevel() {
            return potentialLevel;
        }

        public void setPotentialLevel(int potentialLevel) {
            this.potentialLevel = potentialLevel;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        public int getRemainingCards() {
            return remainingCards;
        }

        public void setRemainingCards(int remainingCards) {
            this.remainingCards = remainingCards;
        }

        public int getNumberOfLevels() {
            return numberOfLevels;
        }

        public void setNumberOfLevels(int numberOfLevels) {
            this.numberOfLevels = numberOfLevels;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public double getNeededRequests() {
            return neededRequests;
        }

        public void setNeededRequests(double neededRequests) {
            this.neededRequests = neededRequests;
        }

        public void performUpgrade() {
            int[] requiredCards = getRequiredCardsArray(rarity);

            if (level != maxLevel) {
                for (potentialLevel = level; remainingCards >= requiredCards[potentialLevel]; potentialLevel++){
                    remainingCards = remainingCards - requiredCards[potentialLevel];
                }
            }
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
    }

}