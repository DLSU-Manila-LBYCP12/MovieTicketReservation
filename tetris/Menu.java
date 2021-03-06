    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package tetris;

    import acm.graphics.GImage;
    import acm.graphics.GLabel;
import acm.io.IODialog;
import acmx.export.javax.swing.JLabel;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.event.ComponentEvent;
    import javax.swing.JPanel;
    import java.util.Queue;
    import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.Global.println;

    public class Menu extends JPanel {
            private static final int TILE_SIZE = Main.TILE_SIZE >> 1;
            private static final int SHADE_WIDTH = Main.SHADE_WIDTH >> 1;
            private static final int TILE_COUNT = 5;
            private static final int SQUARE_CENTER_X = 130;
            private static final int SQUARE_CENTER_Y = 65;
            private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);
            private static final int SMALL_INSET = 20;
            private static final int LARGE_INSET = 40;
            private static final int CONTROLS_INSET = 300;
            private static final int STATS_INSET = 125;
            private static final int TEXT_STRIDE = 25;
            private static final Font SMALL_FONT = new Font("Calibri", Font.BOLD, 11);
            private static final Font LARGE_FONT = new Font("Calibri", Font.BOLD, 13);
            private static final Color DRAW_COLOR = new Color(128, 192, 128);
            private final Tetris tetris;
            Color blue = Color.decode("#2d2a69");
            Color or = Color.decode("#f97300");
            public final String playerName;
            public final MyQueue<String> playerQueue = new MyQueue<>();

            public Menu(Tetris tetris) {
                    this.tetris = tetris;
                    IODialog dialog = new IODialog();
                    playerName = dialog.readLine("Enter your Name");
                    playerQueue.initializeQueue();  
                    setPreferredSize(new Dimension(200, Main.PANEL_HEIGHT));
                    setBackground(or);
            }           
		
            @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(blue);
                    int offset;
                    JLabel player1 = new JLabel();
                    JLabel player2 = new JLabel();
                    JLabel player3 = new JLabel();                    
                    playerQueue.enqueue(playerName);                    
                    player3.setText(player2.getText());
                    player2.setText(player1.getText());
                    player1.setText(playerName);                    
                    if (playerQueue.getSize() == 3){
                        playerQueue.dequeue();
                    }
                    g.setFont(LARGE_FONT);
                    g.drawString("Controls", SMALL_INSET, offset = CONTROLS_INSET);
                    g.setFont(SMALL_FONT);
                    g.drawString("A - Move Left", LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("D - Move Right", LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("S - Drop", LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("E - Rotate Clockwise", LARGE_INSET, offset += TEXT_STRIDE);		
                    g.drawString("Q - Rotate Counterclockwise", LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("P - Pause", LARGE_INSET, offset += TEXT_STRIDE);
                    g.setFont(LARGE_FONT);
                    g.drawString("Data", SMALL_INSET, offset = STATS_INSET);
                    g.setFont(SMALL_FONT);
                    g.drawString("Difficulty: " + tetris.getLevel(), LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("Score: " + tetris.getScore(), LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("Recent Players: ", LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("" + player1.getText(), LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("" + player2.getText(), LARGE_INSET, offset += TEXT_STRIDE);
                    g.drawString("" + player3.getText(), LARGE_INSET, offset += TEXT_STRIDE);
                    g.setFont(LARGE_FONT);
                    g.drawString("Next Piece:", SMALL_INSET, 70);
                    g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
                    g.setFont(LARGE_FONT);
                    Tiles type = tetris.getNextPieceTile();

                    if(!tetris.isGameOver() && type != null) {
                            int cols = type.getCols();
                            int rows = type.getRows();
                            int dimension = type.getDimension();
                            int startX = (SQUARE_CENTER_X - (cols * TILE_SIZE / 2));
                            int startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));
                            int top = type.getTopInset(0);
                            int left = type.getLeftInset(0);
                            for(int row = 0; row < dimension; row++) {
                                    for(int col = 0; col < dimension; col++) {
                                            if(type.isTile(col, row, 0)) {
                                                    drawTile(type, startX + ((col - left) * TILE_SIZE), startY + ((row - top) * TILE_SIZE), g);
                                            }
                                    }
                            }   
                            for(int row = 0; row < dimension; row++) {
                                    for(int col = 0; col < dimension; col++) {
                                            if(type.isTile(col, row, 0)) {
                                                    Text(type, startX + ((col - left) * TILE_SIZE), startY + ((row - top) * TILE_SIZE), g);
                                            }
                                    }
                            }
                    }
            }
            
            
            private void Text(Tiles type, int x, int y, Graphics g) {
                    int offset = 0;                                       
        }
            private void drawTile(Tiles type, int x, int y, Graphics g) {
                    g.setColor(type.getBaseColor());
                    g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g.setColor(type.getDarkColor());
                    g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
                    g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
                    g.setColor(type.getLightColor());
                    for(int i = 0; i < SHADE_WIDTH; i++) {
                            g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
                            g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
                    }

            }
    }