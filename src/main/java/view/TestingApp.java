package view;

import Controller.ViewController;

import javax.swing.*;


//TODO: NOTES: pseudolegal move generator is working fine. legal move generator has issues related to king movement
//TODO: converting from legal moves to tiles (controller) is working fine as well
//TODO: possible ideas: something up with checking for check

//have to make the simple move, then look at opposing pseudolegal moves, then check for check
public class TestingApp {

    static ViewController vc;
    static GUI gui;

    public static void main(String[] args){
        gui = new GUI();
        vc = new ViewController(gui);

        JFrame f = new JFrame("Java Chess");
        f.add(gui.getGui());
        // Ensures JVM closes after frame(s) closed and
        // all non-daemon threads are finished
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // See https://stackoverflow.com/a/7143398/418556 for demo.
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
        vc.initController();


    }

}
