/*
 Sphere Plot - 3D OOP
 planets JSON data
 interactive Feedback
 
 Change Log:
 * Made Sphere randomly populate in the Z direction
 * Added rotation currently in the X and Z direction based on frameCount (commented out)
 * changed the isHit ellipse a sphere, but the detection algorithm doesn't work in 3D (yet)
 * Added Z boundary.
 * Added line to link first orbo with all other orbs.
 * Added text to top of view to not move with rotating screen.
 
 */

JSONArray planetsData;
int adjZ = 0;
int adjZFactor = 100;

Planet[] orbs;
PFont font;
float plotRadii, sclFactor;
float attraction = 1.0;

void setup() {
  size(1000, 1000, P3D);
  noStroke();
  ortho();

  font = loadFont("ArialMT-148.vlw");
  // load JSON data
  planetsData =loadJSONArray("planets.json");
  // size arrays
  int dataSize = planetsData.size();
  attraction = 1.0 + 1.0/(dataSize*10);

  orbs = new Planet[dataSize];
  for (int i=0; i<dataSize; i++) {
    plotRadii += PI*planetsData.getJSONObject(i).getFloat("mass")*planetsData.getJSONObject(i).getFloat("mass");
  }
  float canvasArea = PI*(width/2.6)*(width/2.6);
  sclFactor = sqrt(canvasArea/plotRadii);
  println(sclFactor);


  for (int i=0; i<dataSize; i++) {
    JSONObject planet = planetsData.getJSONObject(i); 
    orbs[i] = new Planet(this, new PVector(random(-2, 2), random(-2, 2), random(-2, 2)), planet.getFloat("mass")*sclFactor, i, planet.getString("composition"));
  }
}

void keyPressed() {
  int keyIndex = -1;
  if (key >= 'A' && key <= 'Z') {
    keyIndex = key - 'A';
  } else if (key >= 'a' && key <= 'z') {
    keyIndex = key - 'a';
  }
  if (keyIndex == -1) {
    // If it's not a letter key, clear the screen
    adjZ = adjZ + adjZFactor;
  } else { 
    // It's a letter key, fill a rectangle
    adjZ= adjZ - adjZFactor;
    //fill(millis() % 255);
    //float x = map(keyIndex, 0, 25, 0, width - rectWidth);
    //rect(x, 0, rectWidth, height);
  }
}
void draw() {
  background(25);
  ambientLight(85, 85, 85);
  emissive(30, 0, 0);
  lightSpecular(255, 255, 255);
  pointLight(255, 255, 255, -100, -100, 800);
  pointLight(150, 150, 150, -100, 100, 800);
  specular(255, 255, 255);
  shininess(95);
  // set modelview matrix
  float eye = 20 + mouseY;
  camera(width/2.0 + adjZ, height/2.0, ((height/2.0)) / tan(PI*30.0 / 180.0) ,   width/2.0, height/2.0, 0,   0, 1, 0);
  
  fill(85);
  textFont(font, 23);
  String ss = "FrameCount: " + frameCount + "  Planet ID:  " + orbs[0].id + "       Composition:  " + orbs[0].composition +"       Mass: " + orbs[0].radius;
  float ww = textWidth(ss);
  text(ss, (ww)/4, 30, height/2.0);
  
  translate(width/2, height/2 );
  // rotate around the center of the sketch
  rotateZ(radians(frameCount/4));
  rotateX(radians(frameCount/4));
  
  // orb-orb collision
  for (int i=0; i<orbs.length; i++) {
    for (int j=i; j<orbs.length; j++) {
      if (i!=j) {
        //float r2 = (orbs[i].radius + orbs[j].radius)*1.2;
        float r2 = orbs[i].radius + orbs[j].radius;
        //float d = dist(orbs[i].loc.x, orbs[i].loc.y, orbs[j].loc.x, orbs[j].loc.y);
        float d = dist(orbs[i].loc.x, orbs[i].loc.y, orbs[i].loc.z, orbs[j].loc.x, orbs[j].loc.y, orbs[j].loc.z);
        if (d < r2) {
          if (d==0) { // avoid dist of 0
            orbs[i].loc.add(new PVector(random(.1, .1), random(.1, .1), random(.1, .1)));
          }
          PVector axis = PVector.sub(orbs[i].loc, orbs[j].loc);
          axis.normalize();
          PVector temp = new PVector();
          temp.set(orbs[i].loc);
          orbs[i].loc.x = orbs[j].loc.x + axis.x*r2;
          orbs[i].loc.y = orbs[j].loc.y + axis.y*r2;
          orbs[i].loc.z = orbs[j].loc.z + axis.z*r2;
          orbs[j].loc.x = temp.x - axis.x*r2;
          orbs[j].loc.y = temp.y - axis.y*r2;
          orbs[j].loc.z = temp.z - axis.z*r2;
        }
      }
    }
    orbs[i].loc.x *= attraction;
    orbs[i].loc.y *= attraction;
    orbs[i].loc.z *= attraction;
  }
  
  stroke(126);
  for(int i=1; i<orbs.length;i++) {    
    line(orbs[0].loc.x, orbs[0].loc.y, orbs[0].loc.z, orbs[i].loc.x, orbs[i].loc.y, orbs[i].loc.z);
  }
  for (int i=0; i<orbs.length; i++) {
    pushMatrix();
    translate(orbs[i].loc.x, orbs[i].loc.y, orbs[i].loc.z);
    rotateX(orbs[i].rot.x);
    rotateY(orbs[i].rot.y);
    rotateZ(orbs[i].rot.z);
    noStroke();
    orbs[i].display();
    popMatrix();

    // boundary collison
    if (orbs[i].loc.x > width/2-orbs[i].radius) {
      orbs[i].loc.x = width/2-orbs[i].radius;
    } else if (orbs[i].loc.x < -width/2+orbs[i].radius) {
      orbs[i].loc.x = -width/2+orbs[i].radius;
    } 

    if (orbs[i].loc.y > height/2-orbs[i].radius) {
      orbs[i].loc.y = height/2-orbs[i].radius;
    } else if (orbs[i].loc.y < -height/2+orbs[i].radius) {
      orbs[i].loc.y = -height/2+orbs[i].radius;
    }
    
    if (orbs[i].loc.z > height/2-orbs[i].radius) {
      orbs[i].loc.z = height/2-orbs[i].radius;
    } else if (orbs[i].loc.z < -height/2+orbs[i].radius) {
      orbs[i].loc.z = -height/2+orbs[i].radius;
    }

    // mouse detection
    if (orbs[i].isHit()) {
      pushMatrix();
      stroke(255, 0, 0);
      fill(255, 0, 0, 100);
      translate(0, 0, 100);
      //ellipse(orbs[i].loc.x, orbs[i].loc.y, orbs[i].radius*2, orbs[i].radius*2);
      sphere(orbs[i].radius);
      popMatrix();

      fill(85);
      textFont(font, 23);
      String s = "  Planet ID:  " + orbs[i].id + "       Composition:  " + orbs[i].composition +"       Mass: " + orbs[i].radius;
      float w = textWidth(s);
      text(s, (-w)/2, height/2-20, 100);
    }
  }
}