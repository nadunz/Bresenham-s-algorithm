    
import java.util.*;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BresenhamLine {

    private List<Point> line;
    private Point[][] grid;
    private static int ROWS;
    private static int COLUMNS;

    // view attributes
    private int squareSize; // number of pixels in a square's edge
    
    private Image image;
    private ImageIcon imageIcon;
    private JLabel jLabel;
    private JFrame jFrame;

    public BresenhamLine(int r,int c) {
        ROWS = r;
        COLUMNS = c;
        // create an array list to store the line
        this.line = new ArrayList<>();
        this.grid = new Point[r][c];
        // initialize the grid mapping the XY scale
        for (int x = 0; x < r; x++){
            for (int y = 0; y < c; y++){
                this.grid[x][y] = new Point(x, y);
            }
        }
    }

    public void initFigure(){

        squareSize = getInt( "How many pixels per square? [1 - 100]?" );
        int imageSizeX = ROWS * squareSize;       
        int imageSizeY = COLUMNS * squareSize;       
        image = new BufferedImage( imageSizeY, imageSizeX, BufferedImage.TYPE_INT_ARGB );
        imageIcon = new ImageIcon( image );
        jLabel = new JLabel( imageIcon );
        jFrame = new JFrame( "BresenhamLine");
        jFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Container container = jFrame.getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( jLabel, BorderLayout.CENTER );
        jFrame.pack();
    }

    private int getInt( String question )
    {
        String intString = JOptionPane.showInputDialog( question );
        return Integer.parseInt( intString );
    }

    private void view() { 
        jFrame.setVisible( true ); 
    }

    public static boolean isValidPoint(int x,int y){
        if((x >= ROWS) || (y >= COLUMNS)) return false;
        else if((x < 0) || (y < 0)) return false;
        return true;
    }

    public void createLine(Point p0, Point p1) {  

        int x1=p0.x;
        int x2=p1.x;
        int y1=p0.y;
        int y2=p1.y;

        // delta of exact value and rounded value of the dependent variable
        int d = 0;
 
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
 
        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
 
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
 
        int x = x1;
        int y = y1;
 
        if (dx >= dy) {
            while (true) {
                System.out.println("("+x+","+y+")");
                this.line.add(this.grid[x][y]);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                System.out.println("("+x+","+y+")");
                this.line.add(this.grid[x][y]);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }  
    }

    public void drawFigure(){

        initFigure();

        Graphics graphics = image.getGraphics();
        
        // paint a red  board
        graphics.setColor( Color.red );
        graphics.fillRect( 0, 0, COLUMNS * squareSize, ROWS * squareSize);
        
        // paint the black squares
        graphics.setColor( Color.black );
       
        for (int i = 0; i < ROWS; i++){
                
            for (int j = 0; j < COLUMNS; j++){

                if (this.line.contains(grid[i][j])){
                    graphics.fillRect( j* squareSize, i* squareSize, squareSize, squareSize );
                }
                
            }
           
        }
    }

    public void err(){
        System.out.println("The point you entered is out of the boundaries.");
        System.out.println("X scale: 0 to "+(ROWS-1));
        System.out.println("Y scale: 0 to "+(COLUMNS-1));
    }

    public static void main(String[] args){
        // scanner to scann all the user inputs
        Scanner in = new Scanner(System.in);
        // print the topic of the application
        System.out.println("Bresenham Algorithm For Line");

        
        System.out.print("\nEnter dimensions of grid: ");
        int rows = in.nextInt();
        int columns = in.nextInt();

        BresenhamLine  bresenham = new BresenhamLine(rows,columns);

        // create two points
        Point p0 = null;
        Point p1 = null;
        while(true){
            System.out.print("Enter the first point p0: ");
            int x0 = in.nextInt();
            int y0 = in.nextInt();
            if(isValidPoint(x0, y0)) {
                p0 = new Point(x0,y0);
                break;
            }
            // print the error
            bresenham.err();
        }

        while(true){
            System.out.print("Enter the second point p1: ");
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            if(isValidPoint(x1, y1)) {
                p1 = new Point(x1,y1);
                break;
            }
            // print the error
            bresenham.err();
        }
        
        // create the line between the two points
        bresenham.createLine(p0, p1);
        // new line
        System.out.println();
        // draw the line
        bresenham.drawFigure();
        // view it
        bresenham.view();
        // close the scanner
        in.close();

    }        

}