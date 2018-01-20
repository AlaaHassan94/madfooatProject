package com.madfooat.billinquiry;

import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;
import org.junit.Test;

import static com.madfooat.billinquiry.TestConstants.*;
import static org.junit.Assert.assertEquals;

public class ParseInquiryResponseTest {

    private ParseBillInquiryResponse parseXMLInquiryResponse = new XMLParseBillInquiryResponse();
    private ParseBillInquiryResponse parseJSONInquiryResponse = new JSONParseBillInquiryResponse();

    @Test
    public void givenValidXMLResponse_WhenParse_ThenReturnValidBillsList() {
        assertEquals(2, parseXMLInquiryResponse.parse(VALID_XML_RESPONSE).size());
    }

    @Test(expected = InvalidBillInquiryResponse.class)
    public void givenInvalidXMLResponse_WhenParse_ThenThrowException() {
        parseXMLInquiryResponse.parse(INVALID_XML_RESPONSE);
    }

    @Test
    public void givenValidJSONResponse_WhenParse_ThenReturnValidBillsList() {
        assertEquals(2, parseJSONInquiryResponse.parse(VALID_JSON_RESPONSE).size());
    }

    @Test(expected = InvalidBillInquiryResponse.class)
    public void givenInvalidJSONResponse_WhenParse_ThenThrowException() {
        parseJSONInquiryResponse.parse(INVALID_JSON_RESPONSE);
    }


}
