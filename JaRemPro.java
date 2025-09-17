import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class JaRemPro implements Serializable{
    private static final ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
    public static final List<String> reminders=new ArrayList<>();
    private static final URL [] iconPaths={JaRemPro.class.getResource("/Images/icons/Bird.png"),JaRemPro.class.getResource("/Images/icons/Clock.png"),JaRemPro.class.getResource("/Images/icons/Dog.png"),JaRemPro.class.getResource("/Images/icons/Hen.png"),JaRemPro.class.getResource("/Images/icons/Snail.png"),JaRemPro.class.getResource("/Images/icons/Monitor.png"),JaRemPro.class.getResource("/Images/icons/Plane.png")};
    private static final Random rand = new Random();
    public static List<String> tasks = new ArrayList<>();
    public static List<String> times = new ArrayList<>();
    public static List<Date> dates = new ArrayList<>();
    public static final String FILE_NAME = "reminders.dat";

    public JaRemPro(List<String> tasks, List<String> times, List<Date> remDates){
        this.tasks=tasks;
        this.times=times;
        this.dates=remDates;
    }
    public static void main(String[] args) {
        JaRemPro saved = JaRemPro.loadReminders();
        if(saved!=null){
            showReminder(saved.tasks, saved.times, saved.dates);
        }
         Date today = new Date();
         if(args.length>=2){
        
        }
        else if(args.length == 1 && args[0].equalsIgnoreCase("-silent")){

        }
        else{
            SwingUtilities.invokeLater(()->new reminderInputGUI());
        }
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
                    dates.add(today);
                }
                i=k-1;
            }
        }
        if(tasks.size()!=times.size()){
            JFrame frame=new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, "Tasks and times do not match. Please check your input.", "JaRemPro", JOptionPane.WARNING_MESSAGE);
            frame.dispose();
            return;
        }
        showReminder(tasks, times, dates);
    }
    private static ImageIcon randomIcon(int height, int width){
            URL path=iconPaths[rand.nextInt(iconPaths.length)];
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

    }
    public static void showReminder(List<String> tasks, List<String> times, List<Date> remDate) {
        String[] options={"Ok","Snooze"};
        for (int i = 0; i < tasks.size(); i++) {
            String time=times.get(i);
            String task=tasks.get(i);
            Date date=remDate.get(i);
            time = time.trim().toUpperCase();
            LocalTime remTime;
            if (time.contains("AM") || time.contains("PM")) {
                DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.ENGLISH);
                remTime = LocalTime.parse(time.toUpperCase(), formatter12);

            } else {
                DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("HH:mm");
                remTime = LocalTime.parse(time, formatter24);
            }
            int hour = remTime.getHour();
            int minute =  remTime.getMinute();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date finalRemTime = cal.getTime(); 

            long delay = finalRemTime.getTime() - System.currentTimeMillis();
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
                    @Override
                    public void run() {
                        JFrame frame=new JFrame();
                        frame.setAlwaysOnTop(true);
                        ImageIcon icon=randomIcon(50, 50);
                        SwingUtilities.invokeLater(()->{
                            int choice=JOptionPane.showOptionDialog(frame, remTask, "JaRemPro - Reminder", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
                            int index = tasks.indexOf(remTask);
                            switch (choice){
                                case 0:
                                if(!tasks.isEmpty() && !times.isEmpty() && !remDate.isEmpty() && index>0 && index<=tasks.size()){
                                reminders.remove(index);
                                tasks.remove(index);
                                times.remove(index);
                                remDate.remove(index);
                                JaRemPro remainingReminders1 = new JaRemPro(tasks, times, remDate);
                                saveReminders(remainingReminders1);
                                }
                                if(reminders.isEmpty()){
                                    System.exit(0);
                                }
                                frame.dispose();
                                break;
                                case 1:{
                                String input=JOptionPane.showInputDialog(frame,"Snooze time in minutes:","JaRemPro",JOptionPane.QUESTION_MESSAGE);
                                try{
                                int snoozeTime=Integer.parseInt(input);
                                scheduler.schedule(()->SwingUtilities.invokeLater(this::run), snoozeTime, TimeUnit.MINUTES);
                                }
                                catch(NumberFormatException e){
                                    JOptionPane.showMessageDialog(frame, "Invalid Snooze Time", "JaRemPro", JOptionPane.WARNING_MESSAGE);
                                }

                            }break;

                            case -1:
                                if(!tasks.isEmpty() && !times.isEmpty() && !remDate.isEmpty() && index>0 && index<=tasks.size()){
                                reminders.remove(index);
                                tasks.remove(index);
                                times.remove(index);
                                remDate.remove(index);
                                JaRemPro remainingReminders2 = new JaRemPro(tasks, times, remDate);
                                saveReminders(remainingReminders2);
                                }
                                System.exit(0);
                                break;
                            
                        }
                          });
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        timer.cancel();
                    }
                }, delay);
            }
            JOptionPane.showMessageDialog(null, "Reminder "+task+" : "+time+" added successfully!","JaRemPro",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void saveReminders(JaRemPro remainingReminders){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
            oos.writeObject(remainingReminders);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static JaRemPro loadReminders(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            return (JaRemPro) ois.readObject();
        }
        catch(FileNotFoundException e){
            return null;
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}