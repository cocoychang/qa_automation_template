package com.sampleTest;


import com.base.BaseClass;
import com.utilities.DBConnection;
import com.utilities.DataProviders;
import com.utilities.ExtentManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class DBVerificationTest extends BaseClass {
	

	@Test(dataProvider="emplVerification", dataProviderClass = DataProviders.class)
	public void verifyEmployeeNameVerificationFromDB(String emplID, String empName) {
		
		SoftAssert softAssert = getSoftAssert();
		
		ExtentManager.logStep("Logging with Admin Credentails");
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
		ExtentManager.logStep("click on PIM tab");
		homePage.clickOnPIMTab();
		
		ExtentManager.logStep("Search for Employee");
		homePage.employeeSearch(empName);
		staticWait(1);
		
		ExtentManager.logStep("Get the Employee Name from DB");
		String employee_id=emplID;
		
		//Fetch the data into a map
		
		Map<String,String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
		
		String emplFirstName = employeeDetails.get("firstName");
		String emplMiddleName = employeeDetails.get("middleName");
		String emplLastName = employeeDetails.get("lastName");
		
		String emplFirstAndMiddleName =(emplFirstName+" "+emplMiddleName).trim();
		
		//Validation for first and middle name
		ExtentManager.logStep("Verify the employee first and middle name");
		softAssert.assertTrue(homePage.verifyEmployeeFirstAndMiddleName(emplFirstAndMiddleName),"First and Middle name are not Matching");
		
		//validation for last name
		ExtentManager.logStep("Verify the employee last name");
		softAssert.assertTrue(homePage.verifyEmployeeLastName(emplLastName));
		
		ExtentManager.logStep("DB Validation Completed");
		
		softAssert.assertAll();

	}

}
