package com.lookout.auction.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuctionBidCalculatorUtilTest {

    @Test
    void test_calculateHighestBidAmount() {
        int result = AuctionBidCalculatorUtil.calculateHighestBidAmount(10,50,6 );
        assertEquals(46, result);

        result = AuctionBidCalculatorUtil.calculateHighestBidAmount(50,80,3 );
        assertEquals(80, result);

        result = AuctionBidCalculatorUtil.calculateHighestBidAmount(2800,3000,201 );
        assertEquals(2800, result);
    }

    @Test
    void test_calculateLeastPossibleBidAmount() {
        int result = AuctionBidCalculatorUtil.calculateLeastPossibleBidAmount(70,50,6 );
        assertEquals(74, result);

        result = AuctionBidCalculatorUtil.calculateLeastPossibleBidAmount(80,90,3 );
        assertEquals(90, result);

        result = AuctionBidCalculatorUtil.calculateLeastPossibleBidAmount(3000,3000,201 );
        assertEquals(3000, result);

        result = AuctionBidCalculatorUtil.calculateLeastPossibleBidAmount(3400,3000,201 );
        assertEquals(3402, result);
    }

}