import java.awt.*;
import java.util.Date;
import javax.swing.*;

public class reminderInputGUI extends JFrame{
    private JTextField taskField;
    private JTextField timeField;

    public reminderInputGUI(){
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        setContentPane(content);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon bannerImage = new ImageIcon(reminderInputGUI.class.getResource("/Images/Banner.png"));
        Image scaledImage = bannerImage.getImage().getScaledInstance(350, 125, Image.SCALE_SMOOTH);
        bannerImage=new ImageIcon(scaledImage);
        JLabel Banner = new JLabel(bannerImage);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        content.add(Banner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        content.add(new JLabel ("Task"), gbc);

        taskField = new JTextField(24);
        Dimension d1 = taskField.getPreferredSize();
        d1.height = 24;
        taskField.setPreferredSize(d1);
        gbc.weightx = 1.0;
        gbc.gridwidth = 4;
        gbc.gridx = 2;
        gbc.gridy = 1;
        content.add(taskField,gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 1;
        gbc.gridy = 2;
        content.add(new JLabel("Time"),gbc);

        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        timeField = new JTextField(10);
        Dimension d2 = timeField.getPreferredSize();
        d2.height = 24;
        timeField.setPreferredSize(d2);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 2;
        content.add(timeField,gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        content.add(new JLabel("Date"), gbc);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner,"dd-MM-yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(new Dimension(140, 25));
        gbc.gridx = 4;
        gbc.gridy = 2;
        content.add(dateSpinner, gbc);

        JButton button = new JButton("Add Reminder");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        button.setPreferredSize(new Dimension(24, 30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth=5;
        content.add(button, gbc);


        ImageIcon icon = new ImageIcon(reminderInputGUI.class.getResource("/Images/JaRemPro.png"));
        setIconImage(icon.getImage());


        setTitle("JaRemPro");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        button.addActionListener(e -> {
        String task=taskField.getText().trim();
        String time=timeField.getText().trim();
        Date remDate = (Date) dateSpinner.getValue();
        addReminder(task,time, remDate);
        });



    }
    private void addReminder(String task, String time, Date remDate){
        if(!task.isEmpty() && !time.isEmpty()){
            JaRemPro.tasks.add(task);
            JaRemPro.times.add(time);
            JaRemPro.dates.add(remDate);       
            JaRemPro.reminders.add(task);
            JaRemPro.showReminder(JaRemPro.tasks, JaRemPro.times, JaRemPro.dates);
        }
        else{
            JOptionPane.showMessageDialog(this, "Please enter both task and time.","JaRemPro",JOptionPane.WARNING_MESSAGE);
        }

    }

}