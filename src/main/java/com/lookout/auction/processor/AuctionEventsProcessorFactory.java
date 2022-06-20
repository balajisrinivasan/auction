package com.lookout.auction.processor;

public class AuctionEventsProcessorFactory {

    public static AuctionEventsProcessor getAuctionEventProcessor(AuctionEventsSourceType type) {
        if (AuctionEventsSourceType.FILE == type) {
            return new AuctionEventsFileProcessor();
        }

        throw new IllegalArgumentException("Invalid source type");
    }
}
