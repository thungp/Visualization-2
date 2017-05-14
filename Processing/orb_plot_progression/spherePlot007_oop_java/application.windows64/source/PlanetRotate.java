/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import processing.core.*;

/**
 * Currently doesn't some random rotation
 * @author thungp
 */
public class PlanetRotate extends Planet{
    int rotateDegree = 0;
    PlanetRotate() {
        super();
        
    }
    public PlanetRotate(PApplet p, PVector loc, float radius, int id, String composition) {
        super(p, loc, radius, id, composition);   
    }
    
  public void display() {

    p.pushMatrix();
    p.translate(loc.x, loc.y, loc.z);
    
    
    p.rotateX(rot.x);
    p.rotateY(rot.y);
    p.rotateZ(rot.z);
    

    p.noStroke();
                p.box(1.2f * this.radius);
            if (PApplet.second() % 1 == 0) {  // if I make a 1 a 2 there is a pause in the rotations.
                rot = new PVector(p.random(p.QUARTER_PI), p.random(p.QUARTER_PI), p.random(p.QUARTER_PI)); // this is what needs to change to rotate sphere
            }
        p.pushMatrix();

            s.display();    

        p.popMatrix();
        
        p.pushMatrix();

        p.popMatrix();

    p.popMatrix();
    //p.pushMatrix();
    if (PApplet.second() % 1 == 0) {  // if I make a 1 a 2 there is a pause in the rotations.
        //p.translate(loc.x, loc.y, loc.z); 
        //System.out.println(rotateDegree % 360);
        //p.getMatrix().rotate(PApplet.radians(rotateDegree % 360)); // This didn't seem to work.
        //p.getMatrix().rotateY(PApplet.radians(rotateDegree % 360));
      
        //p.rect(this.loc.x, this.loc.x, this.radius, this.radius);
        //p.box(1.2f * this.radius);
        //rotateDegree = rotateDegree + 1;
        //rot = new PVector(p.random(p.QUARTER_PI), p.random(p.QUARTER_PI), p.random(p.QUARTER_PI)); // this is what needs to change to rotate sphere
    }
    //p.popMatrix();
    
  }
    
}
