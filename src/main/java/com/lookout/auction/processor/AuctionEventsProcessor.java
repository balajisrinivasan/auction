package com.lookout.auction.processor;

import com.lookout.auction.exception.AuctionEventProcessorException;
import com.lookout.auction.model.AuctionResult;

import java.util.List;

public interface AuctionEventsProcessor {

    void ingest(String sourceName) throws AuctionEventProcessorException;

    void setIngestRate(int ingestRate);

    List<AuctionResult> getAllAuctionResults();
}
