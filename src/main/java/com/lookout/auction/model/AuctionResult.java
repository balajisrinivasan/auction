package com.lookout.auction.model;

public class AuctionResult {

    String id;

    String itemName;

    // Name of the bidder
    String name;

    int highestBid;

    // If false, the auction is still active
    boolean isWinningBid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }

    public boolean isWinningBid() {
        return isWinningBid;
    }

    public void setWinningBid(boolean winningBid) {
        isWinningBid = winningBid;
    }
}
