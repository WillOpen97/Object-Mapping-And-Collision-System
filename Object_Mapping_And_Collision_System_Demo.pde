
import java.util.*;

// keys for accesing parameters.
final int x = 0;  final int y = 1;  final int p = 2; // p stands for pixel.

final int min = 0;  final int max = 1; // minimum and maximum values for a parameters
//*************************************************************************************
final color black = color(0); final color white = color(255,255,255); final color red = color(255,0,0); 

final color green = color(0,200,0); final color blue = color(0,0,200); final color yellow = color(200,200,0);

enum Edge {TOP, BOTTOM, LEFT, RIGHT}

enum Collision_Mode {STOP, BOUNCE, IGNORE}

enum Option {STOP, BOUNCE, IGNORE} // For checkboxes

enum Category {EDGE, OBJECT} // Collision modes

enum Function {START_STOP, PAUSE_RESUME, ABOUT, NEXT_PREV} // For buttons

int canvas_Width = 740; 

int canvas_Height = 540;

color background_Color = color(80,90,100);

boolean started; // Returns true if the demo is running.

boolean paused; // Returns true if the demo is paused.

void settings() {
    
    size(canvas_Width, canvas_Height);
}

void setup() {
    
    background(background_Color);
    
    frameRate(60);
} 

Object_Manager object_Manager = new Object_Manager();

Mapper mapper = new Mapper(100 ,100);

Ui ui = new Ui();

void draw() {
    
    background(background_Color);
    
    ui.run(); 
}

void mouseClicked() {
    
    for (Button button : ui.buttons) {
        
        if (button.active && mouseX > button.location.x && mouseX < button.size.x + button.location.x 
            && mouseY > button.location.y && mouseY < button.size.y + button.location.y) {
 
            button.applied = !button.applied;
        
        if (button.function == Function.START_STOP && button.applied) object_Manager.initialized = false;
            
        }    
    }
     for (CheckBox checkbox : ui.checkboxes) {

        if (checkbox.active && mouseX > checkbox.location.x && mouseX < checkbox.size.x + checkbox.location.x 
            && mouseY > checkbox.location.y && mouseY < checkbox.size.y + checkbox.location.y) {
            
            checkbox.checked = !checkbox.checked;
        }    
    }  
}
