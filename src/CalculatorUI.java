
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class CalculatorUI extends JFrame implements ActionListener{
    private CalculatorModel calculatorModel;
    private JButton delBtn, clearBtn;
    private JButton NumberBtn[];
    private JButton functionBtn[];
    private JButton Add_btn, Sub_btn, Mul_btn, Div_btn, Sqr_btn, Sqrt_btn, equalBtn, decBtn;
    private JButton memoryBtn[];
    private JButton Madd_btn, Mdel_btn, Mclr_btn, Mres_btn; //buttons for memory operations
    private JPanel numberPanel;   //panel for the number buttons
    private JPanel functionPanel;  //panel for operation buttons
    private JPanel memoryPanel; //panel for memory buttons
    private JTextField text;

    private double number, result = 0, current, temp = 0;
    private char operator = ' ';
    private int x = 0;
    private String del= "", str = "";

    public CalculatorUI(){
        this.calculatorModel = new CalculatorModel();  //create a new calculator model object
        this.setSize(new Dimension(420, 420));   //adjusts the size of the window
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //create text field
        text = new JTextField();
        text.setSize(new Dimension(361, 45));
        text.setLocation(new Point(20, 20));
        text.setFont(new Font("Orbitron", Font.PLAIN, 20));
        text.setEditable(false);
        text.setForeground(Color.black);
        text.setHorizontalAlignment(JTextField.RIGHT);

        //////MEMORY PANEL STUFF/////////
        //create memory panel
        memoryPanel = new JPanel();
        memoryPanel.setLayout(new GridLayout(1, 4));
        memoryPanel.setSize(new Dimension(361, 50));
        memoryPanel.setLocation(new Point(20,70));

        //create memory buttons
        Madd_btn = new JButton("M+");
        Mdel_btn = new JButton("M-");
        Mclr_btn = new JButton("Mc");
        Mres_btn = new JButton("Mr");

        //add mem buttons to array
        memoryBtn = new JButton[4];
        memoryBtn[0] = Madd_btn;
        memoryBtn[1] = Mdel_btn;
        memoryBtn[2] = Mclr_btn;
        memoryBtn[3] = Mres_btn;

        //initialize memory button formatting
        for(int i = 0; i < 4; i++){
            memoryBtn[i].setFocusable(false);
            memoryBtn[i].setVerticalTextPosition(JButton.CENTER);
            memoryBtn[i].setHorizontalTextPosition(JButton.CENTER);
            memoryBtn[i].setFont(new Font("Orbitron", Font.PLAIN, 20));
            memoryBtn[i].setSize(new Dimension(130, 30));
            memoryBtn[i].addActionListener(this);
            memoryBtn[i].setForeground(Color.black);
        }

        //add the 4 memory buttons to the memory panel
        for(int i=0; i<4 ; i++){
            memoryPanel.add(memoryBtn[i]); //add the memory button to the panel
        }

        /////////FUNCTION PANEL STUFF////////
        //creating function panel
        functionPanel = new JPanel();
        functionPanel.setLayout(new GridLayout(4,2));
        functionPanel.setSize(new Dimension(170, 200));
        functionPanel.setLocation(new Point(210, 130));

        //create function buttons
        Add_btn = new JButton("+");
        Sub_btn = new JButton("-");
        Mul_btn = new JButton("*");
        Div_btn = new JButton("/");
        Sqr_btn = new JButton("sqr");
        Sqrt_btn = new JButton("sqrt");
        equalBtn = new JButton("=");
        decBtn = new JButton(".");
        clearBtn = new JButton("CLR");
        delBtn = new JButton("DEL");

        //add function buttons to array
        functionBtn = new JButton[10];
        functionBtn[0] = Add_btn;
        functionBtn[1] = Sub_btn;
        functionBtn[2] = Mul_btn;
        functionBtn[3] = Div_btn;
        functionBtn[4] = Sqr_btn;
        functionBtn[5] = Sqrt_btn;
        functionBtn[6] = delBtn;
        functionBtn[7] = clearBtn;
        functionBtn[8] = decBtn;
        functionBtn[9] = equalBtn;

        //initialize function button formatting
        for(int i = 0; i < 10; i++){
            functionBtn[i].setFocusable(false);
            functionBtn[i].setVerticalTextPosition(JButton.CENTER);
            functionBtn[i].setHorizontalTextPosition(JButton.CENTER);
            functionBtn[i].setFont(new Font("Orbitron", Font.PLAIN, 20));
            functionBtn[i].setSize(new Dimension(130, 30));
            functionBtn[i].addActionListener(this);
            functionBtn[i].setForeground(Color.black);
        }

        //add the 8 function buttons to the function panel
        for(int i=0; i<8 ; i++){
            functionPanel.add(functionBtn[i]); //add the function button to the panel
        }


        //////////NUMBER PANEL STUFF/////////
        //create number panel
        numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 3));
        numberPanel.setSize(new Dimension(180, 200));
        numberPanel.setLocation(new Point(20, 130));

        //create & format number buttons
        NumberBtn = new JButton[10];
        for(int i= 0; i < 10; i++){
            NumberBtn[i] = new JButton(String.valueOf(i));
            NumberBtn[i].setFocusable(false);
            NumberBtn[i].setFont(new Font("Orbitron", Font.PLAIN, 20));
            NumberBtn[i].setSize(new Dimension(10, 10));
            NumberBtn[i].setVerticalTextPosition(JButton.CENTER);
            NumberBtn[i].setHorizontalTextPosition(JButton.CENTER);
            NumberBtn[i].addActionListener(this);
            NumberBtn[i].setForeground(Color.black);
        }

        //Adding buttons to the number panel
        numberPanel.add(NumberBtn[7]);
        numberPanel.add(NumberBtn[8]);
        numberPanel.add(NumberBtn[9]);

        numberPanel.add(NumberBtn[6]);
        numberPanel.add(NumberBtn[5]);
        numberPanel.add(NumberBtn[4]);

        numberPanel.add(NumberBtn[3]);
        numberPanel.add(NumberBtn[2]);
        numberPanel.add(NumberBtn[1]);

        numberPanel.add(functionBtn[8]);
        numberPanel.add(NumberBtn[0]);
        numberPanel.add(functionBtn[9]);

        //adding all buttons and panels to this calculator
        this.add(text);
        this.add(numberPanel);
        this.add(functionPanel);
        this.add(memoryPanel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //TODO: move the setText stuff to the end if I can
        //check if it is an operation
        for(int i = 0; i<6; i++){
            if (e.getSource() == functionBtn[i]){
                calculatorModel.handleOperator(i);
//                if (i == 4 || i == 5){ //update the screen after a call to sqrt or sqr
//                    text.setText(calculatorModel.getLastResult());
//                }
            }
        }

        if (e.getSource() == functionBtn[6]) {  //DELETE
            calculatorModel.delete();
            text.setText(calculatorModel.getCurOperand()); //redisplay the updated operand
        }

        if (e.getSource() == functionBtn[7]) {  //CLEAR
            calculatorModel.clear();
            text.setText(calculatorModel.getCurOperand()); //redisplay the current operand which should be nothing
        }

        if (e.getSource() == functionBtn[8]) {  //decimal
            calculatorModel.handleDecimal();
            text.setText(calculatorModel.getCurOperand()); //redisplay the current operand
        }

        if (e.getSource() == functionBtn[9]) {  //Equal
            text.setText(calculatorModel.performCalculation());
        }

        if (e.getSource() == memoryBtn[0]) {  //M+
            calculatorModel.memoryAdd();
            text.setText("M: " + calculatorModel.getMemoryValue()); //display the new memory value to the screen
        }
        if (e.getSource() == memoryBtn[1]) {  //M-
            calculatorModel.memorySubtract();
            text.setText("M: " + calculatorModel.getMemoryValue()); //display the new memory value to the screen
        }
        if (e.getSource() == memoryBtn[2]) {  //M_clear
            calculatorModel.memoryClear();
        }
        if (e.getSource() == memoryBtn[3]) {  //M_recall
            calculatorModel.memoryRecall();
            text.setText(calculatorModel.getCurOperand());  //display the recalled memory val to the screen
        }

        for(int i=0; i<10; i++){ //handle number buttons
            if(e.getSource() == NumberBtn[i]) {
                calculatorModel.handleNumber(i);
                text.setText(calculatorModel.getCurOperand());
            }
        }

    }

    public static void main(String args[]){
        new CalculatorUI();
    }

}