
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import processing.core.PFont;
import processing.core.PVector;
import processing.core.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thungp
 */
public class Menu {

	  Layout menuPosition;
	  ButtonType buttonType;
	  String[] labels;
	  int[] states;
	  Dimension dimension;
	  Component[] buttons;
          PApplet p;

	  Menu() {
	  }

	  Menu(PApplet p, Layout menuPosition, Dimension dimension, String[] labels, int[] states) {
	    this.p = p;
            this.menuPosition = menuPosition;
	    this.dimension = dimension;
	    this.labels = labels;
	    this.states = states;
	    buttons = new Component[labels.length];

	    generate();
	  }

	  Menu(PApplet p, Layout menuPosition, Dimension dimension, String[] labels, int[] states, ButtonType buttonType) {
	    this.p = p;
            this.menuPosition = menuPosition;
	    this.dimension = dimension;
	    this.labels = labels;
	    this.states = states;
	    this.buttonType = buttonType;
	    buttons = new Component[labels.length];

	    generate();
	  }


	  public void generate() {

	    float btnW, btnH;
	    if (menuPosition == Layout.TOP || menuPosition == Layout.BOTTOM) {
	      btnW = dimension.w/buttons.length;
	      btnH = dimension.h;
	    } else {

	      // left or right position
	      btnW = dimension.w;
	      btnH = dimension.h/buttons.length;
	    }

	    for (int i=0; i<buttons.length; i++) {
	      PVector pos;
	      Dimension dim;
	      switch (menuPosition) {
	      case TOP:
	        pos = new PVector(btnW * i, 0);
	        dim = new Dimension(btnW, btnH);
	        break; 
	      case BOTTOM:
	        pos = new PVector(btnW * i, p.height-btnH);
	        dim = new Dimension(btnW, btnH); 
	        break;
	      case LEFT:
	        pos = new PVector(0, btnH * i);
	        dim = new Dimension(btnW, btnH); 
	        break;
	      case RIGHT:
	        pos = new PVector(p.width-btnW, btnH * i);
	        dim = new Dimension(btnW, btnH); 
	        break;
	      default: // top
	        pos = new PVector(btnW * i, 0);
	        dim = new Dimension(btnW, btnH);
	      }

	      switch (buttonType) {
	      case RECT:
	        buttons[i] = new RectButton(p, pos, dim, labels[i], states);
	        break;
	      case ROUNDED_RECT:
	        buttons[i] = new RoundedRectButton(p, pos, dim, labels[i], states, 6);
	        break;
	      default:
	        buttons[i] = new RectButton(p, pos, dim, labels[i], states);
	      }
	    }
	  }


	  public void display() {
	    for (int i=0; i<buttons.length; i++) {
	      buttons[i].display();
	    }

	    createMenuEvents();
	  }

	  public void createMenuEvents() {
	    for (int i=0; i<buttons.length; i++) {
	      // pressed
	      if (buttons[i].isHit() && p.mousePressed) {
	        select(i);
	        buttons[i].labelCol = states[3];
	        // mouse over
	      } else if (buttons[i].isHit() && !buttons[i].isSelected) {
	        buttons[i].labelCol = states[2];
	         buttons[i].labelTextCol = 0xff766676;
	        // selected
	      } else if (buttons[i].isSelected) {
	        buttons[i].labelCol = states[1];
	        buttons[i].labelTextCol = 0xff766676;
	        // default
	      } else {
	        buttons[i].labelCol = states[0];
	        buttons[i].labelTextCol = 0xffffffff;
	      }
	    }
	  }
	  public void select(int isSelectedID) {
	    for (int i=0; i<buttons.length; i++) {
	      if (i==isSelectedID) {
	        buttons[i].isSelected = true;
	      } else {
	        buttons[i].isSelected = false;
	      }
	    }
	  }

	  public String getSelected() {
	    String btn;
	    for (int i=0; i<buttons.length; i++) {
	      if (buttons[i].isSelected) {
	        return buttons[i].label;
	      }
	    }
	    return "";
	  }
	}
	class RectButton extends Component {
	  PFont font;

	  RectButton(){
	  }
	  
	  RectButton(PApplet p, PVector position, Dimension dimension, 
	  String label, int[] states) {
	    super(p, position, dimension, label, states);
	    font = p.loadFont("ArialMT-22.vlw");
	    p.textFont(font, 15);
	  }

	  public boolean isHit() {
	    if (p.mouseX >= position.x && p.mouseX <= position.x + dimension.w &&
	      p.mouseY >= position.y && p.mouseY <= position.y + dimension.h) {
	      return true;
	    }
	    return false;
	  }

	  public void display() {
	    if (hasBorder) {
	      p.stroke(100);
	    } else {
	      p.noStroke();
	    }
	    p.fill(labelCol);
	    p.rect(position.x, position.y, dimension.w, dimension.h);

	    p.fill(labelTextCol);
	    float tw = p.textWidth(label);
	    p.textAlign(LEFT, CENTER);
	    p.text(label, position.x +(dimension.w-tw)/2.0f, position.y + dimension.h/2);
	  }
	}
	class RoundedRectButton extends RectButton {
	  float cornerRadius = 6;
	  
	  RoundedRectButton(){
	  }
	  
	  RoundedRectButton(PApplet p, PVector position, Dimension dimension, 
	  String label, int[] states, float cornerRadius) {
	    super(p, position, dimension, label, states);
	    this.cornerRadius = cornerRadius;
	  }
	  
	  public void display() {
	    if (hasBorder) {
	      p.stroke(100);
	    } else {
	      p.noStroke();
	    }
	    p.fill(labelCol);
	    p.rect(position.x, position.y, dimension.w, dimension.h, cornerRadius);

	    p.fill(labelTextCol);
	    float tw = p.textWidth(label);
	    p.textAlign(LEFT, CENTER);
	    p.text(label, position.x +(dimension.w-tw)/2.0f, position.y + dimension.h/2);
	  }
	}
