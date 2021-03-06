package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

public class GUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private Tile[][] chessBoardSquares = new Tile[8][8];
    public static Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Hamza's Chess Game!");
    private final JLabel message2 = new JLabel("");
    private final JLabel message3 = new JLabel("");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;
    private JToolBar tools;


    public GUI() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("Play Against Computer") {

            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(newGameAction);
        tools.add(new JButton("Play Two Player"));
        tools.addSeparator();
        tools.add(message);
        tools.addSeparator();
        tools.add(message2);
        tools.addSeparator();
        tools.add(message3);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = new Color(204,119,34);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                Tile b = new Tile(ii,jj);
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setForeground(Color.WHITE);
                    b.setContentAreaFilled(false);
                    b.setOpaque(true);
                } else {
                    b.setForeground(Color.BLACK);
                    b.setContentAreaFilled(true);
                    b.setOpaque(false);
                }
                chessBoardSquares[ii][jj] = b;

            }
        }

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[ii][jj]);
                }
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    //todo: create image thing
    private final void createImages() {

        try {
            chessPieceImages[0][0] = ImageIO.read(getClass().getResource("/bking.png"));
            chessPieceImages[0][1] = ImageIO.read(getClass().getResource("/bqueen.png"));
            chessPieceImages[0][2] = ImageIO.read(getClass().getResource("/brook.png"));
            chessPieceImages[0][3] = ImageIO.read(getClass().getResource("/bknight.png"));
            chessPieceImages[0][4] = ImageIO.read(getClass().getResource("/bbishop.png"));
            chessPieceImages[0][5] = ImageIO.read(getClass().getResource("/bpawn.png"));

            chessPieceImages[1][0] = ImageIO.read(getClass().getResource("/wking.png"));
            chessPieceImages[1][1] = ImageIO.read(getClass().getResource("/wqueen.png"));
            chessPieceImages[1][2] = ImageIO.read(getClass().getResource("/wrook.png"));
            chessPieceImages[1][3] = ImageIO.read(getClass().getResource("/wknight.png"));
            chessPieceImages[1][4] = ImageIO.read(getClass().getResource("/wbishop.png"));
            chessPieceImages[1][5] = ImageIO.read(getClass().getResource("/wpawn.png"));


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearBoard(){
        for(int i=0; i<chessBoardSquares.length; i++){
            for(int j=0; j<chessBoardSquares[i].length; j++){
                chessBoardSquares[i][j].clear();
            }
        }
    }

    public void setMessage(String msg){
        message.setText(msg);
    }

    public void setMessage2(String msg){
        message2.setText(msg);
    }

    public void setMessage3(String msg) { message3.setText(msg); }

    public void getPromotionType(){
        JOptionPane pane = new JOptionPane();
    }

    public Tile[][] getChessBoardSquares() {
        return chessBoardSquares;
    }

    //todo
    public void gameOver(int winner){
        if(winner == 0){
            JOptionPane.showMessageDialog(null, "Game over. Black Wins", "InfoBox: " + "Check Mate", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "Game over. White Wins", "InfoBox: " + "Check Mate", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Initializes the icons of the initial chess board piece places
     */
    private final void setupNewGame() {
    }


    public JToolBar getTools() {
        return tools;
    }
}
