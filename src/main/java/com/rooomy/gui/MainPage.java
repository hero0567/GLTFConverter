package com.rooomy.gui;

import com.rooomy.listener.FileChooseListener;
import com.rooomy.listener.FileDropListener;
import com.rooomy.util.GltfConvertHelper;
import com.rooomy.util.GltfHelper;
import com.rooomy.util.LogHelper;
import com.sun.deploy.util.StringUtils;

import javax.swing.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton start_but, boli_but;
    private JTextField dragPathField, choosePathField;
    private JTextArea logTextArea;
    private LogHelper log = LogHelper.getLog();

    public MainPage() {
        this.setLayout(null);
        buildLayout();
        completeLayout();
    }

    private void buildLayout() {
        JLabel tips = new JLabel("Please fill in the options to run the tool.");
        tips.setBounds(170, 10, 400, 40);
        JLabel dragPath = new JLabel("File Path :");
        dragPath.setBounds(30, 80, 100, 40);


        dragPathField = new JTextField();
        dragPathField.setBounds(100, 80, 250, 40);
        dragPathField.setEditable(false);

        choosePathField = new JTextField();
        choosePathField.setBounds(100, 150, 250, 40);
        choosePathField.setEditable(true);

        start_but = new JButton("Convert");
        start_but.setBounds(370, 80, 200, 40);
        boli_but = new JButton("Choose...");
        boli_but.setBounds(370, 150, 200, 40);

        logTextArea = new JTextArea();
        log.setLogTextArea(logTextArea);
        JScrollPane jsp = new JScrollPane(logTextArea);
        jsp.setBounds(50, 250, 520, 340);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(jsp);
        this.add(tips);
        this.add(dragPath);
        this.add(dragPathField);
        this.add(choosePathField);
        this.add(start_but);
        this.add(boli_but);

    }

    private void completeLayout() {
        // 实现拖拽功能, 获取JPG文件
        new DropTarget(dragPathField, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropListener(dragPathField));
        boli_but.addActionListener(new FileChooseListener(choosePathField));

        start_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String dragPath = dragPathField.getText().trim();
                String choosePath = choosePathField.getText().trim();
                if (dragPath != null && dragPath.isEmpty() && choosePath != null && choosePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the conditions!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String path = null;


                try {
                    JLabel running_label = new JLabel("Running ...    Please wait ...");
                    running_label.setBounds(130, 150, 300, 40);
                    add(running_label);
                    start_but.setEnabled(false);
                    repaint();
                    new Thread(() -> {
                        try {
                            if (dragPath != null && dragPath.isEmpty()) {
                                GltfConvertHelper.process(choosePath);
                            } else {
                                GltfConvertHelper.process(dragPath);
                            }
                            log.info("The operation is done!");
                        } catch (Exception e) {
                            log.info("The operation is abort!");
                        }
                        remove(running_label);
                        start_but.setEnabled(true);
                        repaint();
                    }).start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }


}
