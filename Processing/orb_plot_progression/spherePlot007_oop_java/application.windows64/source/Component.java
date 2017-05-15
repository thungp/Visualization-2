
import processing.core.PApplet;
import processing.core.PVector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thungp
 */
public abstract class Component {
          PApplet p;
	  PVector position;
	  Dimension dimension;
	  String label;
	  int labelCol, labelTextCol;
	  int offState, onState, overState, pressState;
	  int[] states = {
	    offState, onState, overState, pressState
	  };
	  boolean hasBorder = false;
	  boolean isSelected = false;
	  int mouseClickCount=0;

	  Component() {
	  }

	  Component(PApplet p, PVector position, Dimension dimension, 
	  String label, int[] states) {
            this.p = p;
	    this.position = position;
	    this.dimension = dimension;
	    this.label = label;
	    labelCol = states[0];
	    labelTextCol = 0xffffffff;
	    offState = states[0];
	    onState = states[1];
	    overState = states[2];
	    pressState = states[3];
	    this.states = states;
	  }
	  
	  //concrete method
	  public void setHasBorder(boolean hasBorder){
	    this.hasBorder = hasBorder;
	  }

	  // implement in subclasses
	  public abstract boolean isHit();
	  public abstract void display();
}