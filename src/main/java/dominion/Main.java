package dominion;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JOptionPane.showInputDialog(null, "Please choose a name", "Example 1",
                JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Amanda",
                        "Colin", "Don", "Fred", "Gordon", "Janet", "Jay",
                        "Joe", "Judie", "Kerstin", "Lotus", "Maciek", "Mark",
                        "Mike", "Mulhern", "Oliver", "Peter", "Quaxo", "Rita",
                        "Sandro", "Tim", "Will" }, "Joe");
    }
}
