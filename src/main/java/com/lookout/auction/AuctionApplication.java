package com.lookout.auction;

import com.lookout.auction.exception.AuctionEventProcessorException;
import com.lookout.auction.processor.AuctionEventsProcessor;
import com.lookout.auction.model.AuctionResult;
import com.lookout.auction.processor.AuctionEventsProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.lookout.auction.processor.AuctionEventsSourceType.FILE;

@SpringBootApplication
public class AuctionApplication {

	private static final Logger logger = LoggerFactory.getLogger(AuctionApplication.class);
	private static final String SOURCE_FILE = "classpath:auctionItems.json";

	public static void main(String[] args) throws AuctionEventProcessorException {
		SpringApplication.run(AuctionApplication.class, args);

		AuctionEventsProcessor ingestor = AuctionEventsProcessorFactory.getAuctionEventProcessor(FILE);
		ingestor.setIngestRate(5); //default is 1 event per second
		ingestor.ingest(SOURCE_FILE);

		// isWinningBid=true implies the auction has ended. If false, auction is active and it's the highest bid yet
		for (AuctionResult result : ingestor.getAllAuctionResults()) {
			logger.info(String.format("Auction id: %s, Item Name: %s, Name: %s, Bid amount:  %d, isWinningBid: %s",
					result.getId(), result.getItemName(), result.getName(), result.getHighestBid(), result.isWinningBid()));
		}
	}
}
