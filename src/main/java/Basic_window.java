import Missions_data.Mission;
/*sffsfsfs*/
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Basic_window extends JFrame {

    private JTextField path_field;
    private JButton browse_button;
    private JButton parse_button;
    private JTextArea result_area;
    private GenericControl genericControl;

    public Basic_window() {

        genericControl = new GenericControl(this);
        setTitle("Анализатор миссий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel main_panel = new JPanel(new BorderLayout(10, 10));
        main_panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel top_panel = new JPanel(new BorderLayout(10, 10));

        JPanel path_panel = new JPanel(new BorderLayout(10, 5));
        path_field = new JTextField("Путь к файлу...");
        path_field.setForeground(Color.GRAY);

        path_field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (path_field.getText().equals("Путь к файлу...")) {
                    path_field.setText("");
                    path_field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (path_field.getText().isEmpty()) {
                    path_field.setText("Путь к файлу...");
                    path_field.setForeground(Color.GRAY);
                }
            }
        });

        browse_button = new JButton("Обзор...");
        browse_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Поддерживаемые файлы (*.xml, *.json, *.txt, *.yaml)",
                        "xml", "json", "txt", "yaml"
                );

                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);

                fileChooser.setAcceptAllFileFilterUsed(true);

                int result = fileChooser.showOpenDialog(Basic_window.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path_field.setText(selectedFile.getAbsolutePath());
                    path_field.setForeground(Color.BLACK);
                    parse_button.setEnabled(true);
                    result_area.setText("");
                }
            }
        });

        path_panel.add(path_field, BorderLayout.CENTER);
        path_panel.add(browse_button, BorderLayout.EAST);

        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        parse_button = new JButton("Распарсить миссию");
        parse_button.setEnabled(false);
        parse_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result_area.setText("");
                String filepath = path_field.getText();
                String view = genericControl.getView(filepath);
                if (view != null) {
                    result_area.setText(view);
                }
            }
        });

        String[] keysArray  = genericControl.getReportTypes();
        JComboBox<String> reportSelector = new JComboBox<>(keysArray);
        reportSelector.setSelectedItem("Brief");
        reportSelector.addActionListener(e -> {
            String selected = (String) reportSelector.getSelectedItem();
            genericControl.setReportBuilder(selected);
        });



        button_panel.add(reportSelector);
        button_panel.add(parse_button);
        top_panel.add(path_panel, BorderLayout.CENTER);
        top_panel.add(button_panel, BorderLayout.SOUTH);

        result_area = new JTextArea();
        result_area.setEditable(false);
        result_area.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        JScrollPane scrollPane = new JScrollPane(result_area);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Результат"));

        main_panel.add(top_panel, BorderLayout.NORTH);
        main_panel.add(scrollPane, BorderLayout.CENTER);

        add(main_panel);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                browse_button.requestFocusInWindow();
            }
        });
    }

    public void show_error_message(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}