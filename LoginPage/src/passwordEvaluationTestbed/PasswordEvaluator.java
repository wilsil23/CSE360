package passwordEvaluationTestbed;

public class PasswordEvaluator {
    
    // Variables used to store the evaluation results and error information
    public static String passwordErrorMessage = ""; // Stores the error message if the password is invalid
    public static String passwordInput = ""; // Stores the password input string
    public static int passwordIndexofError = -1; // Index position where the error occurred in the password
    
    // Boolean flags to track whether certain password conditions are met
    public static boolean foundUpperCase = false;
    public static boolean foundLowerCase = false;
    public static boolean foundNumericDigit = false;
    public static boolean foundSpecialChar = false;
    public static boolean foundLongEnough = false;
    
    // Variables for internal state during password evaluation
    private static String inputLine = ""; // Stores the password input string during evaluation
    private static char currentChar; // The current character being evaluated
    private static int currentCharNdx; // Index of the current character
    private static boolean running; // Controls whether the while loop continues
    
    // This method visually prints the current state of the input string evaluation
    private static void displayInputState() {
        // Prints the password and highlights the current character being evaluated
        System.out.println(inputLine);
        System.out.println(inputLine.substring(0, currentCharNdx) + "?");
        System.out.println("The password size: " + inputLine.length() + "  |  The currentCharNdx: " + 
                currentCharNdx + "  |  The currentChar: \"" + currentChar + "\"");
    }
    
    // Method to evaluate the password based on certain conditions
    public static String evaluatePassword(String input) {
        // Reset error message and index
        passwordErrorMessage = "";
        passwordIndexofError = 0;
        inputLine = input; // Set the input line to the provided password
        currentCharNdx = 0; // Start at the first character of the password
        
        // Check if the input is empty and return an error if true
        if (input.length() <= 0) return "*** Error *** The password is empty!";
        
        // Set the current character to the first character in the input
        currentChar = input.charAt(0);
        passwordInput = input; // Store the input password for further use
        
        // Reset boolean flags to indicate whether conditions are met
        foundUpperCase = false; 
        foundLowerCase = false; 
        foundNumericDigit = false;
        foundSpecialChar = false;
        foundLongEnough = false;
        
        // Initialize the running variable to control the while loop
        running = true;
        
        // Loop through the input characters as long as we're still within the string length
        while (running) {
            // Display the current state of evaluation
            displayInputState();
            
            // Check if the current character is an uppercase letter
            if (currentChar >= 'A' && currentChar <= 'Z') {
                System.out.println("Upper case letter found");
                foundUpperCase = true;
            } 
            // Check if the current character is a lowercase letter
            else if (currentChar >= 'a' && currentChar <= 'z') {
                System.out.println("Lower case letter found");
                foundLowerCase = true;
            } 
            // Check if the current character is a digit
            else if (currentChar >= '0' && currentChar <= '9') {
                System.out.println("Digit found");
                foundNumericDigit = true;
            } 
            // Check if the current character is a special character from a predefined set
            else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {
                System.out.println("Special character found");
                foundSpecialChar = true;
            } 
            // If none of the conditions are met, an invalid character was found
            else {
                passwordIndexofError = currentCharNdx;
                return "*** Error *** An invalid character has been found!";
            }
            
            // Check if the length of the password is at least 8 characters
            if (currentCharNdx >= 7) {
                System.out.println("At least 8 characters found");
                foundLongEnough = true;
            }
            
            // Move to the next character
            currentCharNdx++;
            if (currentCharNdx >= inputLine.length()) // Check if we're past the end of the input string
                running = false; // Exit the loop if we've checked all characters
            else
                currentChar = input.charAt(currentCharNdx); // Set the current character to the next one in the input
            
            // Add a blank line for readability after each character check
            System.out.println();
        }
        
        // Build an error message for any conditions that were not satisfied
        String errMessage = "";
        if (!foundUpperCase)
            errMessage += "Upper case; ";
        
        if (!foundLowerCase)
            errMessage += "Lower case; ";
        
        if (!foundNumericDigit)
            errMessage += "Numeric digits; ";
        
        if (!foundSpecialChar)
            errMessage += "Special character; ";
        
        if (!foundLongEnough)
            errMessage += "Long Enough; ";
        
        // If no errors were found, return an empty string (meaning the password is valid)
        if (errMessage == "")
            return "";
        
        // Return the error message if conditions were not satisfied
        passwordIndexofError = currentCharNdx;
        return errMessage + "conditions were not satisfied";
    }
}
