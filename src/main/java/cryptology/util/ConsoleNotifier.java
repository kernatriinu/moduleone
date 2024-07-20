package main.java.cryptology.util;

public class ConsoleNotifier implements Notifier{

    @Override
    public void notifyInfo(String message) {
        System.out.println(message);
    }
}
