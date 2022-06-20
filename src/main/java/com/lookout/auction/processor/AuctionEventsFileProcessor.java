package com.lookout.auction.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lookout.auction.exception.AuctionEventProcessorException;
import com.lookout.auction.model.AuctionEvent;
import com.lookout.auction.model.AuctionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class AuctionEventsFileProcessor implements AuctionEventsProcessor{

    private final Logger logger = LoggerFactory.getLogger(AuctionEventsFileProcessor.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuctionTracker tracker = new InMemoryAuctionTracker();

    private int ingestRatePerSecond = 1;

    @Override
    public void ingest(String sourceFile) throws AuctionEventProcessorException {
        logger.info("Ingesting auction events from source file: " +sourceFile);
        try {
            List<AuctionEvent> auctionEvents = parseAuctionEventsSourceFile(sourceFile);
            int counter = 0;
            for (AuctionEvent auctionEvent: auctionEvents) {
                tracker.processEvent(auctionEvent);
                // if ingest rate is set to 5, the ingestor pauses for a second after processing 5 events
                if (++counter % ingestRatePerSecond == 0) {
                    Thread.sleep(1000);
                }
            }
            logger.info("Successfully processed all auction events.");
        } catch (IOException| InterruptedException e) {
            throw new AuctionEventProcessorException("Failed to process event source file");
        }
    }

    @Override
    public List<AuctionResult> getAllAuctionResults() {
        logger.info("Retrieving auction results...");
        return tracker.getAllAuctionResult();
    }

    @Override
    public void setIngestRate(int ingestRate) {
        this.ingestRatePerSecond = ingestRate;
    }

    private List<AuctionEvent> parseAuctionEventsSourceFile(String sourceFile)
            throws IOException {
        // loading the file into memory as test file is small.
        // if files were large, this would using a file buffer streamer to parse contents
        String jsonString = Files.readString(ResourceUtils.getFile(sourceFile).toPath());
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(jsonString,
                typeFactory.constructCollectionType(List.class, AuctionEvent.class));
    }
}
