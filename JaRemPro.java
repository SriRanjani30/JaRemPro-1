import java.awt.Image;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class JaRemPro {
    private static final ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
    private static final List<String> reminders=new ArrayList<>();
    private static final String [] iconPaths={"icons/Bird.png","icons/Clock.png","icons/Dog.png","icons/Hen.png","icons/Snail.png","icons/Monitor.png","icons/Plane.png"};
    private static final Random rand = new Random();
    public static void main(String[] args) {
         if(args.length>0){
        
        }
        else{
            SwingUtilities.invokeLater(()->new reminderInputGUI());
        }
        List<String> tasks = new ArrayList<>();
        List<String> times = new ArrayList<>();
        String[] options={"Ok","Snooze"};
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-task")) {
                StringBuilder taskBuilder=new StringBuilder();
                int j=i+1;

                while(j< args.length && !args[j].startsWith("-")){
                taskBuilder.append(args[j]).append(" ");
                j++;
                }
                String taskStr=taskBuilder.toString().trim();
                if(taskStr.isEmpty()){
                    JFrame frame=new JFrame();
                    frame.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(frame, "Missing task after -task", "JaRemPro", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    return;
                }
                else{
                    tasks.add(taskStr);
                }
                i=j-1;
                reminders.add(taskStr);
            }
            if (args[i].equalsIgnoreCase("-time")) {
                StringBuilder timeBuilder=new StringBuilder();
                int k=i+1;
                while(k< args.length && !args[k].startsWith("-")){
                timeBuilder.append(args[k]).append(" ");
                k++;
                }
                String timeStr=timeBuilder.toString().trim();
                if(timeStr.isEmpty()){
                    JFrame frame=new JFrame();
                    frame.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(frame, "Missing time after -time", "JaRemPro", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    return;
                }
                else{
                    times.add(timeStr);
                }
                i=k-1;
            }
        }
        if(tasks.size()!=times.size()){
            JFrame frame=new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, "Tasks and times do not match. Please check your input.", "JaRemPro", JOptionPane.WARNING_MESSAGE);
            frame.dispose();
        }
        else{
        for (int i = 0; i < tasks.size(); i++) {
            String time=times.get(i);
            String task=tasks.get(i);
            time = time.trim().toUpperCase();
            LocalTime remTime;
            if (time.contains("AM") || time.contains("PM")) {
                DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.ENGLISH);
                remTime = LocalTime.parse(time.toUpperCase(), formatter12);

            } else {
                DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("HH:mm");
                remTime = LocalTime.parse(time, formatter24);
            }
            LocalTime currentTime = LocalTime.now();
            long delay = java.time.Duration.between(currentTime, remTime).toMillis();
            final String remTask = task;
            if (delay <= 0) {
                JFrame frame=new JFrame();
                frame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(frame, "It's already past the reminder time!", "JaRemPro", JOptionPane.WARNING_MESSAGE);
                frame.dispose();
                return;
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        JFrame frame=new JFrame();
                        frame.setAlwaysOnTop(true);
                        ImageIcon icon=randomIcon(50, 50);
                        SwingUtilities.invokeLater(()->{
                            int choice=JOptionPane.showOptionDialog(frame, remTask, "JaRemPro - Reminder", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
                            switch (choice){
                                case 0:
                                reminders.remove(remTask);
                                if(reminders.isEmpty()){
                                    System.exit(0);
                                }
                                frame.dispose();
                                break;
                                
                                case 1:
                                String input=JOptionPane.showInputDialog(frame,"Snooze time in minutes:","JaRemPro",JOptionPane.QUESTION_MESSAGE);
                                try{
                                int snoozeTime=Integer.parseInt(input);
                                scheduler.schedule(()->SwingUtilities.invokeLater(this::run), snoozeTime, TimeUnit.MINUTES);
                                }
                                catch(NumberFormatException e){
                                    JOptionPane.showMessageDialog(frame, "Invalid Snooze Time", "JaRemPro", JOptionPane.WARNING_MESSAGE);
                                }
                                break;

                            case -1:
                                System.exit(0);
                                break;

                            default:
                                System.out.println("Unknown Choice");
                                break;
                            
                            }
                          });
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        timer.cancel();
                    }
                }, delay);
            }
            System.out.println("Reminder set for " + remTime + " : " + task);
        }
    }
    }
    private static ImageIcon randomIcon(int height, int width){
        String path=iconPaths[rand.nextInt(iconPaths.length)];
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

    }
}