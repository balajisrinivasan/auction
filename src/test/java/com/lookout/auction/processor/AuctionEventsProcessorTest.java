package com.lookout.auction.processor;

import com.lookout.auction.exception.AuctionEventProcessorException;
import com.lookout.auction.model.AuctionResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lookout.auction.processor.AuctionEventsSourceType.FILE;
import static org.junit.jupiter.api.Assertions.*;

class AuctionEventsProcessorTest {

    @Test
    void test_auctionEventsFileProcessor() throws AuctionEventProcessorException {
        String sourceFile = "classpath:auctionItems.json";
        AuctionEventsProcessor eventsProcessor = AuctionEventsProcessorFactory.getAuctionEventProcessor(FILE);
        eventsProcessor.setIngestRate(20);
        eventsProcessor.ingest(sourceFile);

        List<AuctionResult> results = eventsProcessor.getAllAuctionResults();

        assertEquals(3, results.size());

        Map<String, AuctionResult> auctionResultByAuctionId = results.stream()
                .collect(Collectors.toMap(AuctionResult::getId, r -> r));

        // auction result for Bicycle
        AuctionResult bicycleAuctionResult = auctionResultByAuctionId.get("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd");
        assertEquals("Bicycle", bicycleAuctionResult.getItemName());
        assertEquals("Amanda", bicycleAuctionResult.getName());
        assertEquals(85, bicycleAuctionResult.getHighestBid());

        // auction result for Scooter
        AuctionResult scooterAuctionResult = auctionResultByAuctionId.get("85ce0c8f-da34-4587-8fde-94a57ff75ae1");
        assertEquals("Scooter", scooterAuctionResult.getItemName());
        assertEquals("Alice", scooterAuctionResult.getName());
        assertEquals(722, scooterAuctionResult.getHighestBid());

        // auction result for Boat
        AuctionResult boatAuctionResult = auctionResultByAuctionId.get("046383dc-1348-4858-acb2-24080c5f255f");
        assertEquals("Boat", boatAuctionResult.getItemName());
        assertEquals("Aaron", boatAuctionResult.getName());
        assertEquals(3001, boatAuctionResult.getHighestBid());
    }

}