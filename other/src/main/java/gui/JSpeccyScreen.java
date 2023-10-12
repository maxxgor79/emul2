// 
// Decompiled by Procyon v0.5.36
// 

package gui;

import javax.swing.BorderFactory;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImageOp;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class JSpeccyScreen extends JComponent
{
    private BufferedImage tvImage;
    private AffineTransform escala;
    private AffineTransformOp escalaOp;
    private RenderingHints renderHints;
    private boolean doubleSize;
    
    public JSpeccyScreen() {
        this.initComponents();
        this.escala = AffineTransform.getScaleInstance(2.0, 2.0);
        (this.renderHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)).put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        this.renderHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        this.renderHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        this.escalaOp = new AffineTransformOp(this.escala, this.renderHints);
        this.setMaximumSize(new Dimension(640, 512));
        this.setMinimumSize(new Dimension(320, 256));
        this.setPreferredSize(new Dimension(320, 256));
    }
    
    public void setTvImage(final BufferedImage bImage) {
        this.tvImage = bImage;
    }
    
    public void toggleDoubleSize() {
        this.doubleSize = !this.doubleSize;
        if (this.doubleSize) {
            this.setPreferredSize(new Dimension(640, 512));
        }
        else {
            this.setPreferredSize(new Dimension(320, 256));
        }
    }
    
    public boolean isDoubleSized() {
        return this.doubleSize;
    }
    
    public void paintComponent(final Graphics gc) {
        final Graphics2D gc2 = (Graphics2D)gc;
        if (this.doubleSize) {
            gc2.drawImage(this.tvImage, this.escalaOp, 0, 0);
        }
        else {
            gc2.drawImage(this.tvImage, 0, 0, null);
        }
    }
    
    private void initComponents() {
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setDoubleBuffered(false);
    }
}
