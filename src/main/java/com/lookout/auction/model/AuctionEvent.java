package com.lookout.auction.model;

/*
Domain model that represents both "new item" and "bid" event types
 */
public class AuctionEvent {

    String id;

    String type;

    String name;

    String description;

    int timeOfAuction;

    String itemName;

    // id of the auction item
    String item;

    int startingBid;

    int maxBid;

    int bidIncrement;

    // this is computed using startingBid, maxBid and bidIncrement
    int currentBid;
    int highestPossibleBidValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeOfAuction() {
        return timeOfAuction;
    }

    public void setTimeOfAuction(int timeOfAuction) {
        this.timeOfAuction = timeOfAuction;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }

    public int getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(int maxBid) {
        this.maxBid = maxBid;
    }

    public int getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(int bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getHighestPossibleBidValue() {
        return highestPossibleBidValue;
    }

    public void setHighestPossibleBidValue(int highestPossibleBidValue) {
        this.highestPossibleBidValue = highestPossibleBidValue;
    }
}
