import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PointDrawingFromFile extends JPanel {
    private List<Point> points; // List to store points
    private List<Line2D> lines;
    private List<Color> lineColors; // List to store line colors
    private List<Float> lineThicknesses; // List to store line thicknesses
    private int sodiem;
    private String FileInput = "";
    
    
    public static String docNoiDungTuTep(String duongDan) {
        StringBuilder noiDung = new StringBuilder();
        try {
            File file = new File(duongDan);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                noiDung.append(scanner.nextLine()).append("\n");
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return noiDung.toString();
    }

    public PointDrawingFromFile() {
        setLayout(null);
 
        lines = new ArrayList<>();
        lineColors = new ArrayList<>();
        lineThicknesses = new ArrayList<>();
        FileInput = docNoiDungTuTep("D:/tai xuong/input.txt");
        //String FileInput = "6 7"+"\n"+"1 2 8"+"\n"+"1 6 2"+"\n"+"2 3 3"+"\n"+"2 5 3"+"\n"+"3 4 2"+"\n"+"4 5 1"+"\n"+"5 6 3";
        //Scanner inp = new Scanner(line);
        setPoint(FileInput);
       addLabel();

       addLine(Color.BLACK, 1.0f, FileInput);
       addKhoangCanh(Color.BLACK, 1.0f, FileInput);
    }
    public void addLineDijkstra(Color color, float thickness, String input) {
        int numberOfPoints = points.size();        
        if (numberOfPoints < 2) {           
            return;
        }
        if (lineColors == null) {
            lineColors = new ArrayList<>(); 
        }
        
       
        String[] pathLines = input.split("\n");
        String line2 = pathLines[1];     
        String[] numbers = line2.split(" ");
        int numberCount = numbers.length;
        System.out.println("số x:"+ numberCount);
        for (int i = 0; i < numberCount-1 ; i++) {
                 
            
            if (numbers.length >= 2) {
                int a = Integer.parseInt(numbers[i]);
                int b = Integer.parseInt(numbers[i+1]);           
                System.out.println("ax: " + a);
                System.out.println("bx: " + b);
                Point point1 = points.get(a-1);
                Point point2 = points.get(b-1);
                int x1 = (int) point1.getX();
                int y1 = (int) point1.getY();
                int x2 = (int) point2.getX();
                int y2 = (int) point2.getY();            
                addLines(x1, y1, x2, y2,color,thickness);
                
            }
        }
        
        
    }
    
    public void resetMap() {
    	
      	 	lines.clear(); 
      	    lineColors.clear(); 
      	    lineThicknesses.clear();
    	String input= FileInput;
        int numberOfPoints = points.size();
        System.out.println("số:"+ numberOfPoints);
        if (numberOfPoints < 2) {           
            return;
        }
        if (lineColors == null) {
            lineColors = new ArrayList<>(); 
        }
        
        
        String[] pathLines = input.split("\n");
        int lineCount = pathLines.length;
        for (int i = 0; i < lineCount-1 ; i++) {
        String line = pathLines[i+1];        
        String[] numbers = line.split(" ");
        if (numbers.length >= 2) {
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);           
            System.out.println("a: " + a);
            System.out.println("b: " + b);
            Point point1 = points.get(a-1);
            Point point2 = points.get(b-1);
            int x1 = (int) point1.getX();
            int y1 = (int) point1.getY();
            int x2 = (int) point2.getX();
            int y2 = (int) point2.getY();            
            addLines(x1, y1, x2, y2,Color.BLUE,1.0f);
            
        }
    }
        
        
    }
    
    public void addKhoangCanh(Color color, float thickness, String input) {
    	 int numberOfPoints = points.size();
         System.out.println("số:"+ numberOfPoints);
         if (numberOfPoints < 2) {           
             return;
         }
         if (lineColors == null) {
             lineColors = new ArrayList<>(); 
         }
                
         String[] pathLines = input.split("\n");
         int lineCount = pathLines.length;
         for (int i = 0; i < lineCount-1 ; i++) {
         String line = pathLines[i+1];        
         String[] numbers = line.split(" ");
         if (numbers.length >= 2) {
             int a = Integer.parseInt(numbers[0]);
             int b = Integer.parseInt(numbers[1]); 
             int c = Integer.parseInt(numbers[2]);    
            
             Point point1 = points.get(a-1);
             Point point2 = points.get(b-1);
             int x1 = (int) point1.getX();
             int y1 = (int) point1.getY();
             int x2 = (int) point2.getX();
             int y2 = (int) point2.getY();   
             JLabel label = new JLabel(""+ c);
             label.setBounds((x1+x2)/2, (y1+y2)/2, label.getPreferredSize().width, label.getPreferredSize().height);
             Font labelFont = new Font("Arial", Font.BOLD, 10);

             label.setFont(labelFont);
             label.setForeground(color);

             add(label);
         }
         }
	}
    public void addLine(Color color, float thickness, String input) {
        int numberOfPoints = points.size();
        System.out.println("số:"+ numberOfPoints);
        if (numberOfPoints < 2) {           
            return;
        }
        if (lineColors == null) {
            lineColors = new ArrayList<>(); 
        }
        
        
        String[] pathLines = input.split("\n");
        int lineCount = pathLines.length;
        for (int i = 0; i < lineCount-1 ; i++) {
        String line = pathLines[i+1];        
        String[] numbers = line.split(" ");
        if (numbers.length >= 2) {
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);           
            System.out.println("a: " + a);
            System.out.println("b: " + b);
            Point point1 = points.get(a-1);
            Point point2 = points.get(b-1);
            int x1 = (int) point1.getX();
            int y1 = (int) point1.getY();
            int x2 = (int) point2.getX();
            int y2 = (int) point2.getY();            
            addLines(x1, y1, x2, y2,color,1.0f);
            
        }
    }
        
        repaint();
    }

    public void addLabel() {
    	int i=1;
    	for (Point point : points) {
    		JLabel label = new JLabel(""+ i);
    		i++;
    		int x = (int) point.getX();
            int y = (int) point.getY()+2;
            label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);

            Font labelFont = new Font("Arial", Font.BOLD, 11);

            label.setFont(labelFont);
            label.setForeground(Color.BLUE);

            add(label);
        }
		
	}

    public void setPoint(String inputData) {
    	
        String[] lines = inputData.split("\n");
        String[] parts = lines[0].split(" ");
        if (parts.length >= 2) {
            sodiem = Integer.parseInt(parts[0]);
        }
        
        
        System.out.print("Số điểm:"+ sodiem);
        int x0 = 5; // Starting x-coordinate
        int y0 = 50; // Starting y-coordinate
        
        points = new ArrayList<>();
        points.add(new Point(x0, y0));   
        for (int i = 2; i <=sodiem; i++) {
        	            
            if(i<=sodiem/2) {
            	 y0 = 5;
                 x0 = x0 +60;
            }
            if (i == sodiem/2+1) {
            	 x0=30*sodiem-10;
            	 y0=50;
            	 System.out.print("Số: "+ i);           	 
            } 
            if(i>sodiem/2+1){
                y0 = 100;
                x0 =x0-60 ;
            }
            points.add(new Point(x0, y0));   
        }
       
    }
    

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the points (circles) at the coordinates stored in the points list
        for (Point point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            int radius = 5; // Adjust the radius as needed

            g2d.setColor(Color.BLUE);
            g2d.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
        }
        

        
        for (int i = 0; i < lines.size(); i++) {
            g2d.setColor(lineColors.get(i));
            g2d.setStroke(new BasicStroke(lineThicknesses.get(i)));
            g2d.draw(lines.get(i));
        }
    }

    public void addLines(int x1, int y1, int x2, int y2, Color color, float thickness) {
        lines.add(new Line2D.Double(x1, y1, x2, y2));
        lineColors.add(color);
        lineThicknesses.add(thickness);
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Draw Points and Lines with JLabels");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(100, 100, 800, 600);
            PointDrawingFromFile map = new PointDrawingFromFile();
            
            frame.add(map);
            frame.setVisible(true);
        });
    }
}
