package com.li.chatvoice.ai;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/13 14:53
 * @Description NewDialog
 * ************************************
 */
public class NewJFrame extends JFrame {

    private static NewJFrame dialog;

    private final JFrame frame;

    private String myName = "我";

    private String youName = "她";

    private NewJFrame(final JFrame frame) {
        super("设置");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        final JPanel panel = new JPanel();
        add(panel);

        panel.setLayout(null);

        JLabel label1 = new JLabel("标题: ");
        label1.setBounds(20, 20, 100, 30);
        panel.add(label1);

        final JTextField jTextField1 = new JTextField("~智能聊天解闷~");
        jTextField1.setBounds(60, 20, 200, 30);
        panel.add(jTextField1);

        JLabel label2 = new JLabel("我方名称: ");
        label2.setBounds(5, 60, 100, 30);
        panel.add(label2);

        final JTextField jTextField2 = new JTextField("我");
        jTextField2.setBounds(60, 60, 200, 30);
        panel.add(jTextField2);

        JLabel label3 = new JLabel("对方名称: ");
        label3.setBounds(5, 100, 100, 30);
        panel.add(label3);

        final JTextField jTextField3 = new JTextField("她");
        jTextField3.setBounds(60, 100, 200, 30);
        panel.add(jTextField3);

        JButton jButton = new JButton("确认");
        jButton.setBounds(120, 140, 60, 30);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(StringUtils.isBlank(jTextField1.getText()) ||
                        StringUtils.isBlank(jTextField2.getText()) ||
                        StringUtils.isBlank(jTextField3.getText())){
                    JOptionPane.showMessageDialog(panel, "数据为空！", "消息", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                frame.setTitle(jTextField1.getText());
                setMyName(jTextField2.getText());
                setYouName(jTextField3.getText());

                setVisible(false);
            }
        });
        panel.add(jButton);

        this.frame = frame;
    }

    public static NewJFrame getInstance(){
        return dialog;
    }

    public static NewJFrame getInstance(JFrame frame){
        if(dialog == null){
            dialog = new NewJFrame(frame);
        }

        return dialog;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        } else {
            super.processWindowEvent(e);
        }
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getYouName() {
        return youName;
    }

    public void setYouName(String youName) {
        this.youName = youName;
    }
}
