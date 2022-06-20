package com.lookout.auction.processor;

import com.lookout.auction.model.AuctionEvent;
import com.lookout.auction.model.AuctionResult;
import com.lookout.auction.util.AuctionBidCalculatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class InMemoryAuctionTracker implements AuctionTracker {

    private final Logger logger = LoggerFactory.getLogger(InMemoryAuctionTracker.class);

    private static final String NEW_ITEM_EVENTTYPE = "newItem";
    private static final String BID_EVENT_TYPE = "bid";

    // stores the auction end time for all auctions
    private final Map<String, Long> auctionIdByAuctionEndTime = new HashMap<>();
    // stores the highest bid for an auction by id
    private final Map<String, AuctionEvent> auctionIdByHighestBid = new HashMap<>();

    @Override
    public void processEvent(AuctionEvent event) {
        if (NEW_ITEM_EVENTTYPE.equals(event.getType())) {
            createNewAuction(event);
        } else if (BID_EVENT_TYPE.equals(event.getType())) {
            bidOnExistingAuction(event);
        }
    }

    @Override
    public List<AuctionResult> getAllAuctionResult() {
        return auctionIdByHighestBid.keySet().stream()
                .map(key -> {
                    AuctionEvent winningBid = auctionIdByHighestBid.get(key);
                    Long auctionExpireTime = auctionIdByAuctionEndTime.get(winningBid.getItem());
                    boolean hasAuctionEnded = Instant.now().toEpochMilli() > auctionExpireTime;

                    AuctionResult result = new AuctionResult();
                    result.setId(key);
                    result.setItemName(winningBid.getItemName());
                    result.setName(winningBid.getName());
                    result.setHighestBid(winningBid.getCurrentBid());
                    result.setWinningBid(hasAuctionEnded);

                    return result;
                }).collect(Collectors.toList());
    }

    /* Logic: Auction bid with the highest possible bid value always wins.
       Auction bid with the second highest possible bid value is the minimum bid value for the winning bid
     */
    private void bidOnExistingAuction(AuctionEvent auctionEvent) {
        String auctionId = auctionEvent.getItem();
        logger.info("Adding a new bid for auction: " + auctionId);

        Long auctionEndTime = auctionIdByAuctionEndTime.getOrDefault(auctionId, null);
        if (auctionEndTime == null || auctionEndTime < Instant.now().toEpochMilli()) {
            logger.info("Auction does not exist or has ended. Ignoring the event. Id: " +auctionId);
            return;
        }

        int highestPossibleBidValue = AuctionBidCalculatorUtil
                .calculateHighestBidAmount(auctionEvent.getStartingBid(), auctionEvent.getMaxBid(), auctionEvent.getBidIncrement());
        auctionEvent.setHighestPossibleBidValue(highestPossibleBidValue);

        AuctionEvent currentHighestBid = auctionIdByHighestBid.get(auctionId);
        if (currentHighestBid == null) {
            // first bid on the auction. Set current bid as starting value
            auctionEvent.setCurrentBid(auctionEvent.getStartingBid());
            auctionIdByHighestBid.put(auctionId, auctionEvent);
            return;
        }

        int currentHighestPossibleBidAmount = currentHighestBid.getHighestPossibleBidValue();

        // Identify bid that has highest possible bid amount
        // Calculate current bid amount where minimum bid amount is lower of the two highest possible bid amounts
        AuctionEvent highestBid = currentHighestPossibleBidAmount > auctionEvent.getHighestPossibleBidValue() ?
                currentHighestBid : auctionEvent;
        AuctionEvent secondHighestBid = currentHighestPossibleBidAmount > auctionEvent.getHighestPossibleBidValue() ?
                auctionEvent : currentHighestBid;
        int minBidAmount = secondHighestBid.getHighestPossibleBidValue();
        int currentBidValue = AuctionBidCalculatorUtil
                .calculateLeastPossibleBidAmount(minBidAmount, highestBid.getStartingBid(), highestBid.getBidIncrement());
        highestBid.setCurrentBid(currentBidValue);
        auctionIdByHighestBid.put(auctionId, highestBid);
    }

    private void createNewAuction(AuctionEvent auctionEvent) {
        logger.info("Creating a new auction for item: " + auctionEvent.getId());
        long auctionEndTimeInMillis = Instant.now()
                .plus(auctionEvent.getTimeOfAuction(), ChronoUnit.SECONDS).toEpochMilli();
        auctionIdByAuctionEndTime.put(auctionEvent.getId(), auctionEndTimeInMillis);
    }
}
