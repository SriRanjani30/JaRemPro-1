import com.sun.tools.javac.Main;
import java.awt.*;
import javax.swing.*;

public class reminderInputGUI extends JFrame{
    private JTextField taskField;
    private JTextField timeField;

    public reminderInputGUI(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon bannerImage = new ImageIcon("Banner.jpg");
        Image scaledImage = bannerImage.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH);
        bannerImage=new ImageIcon(scaledImage);
        JLabel Banner = new JLabel(bannerImage);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        add(Banner, gbc);

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        add(new JLabel ("Task"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JTextField(15),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("Time"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JTextField(15),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth=2;
        add(new JButton("Add Reminder"), gbc);

        ImageIcon icon = new ImageIcon("JaRemPro.png");
        setIconImage(icon.getImage());


        setTitle("JaRemPro");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);



    }
    private void addReminder(){
        String task=taskField.getText().trim();
        String time=timeField.getText().trim();

        if(!task.isEmpty() && !time.isEmpty()){
            JOptionPane.showMessageDialog(this, "Reminder "+task+" : "+time+" added successfully!","JaRemPro",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Please enter both task and time.","JaRemPro",JOptionPane.WARNING_MESSAGE);
        }

    }

}