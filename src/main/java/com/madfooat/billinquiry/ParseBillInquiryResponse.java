package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

import java.util.List;

public interface ParseBillInquiryResponse {

    List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse;

}
