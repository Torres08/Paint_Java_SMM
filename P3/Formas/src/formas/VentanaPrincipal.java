/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author torres
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Line2D
        Point2D p1=new Point2D.Float(70,70);
        Point2D p2=new Point2D.Float(200,200);
        Line2D linea = new Line2D.Float(p1,p2);
        g2d.draw(linea);
        
        //Rectangle2D
        Point2D p3=new Point2D.Float(70,250); 
        double width = 130;
        double height = 130;
        
        Rectangle2D rectangle = new Rectangle2D.Double(p3.getX(), p3.getY(), width, height);
        
        g2d.draw(rectangle);
        
        
        //RoundRectangle2D
        Point2D p4=new Point2D.Float(70,430); 
        //double width = 130; // se rehuesa el width y height
        //double height = 130;
        double arcWidth = 20; 
        double arcHeight = 20; 
        
        RoundRectangle2D roundRectangle = new RoundRectangle2D.Double(p4.getX(), p4.getY(), width, height, arcWidth, arcHeight);
        g2d.draw(roundRectangle);
        
        
        // Ellipse2D
        Point2D p5=new Point2D.Float(70,610); 
        //double width = 130; // se rehuesa el width y height
        //double height = 130;
        Ellipse2D ellipse = new Ellipse2D.Double(p5.getX(), p5.getY(), width+50, height);
        g2d.draw(ellipse);
        
        
        // Arc2D
        Point2D p6=new Point2D.Float(70,790); 
        //double width = 130; // se rehuesa el width y height
        //double height = 130;
        double startAngle = 45;
        double arcAngle = 180;
        Arc2D arc = new Arc2D.Double(p6.getX(), p6.getY(), width, height, startAngle, arcAngle, Arc2D.OPEN);
        g2d.draw(arc);
        
        // quadcurve use 3 points, 
        // control point represents the control point that influence the curvature
        Point2D startPoint = new Point2D.Float(250, 70);
        Point2D controlPoint = new Point2D.Float(350, 80);
        Point2D endPoint = new Point2D.Float(450, 200);
        QuadCurve2D quadCurve = new QuadCurve2D.Double(startPoint.getX(), startPoint.getY(),
                                                   controlPoint.getX(), controlPoint.getY(),
                                                   endPoint.getX(), endPoint.getY());
        g2d.draw(quadCurve);
        
        // CubicCurve2D, igual que el anterior pero con 2 control points
        // 70,250
        Point2D startPoint_2 = new Point2D.Float(250, 250);
        Point2D controlPoint3 = new Point2D.Float(450, 300);
        Point2D controlPoint4 = new Point2D.Float(300, 350);
        Point2D endPoint_2 = new Point2D.Float(450, 250+130);

        CubicCurve2D cubicCurve = new CubicCurve2D.Double(startPoint_2.getX(), startPoint_2.getY(),
                                                           controlPoint3.getX(), controlPoint3.getY(),
                                                           controlPoint4.getX(), controlPoint4.getY(),
                                                           endPoint_2.getX(), endPoint_2.getY());
        
        g2d.draw(cubicCurve);
        
        // general path - smiley face
        
        int startX = 270;
        int startY = 430;
        //int width = 130; // lo reutilizamos
       // int height = 130;
        
        // 1. draw a face (circle) with ellipse2D
        Ellipse2D face = new Ellipse2D.Double(startX, startY, width, height);        
        
        //2. eyes wit circles
        int eyeRadius = 5;
        Ellipse2D leftEye = new Ellipse2D.Double(startX + width / 4 - eyeRadius / 2, startY + height / 4 - eyeRadius / 2, eyeRadius, eyeRadius);
        Ellipse2D rightEye = new Ellipse2D.Double(startX + width * 3 / 4 - eyeRadius / 2, startY + height / 4 - eyeRadius / 2, eyeRadius, eyeRadius);
        
        //3. mouth
        int mouthWidth = 50;
        int mouthHeight = 30;
        int mouthStartAngle = 180;
        int mouthArcAngle = 180;
        Arc2D mouth = new Arc2D.Double(startX + width / 2 - mouthWidth / 2, startY + height / 2, mouthWidth, mouthHeight, mouthStartAngle, mouthArcAngle, Arc2D.OPEN);
        
        //4. combine using generalpath
        GeneralPath smileyFace = new GeneralPath();
        smileyFace.append(face, false);
        smileyFace.append(leftEye, false);
        smileyFace.append(rightEye, false);
        smileyFace.append(mouth, false);

        // Draw the smiley face
        g2d.setColor(Color.YELLOW);
        g2d.fill(smileyFace);
        g2d.setColor(Color.BLACK);
        g2d.draw(smileyFace);
        
        
        //  general path
        GeneralPath path = new GeneralPath();

        // Define the shape using moveTo, lineTo, quadTo, and curveTo methods
        path.moveTo(270, 430); // Move to starting point
        path.lineTo(100, 100); // Line segment to point (100, 100)
        path.quadTo(150, 50, 200, 100); // Quadratic curve to point (200, 100) with control point (150, 50)
        path.curveTo(250, 150, 300, 50, 350, 100); // Cubic curve to point (350, 100) with control points (250, 150) and (300, 50)
        path.closePath(); // Close the path

        // Draw the shape
        //g2d.draw(path);
        
        // Area
        // se suele crear una clase
        
        
         // Define the starting point and dimensions
        int startX_2 = 470;
        int startY_2 = 70;
        //int width = 130;
        //int height = 130;

        // Create shapes for the face, eyes, and mouth
        Ellipse2D face_2 = new Ellipse2D.Double(startX_2, startY_2, width, height);
        Ellipse2D leftEye_2 = new Ellipse2D.Double(startX_2 + width / 4 - 15, startY_2 + height / 4 - 15, 30, 30);
        Ellipse2D rightEye_2 = new Ellipse2D.Double(startX_2 + width * 3 / 4 - 15, startY_2 + height / 4 - 15, 30, 30);
        Arc2D mouth_2 = new Arc2D.Double(startX_2 + width / 4, startY + height / 2, width / 2, height / 3, 180, 180, Arc2D.OPEN);

        // Combine shapes into a single Area
        Area smileyFace_2 = new Area(face_2);
        smileyFace_2.add(new Area(leftEye_2));
        smileyFace_2.add(new Area(rightEye_2));
        smileyFace_2.add(new Area(mouth_2)); // Subtract the mouth area from the face

        // Draw the smiley face
        //g2d.setColor(Color.YELLOW);
        //g2d.fill(smileyFace_2);
        g2d.setColor(Color.BLACK);
        g2d.draw(smileyFace);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
