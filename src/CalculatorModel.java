import java.net.StandardSocketOptions;

public class CalculatorModel {
    private final int MAX_NUM_LENGTH = 15; //TODO: figure out what this needs to be
    private String[] operands;      //stores the users first and second operand
    private double memoryVal;       //the value stored in memory
    private double lastResult;      //stores last result from carying out an operator on the calculator
    public int curOperand;         //tells which operand is being operated on
    public int curOperator;        //tells current operator (0:+, 1:-, 2:*, 3:/, 4:sqr, 5:sqrt, -1: nothing)
    private boolean memBool;        //tells if the equal button has just been pressed


    //constructor for a new calculator model
    public CalculatorModel(){
        this.curOperand = 0;
        this.memBool = false;
        this.memoryVal = 0;     //initialize memory to be empty
        this.curOperator = -1;

        //initialize operands
        this.operands = new String[2];
        this.operands[0] = "";
        this.operands[1] = "";
    }

    //handles numbers input by users
    public int handleNumber(int num){
        memBool = false;    //remove memory edit access
        //handle invalid curOperand variable
        if(curOperand > 1 || curOperand < 0){
            System.out.println("ERROR: invalid curOperand in handleNumber()");
            return -1;
        }
        //handle the number being too large to display on the screen
        if(operands[curOperand].length() > MAX_NUM_LENGTH){
            System.out.println("ERROR: too long of number to be put on screen in handleNumber()");
            return -1;
        }
        operands[curOperand] = operands[curOperand] + Integer.toString(num);  //add the number to the curOperand
        return 0;
    }

    //TODO: handle operators + - x / ^2 sqrt
    public int handleOperator(int operator){
        memBool = false;    //remove memory edit access
        if(curOperand != 0){
            System.out.println("WARNING: cannot use 2 operands in a row before hitting '='");
            return -1;
        }
        if(operator < 0 || operator > 5){
            System.out.println("ERROR: invalid operator in handleOperator()");
            return -1;
        }
        curOperator = operator;
        curOperand = 1;         //you've entered an operator so go to next operand

        //perform the calculation if the operator is either a ^2 or sqrt becuase they only take one argument
//        if (operator == 4 || operator == 5){
//            performCalculation();
//        }
        return 0;
    }

    public int delete(){
        memBool = false;    //remove memory edit access
        //handle invalid curOperand variable
        if(curOperand > 1 || curOperand < 0){
            System.out.println("ERROR: invalid curOperand in handleNumber()");
            return -1;
        }

        //remove one digit from the current operand if the length is greater than 0
        if(operands[curOperand].length()>0){
            StringBuilder stringBuilder = new StringBuilder(operands[curOperand]);
            stringBuilder.deleteCharAt(operands[curOperand].length() - 1);
            operands[curOperand] = stringBuilder.toString();
        }
        return 0;
    }

    public int handleDecimal(){
        memBool = false;    //remove memory edit access
        //check if the current operand already has a decimal
        if(operands[curOperand].contains(".")){
            System.out.println("WARNING: Cannot add more than one decimal to a number");
            return -1;
        }

        operands[curOperand] = operands[curOperand] + "."; //add the decimal point to the current operand
        return 0;
    }

    //function to clear the calculator to blank start (including memory)
    public int clear(){
        //clear the current operand
        operands[0] = "";
        operands[1] = "";
        memoryVal = 0;      //reset memory val
        curOperand = 0;     //reset to take the first operand
        curOperator = -1;   //reset curent operator to be nothing
        memBool = false;    //remove memory edit access
        return 0;
    }

    //function to run a calculation based on curOperator and operands
    public String performCalculation(){
        //TODO: fill out functionality
        memBool = false;    //remove memory edit access
        double result = 0;
        String returnString = "";
        boolean calcSuccessful = true;
        double[] operandsAsDoubles = new double[2];

        //convert operands from strings to doubles
        for (int i = 0; i<2; i++){
            if (operands[i].equals("")){
                //make sure you have 2 arguments if you are doing calculation other than sqr and sqrt
                if(curOperator != 4 && curOperator != 5){
                    returnString = "SYNTAX ERROR";
                    calcSuccessful = false;
                    break;
                }
                operandsAsDoubles[i] = 0;
            }else{
                try{
                    operandsAsDoubles[i] = Double.parseDouble(operands[i]);
                }catch(Exception e){
                    System.out.println("ERROR: cannot parse operand " + i + " as a double in performCalculation()");
                    returnString = "ERROR";
                    calcSuccessful = false;
                    break;
                }
            }
        }

        //perform correct operation
        switch(curOperator){
            case 0:     //ADDITION (+)
                result = operandsAsDoubles[0] + operandsAsDoubles[1];
                break;

            case 1:     //SUBTRACTION (-)
                result = operandsAsDoubles[0] - operandsAsDoubles[1];
                break;

            case 2:     //MULTIPLICATION (*)
                result = operandsAsDoubles[0] * operandsAsDoubles[1];
                break;

            case 3:     //DIVISION (/)
                //account for division by 0
                if(operandsAsDoubles[1] == 0){
                    System.out.println("WARNING: division by 0");
                    returnString = "UNDEFINED";
                    calcSuccessful = false;
                }
                result = operandsAsDoubles[0] / operandsAsDoubles[1];
                break;

            case 4:     //square (^2)
                result = Math.pow(operandsAsDoubles[0], 2);
                break;

            case 5:     //sqrt
                result = Math.pow(operandsAsDoubles[0], 0.5);
                break;

            case -1:
                System.out.println("WARNING: Must press an operator before performing a calculation");
                returnString = "SYNTAX ERROR";
                calcSuccessful = false;

            default:
                System.out.println("ERROR: Invalid operation " + curOperator + " in performCalculation()");
                returnString = "ERROR";
                calcSuccessful = false;
        }

        //turn the result into the string that will be displayed if the calculation successfuly resulted in a double
        if (calcSuccessful){
            if(result > Math.pow(10, (MAX_NUM_LENGTH-1))){
                returnString = "Result too long";
                calcSuccessful = false;
            }else{
                returnString = Double.toString(result);
                if(returnString.length() > MAX_NUM_LENGTH){
                    returnString = returnString.substring(0,MAX_NUM_LENGTH); //cut off the decimal to what can be displayed
                }
            }
        }

        //reset operand variables
        curOperator = -1;
        curOperand = 0;
        operands[0] = "";
        operands[1] = "";
        lastResult = result;

        if(calcSuccessful){ //We are only allowed to add the result to memory if the calculation was successful
            memBool = true;
        }

        return returnString;
    }

    //function to handle memory add button (M+)
    //Adds a successful result to the value stored in memory
    public int memoryAdd(){
        //Check to see if being called after a successful operation execution
        if(!memBool){
            System.out.println("WARNING: Can only execute M+ directly after a successful result");
            return -1;
        }
        memoryVal = memoryVal + lastResult; //add the last result to the memoryVal
        //todo: do I set the memBool back to false here?
        return 0;
    }

    //function to handle the memory subtract button (M-)
    public int memorySubtract(){
        //Check to see if being called after a successful operation execution
        if(!memBool){
            System.out.println("WARNING: Can only execute M+ directly after a successful result");
            return -1;
        }
        memoryVal = memoryVal - lastResult; //subtracts the last result from the memoryVal
        //todo: do I set the memBool back to false here?
        return 0;
    }

    //function to handle the memory recall button (Mr)
    public int memoryRecall(){
        //TODO: memory recall
        operands[curOperand] = Double.toString(memoryVal);
        return 0;
    }

    //function to handle the memory clear button (Mc)
    public int memoryClear(){
        memoryVal = 0;
        return 0;
    }

    //function to return the current operand
    public String getCurOperand(){
        return operands[curOperand];
    }

    public String getLastResult(){
        return Double.toString(lastResult);
    }

    public String getMemoryValue(){
        return Double.toString(memoryVal);
    }
    public int getCurOperator(){return  curOperator;}

}