package com.rooomy.gui;

import com.rooomy.listener.FileChooseListener;
import com.rooomy.listener.FileDropListener;
import com.rooomy.util.GltfConvertHelper;
import com.rooomy.util.LogHelper;

import javax.swing.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton startBut, fileChooseBut;
    private JTextField dragPathField, choosePathField;
    private JTextArea logTextArea;
    private JCheckBox backupCheckBox;
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

        backupCheckBox = new JCheckBox("创建新文件夹");
        backupCheckBox.setBounds(370, 42, 300,30);
        backupCheckBox.setSelected(true);

        dragPathField = new JTextField();
        dragPathField.setBounds(100, 80, 250, 40);
        dragPathField.setEditable(false);

        choosePathField = new JTextField();
        choosePathField.setBounds(100, 150, 250, 40);
        choosePathField.setEditable(true);

        startBut = new JButton("Convert");
        startBut.setBounds(370, 80, 200, 40);
        fileChooseBut = new JButton("Choose...");
        fileChooseBut.setBounds(370, 150, 200, 40);

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
        this.add(startBut);
        this.add(fileChooseBut);
        this.add(backupCheckBox);

    }

    private void completeLayout() {
        // 实现拖拽功能, 获取JPG文件
        new DropTarget(dragPathField, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropListener(dragPathField));
        fileChooseBut.addActionListener(new FileChooseListener(choosePathField));

        startBut.addActionListener(new ActionListener() {
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
                    startBut.setEnabled(false);
                    repaint();
                    new Thread(() -> {
                        try {
                            boolean backup = backupCheckBox.isSelected();
                            if (dragPath != null && dragPath.isEmpty()) {
                                GltfConvertHelper.process(choosePath, backup);
                            } else {
                                GltfConvertHelper.process(dragPath, backup);
                            }
                            log.info("The operation is done!");
                            JOptionPane.showMessageDialog(MainPage.this, "The operation is done!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e) {
                            System.out.println(e);
                            log.info("The operation is abort!");
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        remove(running_label);
                        startBut.setEnabled(true);
                        repaint();
                    }).start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }


}
