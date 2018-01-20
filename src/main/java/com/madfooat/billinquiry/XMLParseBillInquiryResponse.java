package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.List;



public class XMLParseBillInquiryResponse implements ParseBillInquiryResponse {
    @Override
    public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {

    	
    	 List<Bill> billsListXML = new ArrayList<Bill>();
		    
		 	try {
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         InputStream stream = new ByteArrayInputStream(billerResponse.getBytes("UTF-8"));
		        // Document stream = dBuilder.parse(new InputSource(new StringReader(Pasred.VALID_XML_RESPONSE)));
		         Document doc = dBuilder.parse(stream);
		         doc.getDocumentElement().normalize();
		         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		         NodeList nodeList = doc.getElementsByTagName("bill");
		         System.out.println("----------------------------");
	         
			         for (int temp = 0; temp < nodeList.getLength(); temp++) {
			            Node node = nodeList.item(temp);
			            String CurrentElement=node.getNodeName();
			            System.out.println("\nCurrent Element :" +CurrentElement);
		            
			            if (node.getNodeType() == Node.ELEMENT_NODE) {
			            	
				               Element element = (Element) node;
				               String billID= element.getAttribute("id");
				               System.out.println("bill id is : " + billID);   
				               
				               String dueDateValue = "";
				               // Cheak dueDate Tag if found and it's Value
				               //
				               if(element.getElementsByTagName("dueDate").getLength() == 0 || 
				            		   element.getElementsByTagName("dueDate").item(0).getTextContent().trim().equals("")) {
				            		   
				            	   throw new InvalidBillInquiryResponse(" TagName <dueDate> Or it's Value Not found :(",billID);
				               	}else {
				               		dueDateValue = element.getElementsByTagName("dueDate").item(0).getTextContent();
				               		System.out.println("dueDate is :" + dueDateValue);
				               	}
				               	// 
				                //parse dueDate String  value to Date
					       		Date current = new Date();
					       		System.out.println("current date is " +current);
					       		Date ParseStringToDate = null;
					       		try {
						       		 ParseStringToDate = new SimpleDateFormat("dd-MM-yyyy").parse(dueDateValue); 					       	
						       		 System.out.println("date format is "+ ParseStringToDate);
					       		} catch (ParseException e) {
					       		    System.out.println("Invalid Date Format :"+ e.getMessage());
					       		}
					       		
					       	     // 
					       	    //Cheak Date of dueDate value if future or not
					       		
					       		  if(ParseStringToDate.after(current)) {
					       			  
					       			 throw new InvalidBillInquiryResponse(billID,ParseStringToDate);
					       			
					       		  } 					       		  				       		  
					       		  String dueAmountValue ="";
					       		  // 
						       	  //Cheak dueAmount Tag if found and it's Value ifound
						       	    
					       		if(element.getElementsByTagName("dueAmount").getLength() == 0 || 
					            		   element.getElementsByTagName("dueAmount").item(0).getTextContent().trim().equals("")) {
					            		   
					            	   throw new InvalidBillInquiryResponse(" TagName <dueAmount> Or it's Value Not found :( ",billID);
					               	}else {
					               		dueAmountValue = element.getElementsByTagName("dueAmount").item(0).getTextContent();
					               		System.out.println("dueAmount is :" + dueAmountValue);
					               	}
					       							       		
					       		String[] splitterAmount = dueAmountValue.toString().split("\\.");
					       			//
					       			// Cheak dueAmount Value if Correct Format
					       		if(splitterAmount[0].length() > 3 || splitterAmount[1].length() > 3) {
					       			
					       			throw new Error("Invalid dueAmount format with billID ="+billID);
					       		}
					       		
					       		//
					       		// Create Object of Bill o assign Values
					       		
					       		Bill billingData= new Bill();
					       		billingData.setDueDate(ParseStringToDate);
					       		//
					       		// Convert dueAmount String to  BigDecimal value
					       		
					       		BigDecimal bigdecimal = new BigDecimal(dueAmountValue);
					       		billingData.setDueAmount(bigdecimal);
					       		
					       		//
					       	    // Cheak Fees if found
					       		
					       		if(element.getElementsByTagName("fees").getLength() != 0 ) { 
					       				   //	
					            		   // Cheak Value of Fees
					       				   
					       		   if(!element.getElementsByTagName("fees").item(0).getTextContent().trim().equals("")) {
					       			
					       				Double ValueofAmount = Double.parseDouble(dueAmountValue);
					       				String ValueofFees = element.getElementsByTagName("fees").item(0).getTextContent();
					       				 System.out.println("Fees is"+ ValueofFees);
					       				String[] splitterFees = ValueofFees.toString().split("\\.");
					       					
					       					if(splitterFees[0].length() > 3 ||splitterFees[1].length() > 3 ||
					       							 ValueofAmount < Double.parseDouble(ValueofFees)) {
					       						throw new Error("Invalid Fees format with billID ="+billID);
					       					}else {
					       						bigdecimal = new BigDecimal(ValueofFees);
					       						billingData.setFees(bigdecimal);
					       					}
	
					       				
						       			}					       			
					       		}
					       		
					       		/*
					       		 *  Add  Values to List
					       		 */
					       		billsListXML.add(billingData);
					       		
					               
					              
				            } // end cheak node
			                 //
			            
			         } // end for loop
			         	//
		       } catch (Exception e) {
					   System.out.println(e.getMessage());
					      }
			 
		 return billsListXML;
    }
}
