import javax.swing.JOptionPane;

public class Test{
    public static void main(String[] args){
        String[] options={"Dismiss",Snooze};
        int choice=JOptionPane.showOptionDialog(
        frame,
        "⏰ Time for your reminder!",
        "Reminder",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]
);
System.out.println("Choice = " + choice);
    }
}