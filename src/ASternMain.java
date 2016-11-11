import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * Created by oem on 10/22/16.
 */
public class ASternMain {

    public static final String PATH = "Weg.txt";
    public static final String STYLE = "UTF-8";
    public static int nr = 1;
    public static ArrayList<Wall> mauern = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("A-Stern");

        JLabel x = new JLabel("xWert");
        x.setBounds(150, 20, 50, 20);

        JLabel y = new JLabel("yWert");
        y.setBounds(300, 20, 50, 20);

        JButton berechne = new JButton("Berechne");
        berechne.setBounds(330, 240, 150, 20);

        JLabel feldgr = new JLabel("Feldgröße:");
        feldgr.setBounds(20, 50, 130, 20);

        JTextField feldxfield = new JTextField();
        feldxfield.setBounds(165, 50, 20, 20);

        JTextField feldyfield = new JTextField();
        feldyfield.setBounds(315, 50, 20, 20);

        JLabel start = new JLabel("Start:");
        start.setBounds(20, 75, 130, 20);

        JTextField startx = new JTextField();
        startx.setBounds(165, 75, 20, 20);

        JTextField starty = new JTextField();
        starty.setBounds(315, 75, 20, 20);

        JLabel ziel = new JLabel("Ziel:");
        ziel.setBounds(20, 100, 130, 20);

        JTextField zielx = new JTextField();
        zielx.setBounds(165, 100, 20, 20);

        JTextField ziely = new JTextField();
        ziely.setBounds(315, 100, 20, 20);

        JButton mauer = new JButton("Mauer hinzufügen...");
        mauer.setBounds(20, 125, 200, 20);

        Container container = frame.getContentPane();
        container.setLayout(null);
        container.add(berechne);
        container.add(feldgr);
        container.add(feldxfield);
        container.add(feldyfield);
        container.add(x);
        container.add(y);
        container.add(start);
        container.add(ziel);
        container.add(zielx);
        container.add(ziely);
        container.add(startx);
        container.add(starty);
        container.add(mauer);


        berechne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Writer writer = null;
                    try {
                        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Weg" + nr + ".txt"), "utf-8"));
                    } catch (Exception e2) {

                    }
                    if (writer != null) {
                        AStern.test(Integer.parseInt(feldxfield.getText()), Integer.parseInt(feldyfield.getText()), Integer.parseInt(startx.getText()),
                                Integer.parseInt(starty.getText()), Integer.parseInt(zielx.getText()), Integer.parseInt(ziely.getText()), toArray(mauern), writer);
                        nr++;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "Allse Felder müssen gefüllt sein!", "Warnung", JOptionPane.WARNING_MESSAGE);
                }
                mauern.clear();
            }
        });

        mauer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame neu = new JFrame("Neue Mauer");
                neu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                neu.setBounds(125, 100, 250, 100);

                JLabel x = new JLabel("x =");
                x.setBounds(40, 20, 50, 20);

                JLabel y = new JLabel("y =");
                y.setBounds(140, 20, 50, 20);

                JTextField xfield = new JTextField();
                xfield.setBounds(95, 20, 40, 20);

                JTextField yfield = new JTextField();
                yfield.setBounds(195, 20, 40, 20);

                JButton ok = new JButton("OK");
                ok.setBounds(180, 70, 60, 20);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int x = Integer.parseInt(xfield.getText());
                            int y = Integer.parseInt(yfield.getText());
                            mauern.add(new Wall(x, y));
                            xfield.setText(null);
                            yfield.setText(null);
                            //   mauern+=;
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(frame, "Alle Felder müssen gefüllt sein!", "Warnung", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });

                Container container1 = neu.getContentPane();
                container1.setLayout(null);
                container1.add(x);
                container1.add(ok);
                container1.add(y);
                container1.add(xfield);
                container1.add(yfield);

                neu.setVisible(true);
            }
        });


        //AStern.test(1, 5, 5, 0, 0, 3, 2, new int[][]{{0, 4}, {2, 2}, {3, 1}, {3, 3}});
//        AStern.test(2, 5, 5, 0, 0, 4, 4, new int[][]{{0, 4}, {2, 2}, {3, 1}, {3, 3}});
//        AStern.test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4, 1}, {4, 3}, {5, 3}, {2, 3}});
//        AStern.test(4, 5, 5, 0, 0, 4, 4, new int[][]{{3, 4}, {3, 3}, {4, 3}});

        frame.setVisible(true);
    }

        public static int[][] toArray(java.util.List<Wall> array) {
            int[][] ret = new int[array.size()][2];
            for (int i = 0; i < array.size(); i++) {
                Wall wall = array.get(i);
                ret[i][0] = wall.getX();
                ret[i][1] = wall.getY();
            }
            return ret;
        }
}