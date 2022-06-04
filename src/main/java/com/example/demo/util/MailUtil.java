package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class MailUtil extends Thread {

    private boolean isRun = true;
    Queue<SimpleMailMessage> queue = new ConcurrentLinkedDeque<>();

    @Autowired
    JavaMailSender javaMailSender;


    public MailUtil(int i) {
        super("Thread mail " + i);
        System.out.println("Thread mail " + i );
    }

    @Override
    public void run() {
        while (isRun) {
            System.out.println("Processing......!");
            synchronized (queue) {
                SimpleMailMessage mailMessage = queue.poll();
                if (mailMessage != null) {
                    this.process(mailMessage);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void process(SimpleMailMessage mailMessage) {
        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putData(SimpleMailMessage mailMessage) {
        queue.add(mailMessage);
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

}
