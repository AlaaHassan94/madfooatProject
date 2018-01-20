When we call billers to inquiry about customers bills we received thier response containing all bills related to the customer, and ofcourse there are different message formats used by billers, i.e. Jordan Electricity repsonsed to our bill inquiry using JSON Format, while water authority responsed using XML format, and we have to validate the response for :
- Following fields should exists "Bill Due Date, Amount"
- Bill due date should not be future date.
- Amount should be of valid format in Jordainian Dinar.
- Fees is optional and incase its thier it should be valid format in Jordainian Dinar and less than Amount.

You required to write XML implementation "XMLParseBillInquiryResponse" and another JSON implementation "JSONParseBillInquiryResponse" of provided interface "com.madfooat.billinquiry.ParseBillInquiryResponse", in order to make the provided unit test cases pass, also you more than welcomed to add new test cases. 

