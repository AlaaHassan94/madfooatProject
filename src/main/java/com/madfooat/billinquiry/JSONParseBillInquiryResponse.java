package com.madfooat.billinquiry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



public class JSONParseBillInquiryResponse implements ParseBillInquiryResponse {
    @Override
    public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {
		       
    	
    	List<Bill> billsListJSON = new ArrayList<Bill>();
		   ObjectMapper objectMapper = new ObjectMapper();	   
		   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		   objectMapper.setDateFormat(format);
		
		     List<Bill> receivedData = null;
			try {
				receivedData = objectMapper.readValue(billerResponse,objectMapper.getTypeFactory().constructCollectionType(List.class, Bill.class));
			} catch (IOException e) {
				throw new Error("ER: Can't parse to Invalid Data JSON :(");
			}
				// Cheak if Json Data Found
		 	
				 if (receivedData.size() <= 0 || receivedData == null) {
					 
					 throw new Error("ER: No JSON Data Found :(");
		
				 }
				 Date dueDate;
				 
				 for (int i = 0; i < receivedData.size(); i++) {
					 
						 dueDate = receivedData.get(i).getDueDate();
						 //Cheak dueDate 
						 //
						   if(dueDate == null ) {
							 throw new InvalidBillInquiryResponse("ER: dueDate Value Not Found :(");
							 
						   	}
							System.out.println("dueDate is :"+dueDate);
							Date current = new Date();
				       		System.out.println("current date is " +current);
				       	    // 
				       	    //Cheak Date of dueDate value if future or not
				       		
				       		if(dueDate.after(current)) {
				       			throw new Error("ER: Invalid dueDate Date ,date Should not be future date :(");
				       	   	}
				       	    // 
				       	    //Cheak Date of dueAmount value 
				       		
							BigDecimal dueAmount = receivedData.get(i).getDueAmount();
							 if(dueAmount == null ) {
								 throw new InvalidBillInquiryResponse("ER: dueAmount Value Not Found :(");
								 
							 }
							System.out.println("dueAmount is : "+dueAmount);
							String[] splitterAmount = dueAmount.toString().split("\\.");
							//
			       			// Cheak dueAmount Value if Correct Format
							if(splitterAmount[0].length() > 3 || splitterAmount[1].length() > 3) {
			       			
			       			throw new Error("ER :Invalid dueAmount format :(");
							}
			       		 
			          	   // Create Object of Bill o assign Values
				       	   //	
				       		Bill billingData= new Bill();
				       		billingData.setDueDate(dueDate);
				       		billingData.setDueAmount(dueAmount);
				       		
				       		//
				       	    // Cheak Fees if found
				       		
				       		BigDecimal Fees = receivedData.get(i).getFees();
				       		if( Fees != null) {
				       				
				       			System.out.println("Fees is"+ Fees);
				       			String[] splitterFees = Fees.toString().split("\\.");
				       		       //	
			            		   // Cheak Value of Fees
			       				   
				       				if(splitterFees[0].length()  > 3 || splitterFees[1].length() > 3) {
				       					throw new InvalidBillInquiryResponse("ER :Invalid Fees format :(");
				       				}else if(dueAmount.doubleValue()  <  Fees.doubleValue()) {
				       					
				       					throw new InvalidBillInquiryResponse("ER :Invalid Fees Value,it's greater than dueAmount :(");
				       				}else {
				       					
				       					billingData.setFees(Fees);
				       				}
				       			
				       		}
				       		// Add Bill Object to List
				       		//
				       		billsListJSON.add(billingData);
				       		
				 }//end loop
				       		
		return billsListJSON;
    }
}
