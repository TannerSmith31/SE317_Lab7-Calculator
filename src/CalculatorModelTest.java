import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CalculatorModelTest {
    private CalculatorModel model;

    @Test
    public void testValidNumberInput() {
        model = new CalculatorModel();
        int result = model.handleNumber(5);
        Assertions.assertEquals(0, result, "Valid number input should return 0");
    }

    @Test
    public void testExceedingMaximumNumberLength() {
        model = new CalculatorModel();
        for (int i = 0; i < 15; i++) {
            model.handleNumber(i);  // fill up to the max length
        }
        int result = model.handleNumber(1);  // attempt to exceed max length
        Assertions.assertEquals(-1, result,"Exceeding max length should return -1");
    }

    @Test
    public void testInvalidCurrentOperand() {
        model = new CalculatorModel();
        model.curOperand = 2; // setting curOperand to an invalid value
        int result = model.handleNumber(3);
        Assertions.assertEquals(-1, result, "Invalid curOperand should return -1");
    }

    @Test
    public void testValidOperatorInput() {
        model = new CalculatorModel();
        int result = model.handleOperator(0); // assuming 0 is for addition
        Assertions.assertEquals(0, result, "Valid operator input should return 0");
    }

    @Test
    public void testInvalidOperatorInput() {
        model = new CalculatorModel();
        int result = model.handleOperator(10); // assuming 10 is out of range
        Assertions.assertEquals(-1, result, "Invalid operator input should return -1");
    }

    @Test
    public void testOperatorInputWithIncompleteFirstOperand() {
        model = new CalculatorModel();
        int result = model.handleOperator(-1); // assuming 0 is for addition
        Assertions.assertEquals(-1, result, "Operator input without valid first operand should return -1");
    }
    @Test
    public void testAddingDecimalToNumber() {
        model = new CalculatorModel();
        model.handleNumber(1);  // Add initial digit
        int result = model.handleDecimal();  // Attempt to add a decimal
        Assertions.assertEquals(0, result, "Adding a decimal to a number should succeed");
        Assertions.assertEquals("1.",model.getCurOperand(),"Operand should contain a decimal");
    }

    @Test
    public void testAddingMultipleDecimalsToNumber() {
        model = new CalculatorModel();
        model.handleNumber(1);
        model.handleDecimal();  // First decimal
        int result = model.handleDecimal();  // Second decimal
        Assertions.assertEquals(-1, result, "Adding a second decimal should fail or be ignored");
    }

    @Test
    public void testAddingDecimalToEmptyOperand() {
        model = new CalculatorModel();
        int result = model.handleDecimal();  // Add decimal without any digits
        Assertions.assertEquals(0, result, "Adding a decimal to an empty operand should format correctly");
        Assertions.assertEquals(".",model.getCurOperand(),"Operand should start with '.'");
    }
    @Test
    public void testDeleteSingleCharacter() {
        model = new CalculatorModel();
        model.handleNumber(1);
        model.delete();
        Assertions.assertEquals("", model.getCurOperand(), "Deleting the only character should empty the operand");
    }

    @Test
    public void testDeleteLastCharacter() {
        model = new CalculatorModel();
        model.handleNumber(1);
        model.handleNumber(2);
        model.handleNumber(3);
        model.delete();
        Assertions.assertEquals("12", model.getCurOperand(), "Deleting last character should leave '12'");
    }

    @Test
    public void testDeleteEmptyOperand() {
        model = new CalculatorModel();
        model.delete();
        Assertions.assertEquals("", model.getCurOperand(), "Deleting from an empty operand should do nothing");
    }
    @Test
    public void testClear() {
        model = new CalculatorModel();
        model.handleNumber(1);
        model.handleOperator(0); // Assume 0 is an operator like addition
        model.handleNumber(2);
        model.clear();
        Assertions.assertEquals("", model.getCurOperand(), "Clear should reset current operand");
        Assertions.assertEquals("0.0", model.getMemoryValue(), "Clear should reset memory value");
        Assertions.assertEquals("", model.getCurOperand(), "Clear should reset current operator to initial state");
    }
    @Test
    public void testAddToMemoryAfterCalculation() {
        model = new CalculatorModel();
        model.handleNumber(5);
        model.handleOperator(0);  // Assume 0 is addition
        model.handleNumber(5);
        model.performCalculation();     // Assume this sets `memBool` true if successful
        int result = model.memoryAdd();
        Assertions.assertEquals("10.0", model.getMemoryValue(), "Memory should store the result of 5 + 5");
        Assertions.assertEquals(0, result, "Memory add should return success");
    }

    @Test
    public void testAddToMemoryWithoutCalculation() {
        model = new CalculatorModel();
        int result = model.memoryAdd();
        Assertions.assertEquals(-1, result, "Memory add without prior calculation should fail");
    }

    @Test
    public void testSubtractFromMemory() {
        model = new CalculatorModel();
        model.memoryAdd();  // Set some memory value initially for the test
        model.handleNumber(3);
        model.performCalculation();  // Sets `memBool` true
        model.memorySubtract();
        double memoryValue = Double.parseDouble(model.getMemoryValue());
        Assertions.assertFalse(memoryValue < 0, "Memory should decrease after subtraction");
    }

    @Test
    public void testMemoryRecall() {
        model = new CalculatorModel();
        model.handleNumber(7); // Set first operand
        model.handleOperator(0); // Assume 0 is a valid operator like addition
        model.handleNumber(5); // Set second operand
        model.performCalculation(); // This should compute and set memBool true if successful
        model.memoryAdd(); // Adds the result to memory
        model.memoryRecall(); // Recall the memory to current operand
        Assertions.assertEquals("12.0", model.getCurOperand(), "Recall should set current operand to memory value");
    }

    @Test
    public void testMemoryClear() {
        model = new CalculatorModel();
        model.memoryAdd();  // Set some memory value initially
        model.memoryClear();
        Assertions.assertEquals("0.0", model.getMemoryValue(), "Memory should be cleared to zero");
    }
    @Test
    public void testHandleValidOperator() {
        model = new CalculatorModel();
        int result = model.handleOperator(1); // Test subtraction, a valid operator
        Assertions.assertEquals(0, result, "Valid operator should return 0");
        Assertions.assertEquals(1, model.curOperator, "Operator should be set to subtraction");
    }

    @Test
    public void testRejectSequentialOperators() {
        model = new CalculatorModel();
        model.handleOperator(1); // Set an operator
        int result = model.handleOperator(2); // Try to set another operator immediately
        Assertions.assertEquals(-1, result, "Should return -1 when trying to set another operator without equals");
    }

    @Test
    public void testHandleInvalidOperator() {
        model = new CalculatorModel();
        int result = model.handleOperator(6); // Invalid operator
        Assertions.assertEquals(-1, result, "Invalid operator should return -1");
    }

    @Test
    public void testAddition() {
        model = new CalculatorModel();
        model.handleNumber(5);
        model.handleOperator(0); // Assuming 0 represents addition
        model.handleNumber(3);
        model.performCalculation();
        Assertions.assertEquals("8.0", model.getLastResult(), "Addition of 5 and 3 should be 8");
    }

    @Test
    public void testSubtraction() {
        model = new CalculatorModel();
        model.handleNumber(10);
        model.handleOperator(1); // Assuming 1 represents subtraction
        model.handleNumber(4);
        model.performCalculation();
        Assertions.assertEquals("6.0", model.getLastResult(), "Subtraction of 4 from 10 should be 6");
    }

    @Test
    public void testMultiplication() {
        model = new CalculatorModel();
        model.handleNumber(7);
        model.handleOperator(2); // Assuming 2 represents multiplication
        model.handleNumber(6);
        model.performCalculation();
        Assertions.assertEquals("42.0", model.getLastResult(), "Multiplication of 7 and 6 should be 42");
    }

    @Test
    public void testDivision() {
        model = new CalculatorModel();
        model.handleNumber(8);
        model.handleOperator(3); // Assuming 3 represents division
        model.handleNumber(2);
        model.performCalculation();
        Assertions.assertEquals("4.0", model.getLastResult(), "Division of 8 by 2 should be 4");
    }

    @Test
    public void testDivisionByZero() {
        model = new CalculatorModel();
        model.handleNumber(10);
        model.handleOperator(3); // Assuming 3 represents division
        model.handleNumber(0);
        model.performCalculation();
        Assertions.assertEquals("Infinity", model.getLastResult(), "Division by zero should return an error");
    }

    @Test
    public void testSquare() {
        model = new CalculatorModel();
        model.handleNumber(4);
        model.handleOperator(4); // Square
        Assertions.assertEquals("16.0", model.performCalculation(), "Square of 4 should be 16");
    }

    @Test
    public void testSquareRoot() {
        model = new CalculatorModel();
        model.handleNumber(9);
        model.handleOperator(5); // Square Root
        Assertions.assertEquals("3.0", model.performCalculation(), "Square root of 9 should be 3");
    }

    @Test
    public void testInvalidOperator() {
        model = new CalculatorModel();
        model.handleNumber(10);
        model.handleOperator(-1); // Invalid operator
        model.handleNumber(5);
        model.performCalculation();
        Assertions.assertEquals("0.0", model.getLastResult(), "Invalid operator should return an empty");
    }
}