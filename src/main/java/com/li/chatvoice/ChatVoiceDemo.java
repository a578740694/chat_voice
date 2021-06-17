package com.li.chatvoice;

import com.li.chatvoice.ai.NewJFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/10 10:53
 * @Description ChatVoiceDemo
 * ************************************
 */
public class ChatVoiceDemo {

    private final static String THREAD_3 = "thread_3";

    public static void main(String[] args) {
        // 显示应用 GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGlobalFontSetting(new Font("宋体", Font.PLAIN, 12));
                createAndShowGUI();
            }
        });
    }

    /**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        //使用Quaqua UI界面
        System.setProperty(
                "Quaqua.tabLayoutPolicy","wrap"
        );
        // set the Quaqua Look and Feel in the UIManager
        try {
            // 这句是必须要的，因为要先把原来默认的格式去掉
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(
                    ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel()
            );
        } catch (Exception e) {
            // take an appropriate action here
            e.printStackTrace();
        }

        // 创建 JFrame 实例
        JFrame frame = new JFrame("~智能聊天解闷~");
        // Setting the width and height of frame
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel, frame);

        // 设置界面可见
        frame.setVisible(true);
        NewJFrame.getInstance(frame);
    }

    private static void placeComponents(final JPanel panel, final JFrame frame) {
        final List<Integer> type = new ArrayList<>(1);
        type.add(0);

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        final JButton button5 = new JButton("停止");
        button5.setEnabled(false);
        button5.setBounds(20, 300, 110, 30);
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                killThreadByName(THREAD_3, panel);
            }
        });

        //顶部文本
        final JLabel jLabel = new JLabel("文字转语音");
        jLabel.setBounds(300,10,100,30);
        panel.add(jLabel);

        //第一个大输入框
        final JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)display.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(display);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setBounds(150,50,400,150);
        panel.add(scrollPane, BorderLayout.CENTER);

        //第二个大输入框
        final JTextArea userText = new JTextArea();
        userText.setLineWrap(true);
        userText.setBackground(new Color(255,255,255));
        userText.setBounds(150,230,400,100);
        userText.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    entry(type,userText,display,panel);
                }
            }
        });
        panel.add(userText);

        //第一个按钮
        JButton button1 = new JButton("文字转语音");
        button1.setBounds(20, 50, 110, 30);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type.add(0, 0);
                jLabel.setText("文字转语音");
                clean(userText,display,button5,panel);

            }
        });
        panel.add(button1);

        //第二个按钮
        JButton button2 = new JButton("闲聊模式");
        button2.setBounds(20, 100, 110, 30);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type.add(0, 1);
                jLabel.setText("闲聊模式");
                clean(userText,display,button5,panel);

            }
        });
        panel.add(button2);

        //第三个按钮
        JButton button3 = new JButton("闲聊+语音");
        button3.setBounds(20, 150, 110, 30);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type.add(0, 2);
                jLabel.setText("闲聊+语音模式");
                clean(userText,display,button5,panel);

            }
        });
        panel.add(button3);

        //第四个按钮
        JButton button4 = new JButton("智能语音互聊");
        button4.setBounds(20, 200, 110, 30);
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type.add(0, 3);
                jLabel.setText("智能语音互聊");
                clean(userText,display,button5,panel);

                button5.setEnabled(true);
            }
        });
        panel.add(button4);

        //第六个按钮
        JButton button6 = new JButton("重置");
        button6.setBounds(20, 250, 110, 30);
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clean(userText,display,button5,panel);
            }
        });
        panel.add(button6);

        //第六个按钮
        JButton button7 = new JButton("设置");
        button7.setBounds(550, 0, 50, 30);
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewJFrame.getInstance().setVisible(true);
            }
        });
        panel.add(button7);
    }

    private static void entry(List<Integer> type, JTextArea userText, JTextArea display, JPanel panel){
        final String text = userText.getText().trim();
        String myName = NewJFrame.getInstance().getMyName();
        if(display.getText().length() == 0){
            display.append(myName + ": " + text);
        }else {
            display.append("\n" + myName + ": " + text);
        }

        userText.setText(null);
        userText.setCaretPosition(0);

        new Thread(new Handler(type.get(0), text, display , panel), "thread_" + type.get(0)).start();

        if(type.get(0) == 3){
            userText.setEditable(false);
        }
    }

    private static void clean(JTextArea userText, JTextArea display, JButton button5, JPanel panel){
        display.setText(null);
        userText.setText(null);
        userText.setCaretPosition(0);
        button5.setEnabled(false);
        userText.setEditable(true);

        killThreadByName(THREAD_3, panel);
    }

    public static void killThreadByName(String threadName, JPanel panel) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) {
                t.stop();

                JOptionPane.showMessageDialog(panel, "已停止！", "消息", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static void initGlobalFontSetting(Font fnt){
        FontUIResource fontRes = new FontUIResource(fnt);
        for(Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }
}
