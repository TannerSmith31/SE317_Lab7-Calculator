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
}