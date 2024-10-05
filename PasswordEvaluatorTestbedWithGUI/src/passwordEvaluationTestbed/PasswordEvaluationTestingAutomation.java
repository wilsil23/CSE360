package passwordEvaluationTestbed;

public class PasswordEvaluationTestingAutomation {
	
	//instantiate variables for number of tests passed/failed
	static int numPassed = 0;
	static int numFailed = 0;
	public static void main(String[] args) {
		System.out.println("____________________________________________________________________________");
		System.out.println("\nTesting Automation");
		/*
		  Requirements for passwords:
		  At least one upper case letter 
		  At least one lower case letter 
		  At least one digit 
		  At least one special character 
		  At least 8 characters 
		*/
		//true because it has everything required
		performTestCase(1, "Aa!15678", true);
		//false has everything except no uppercase letters
		performTestCase(2, "aa!15678", false);
		//false has everything except only 7 digits
		performTestCase(3, "Aa!1567", false);
		//false doesn't have any digits
		performTestCase(4, "Aa!aaaaaa", false);
		//false doesn't have a special character
		performTestCase(5, "Aaaaaaaa1", false);
		//false only has uppercase letters
		performTestCase(6, "AAAAAAA1!", false);
		
		// Output summary of the test results
        System.out.println("____________________________________________________________________________");
        System.out.println();
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    // This method runs a test case by comparing the result of evaluating the password 
    // with the expected result (valid or invalid password).
    private static void performTestCase(int testCase, String inputText, boolean expectedPass) {
        System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
        System.out.println("Input: \"" + inputText + "\"");
        System.out.println("______________");
        System.out.println("\nFinite state machine execution trace:");
        
        // Evaluate the password using the PasswordEvaluator class
        String resultText = PasswordEvaluator.evaluatePassword(inputText);
        
        // Check if the password was evaluated as invalid
        if (!resultText.isEmpty()) {
            // If the password was expected to pass but failed
            if (expectedPass) {
                System.out.println("***Failure*** The password <" + inputText + "> is invalid." + 
                        "\nBut it was supposed to be valid, so this is a failure!\n");
                System.out.println("Error message: " + resultText);
                numFailed++;
            }
            // If the password was expected to fail and did fail
            else {            
                System.out.println("***Success*** The password <" + inputText + "> is invalid." + 
                        "\nBut it was supposed to be invalid, so this is a pass!\n");
                System.out.println("Error message: " + resultText);
                numPassed++;
            }
        }
        // Check if the password was evaluated as valid
        else {    
            // If the password was expected to pass and it did pass
            if (expectedPass) {    
                System.out.println("***Success*** The password <" + inputText + 
                        "> is valid, so this is a pass!");
                numPassed++;
            }
            // If the password was expected to fail but passed
            else {
                System.out.println("***Failure*** The password <" + inputText + 
                        "> was judged as valid" + 
                        "\nBut it was supposed to be invalid, so this is a failure!");
                numFailed++;
            }
        }
        // Display the evaluation of the password based on the password rules
        displayEvaluation();
    }

    // This method displays which password conditions were met and which were not,
    // based on the results from PasswordEvaluator.
    private static void displayEvaluation() {
        if (PasswordEvaluator.foundUpperCase)
            System.out.println("At least one upper case letter - Satisfied");
        else
            System.out.println("At least one upper case letter - Not Satisfied");

        if (PasswordEvaluator.foundLowerCase)
            System.out.println("At least one lower case letter - Satisfied");
        else
            System.out.println("At least one lower case letter - Not Satisfied");
    
        if (PasswordEvaluator.foundNumericDigit)
            System.out.println("At least one digit - Satisfied");
        else
            System.out.println("At least one digit - Not Satisfied");

        if (PasswordEvaluator.foundSpecialChar)
            System.out.println("At least one special character - Satisfied");
        else
            System.out.println("At least one special character - Not Satisfied");

        if (PasswordEvaluator.foundLongEnough)
            System.out.println("At least 8 characters - Satisfied");
        else
            System.out.println("At least 8 characters - Not Satisfied");
    }
}