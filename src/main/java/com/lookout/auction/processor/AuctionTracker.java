package com.lookout.auction.processor;

import com.lookout.auction.model.AuctionEvent;
import com.lookout.auction.model.AuctionResult;

import java.util.List;

public interface AuctionTracker {

    void processEvent(AuctionEvent event);

    List<AuctionResult> getAllAuctionResult();
}
