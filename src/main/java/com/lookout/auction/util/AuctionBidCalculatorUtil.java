package com.lookout.auction.util;

public class AuctionBidCalculatorUtil {

    /*
    Given starting bid, max bid and increment, calculates the highest possible bid(HPB) amount.
    For e.g., if starting bid = 10, max bid = 50 and increment = 6, HPB = 46
     */
    public static int calculateHighestBidAmount(int startingBid, int maxBid, int increment) {
        int highestBid = startingBid;
        if (maxBid <= startingBid) {
            return startingBid;
        }
        while (highestBid <= maxBid) {
            highestBid += increment;
        }
        // one increment less than value greater than max bid is highest possible bid value
        return highestBid - increment;
    }

    /*
    Given starting bid, min bid and increment, calculates the least possible bid(LPB) amount.
    For e.g., if starting bid = 10, min bid = 50 and increment = 6, LPB = 52
     */
    public static int calculateLeastPossibleBidAmount(int minBid, int startingBid, int increment) {
        int result = startingBid;
        while (result < minBid) {
            result = result + increment;
        }
        return result;
    }
}
