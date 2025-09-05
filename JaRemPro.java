import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

public class JaRemPro {
    public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();
        List<String> times = new ArrayList<>();
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
                    System.out.println("⚠️ Missing task after -task");
                }
                else{
                    tasks.add(taskStr);
                }
                i=j-1;


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
                    System.out.println("⚠️ Missing time after -time");
                }
                else{
                    times.add(timeStr);
                }
                i=k-1;
            }
        }
        if(tasks.size()!=times.size()){
            System.out.println("⚠️ Tasks and times do not match. Please check your input.");
            return;
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
                System.out.println("⏰ It's already past the reminder time!");
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, remTask, "JaRemPro", JOptionPane.INFORMATION_MESSAGE);
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        timer.cancel();
                    }
                }, delay);
            }
            System.out.println("✅ Reminder set for " + remTime + " → " + task);
            System.out.println(task);
            System.out.println(time);
        }
    }
    }
}