import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Object_Mapping_And_Collision_System_Demo extends PApplet {




// keys for accesing parameters.
final int x = 0;  final int y = 1;  final int p = 2; // p stands for pixel.

final int min = 0;  final int max = 1; // minimum and maximum values for a parameters
//*************************************************************************************
final int black = color(0); final int white = color(255,255,255); final int red = color(255,0,0); 

final int green = color(0,200,0); final int blue = color(0,0,200); final int yellow = color(200,200,0);

enum Edge {TOP, BOTTOM, LEFT, RIGHT}

enum Collision_Mode {STOP, BOUNCE, IGNORE}

enum Option {STOP, BOUNCE, IGNORE} // For checkboxes

enum Category {EDGE, OBJECT} // Collision modes

enum Function {START_STOP, PAUSE_RESUME, ABOUT, NEXT_PREV} // For buttons

int canvas_Width = 740; 

int canvas_Height = 540;

int background_Color = color(80,90,100);

boolean started; // Returns true if the demo is running.

boolean paused; // Returns true if the demo is paused.

public void settings() {
    
    size(canvas_Width, canvas_Height);
}

public void setup() {
    
    background(background_Color);
    
    frameRate(60);
} 

Object_Manager object_Manager = new Object_Manager();

Mapper mapper = new Mapper(100 ,100);

Ui ui = new Ui();

public void draw() {
    
    background(background_Color);
    
    ui.run(); 
}

public void mouseClicked() {
    
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
class Button extends Button_Class {
    
    Function function; // An enum that designates the function of the button.
    
    Button(Function function, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        this.function = function;
        
        size = new PVector(size_X, size_Y);
        
        location = new PVector(loc_X, loc_Y);
        
        button_Color = color(50, 60, 70);
        
        hovered_Color = color(45,55,65);
        
        pressed_Color = color(40,50,60);
        
        inactive_Color = color(40,50,60);
    }
    
    public void display() {
        
        if (hidden == false) {
            
            if (active) {
                
                check_Mouse();
                
                if (hovered && pressed) {
                    
                    fill(pressed_Color);
                    
                } else if (hovered) {
                    
                    fill(hovered_Color);
                    
                } else {
                    
                    fill(button_Color);
                }
                
            } else {
                
                fill(inactive_Color);
            }
            
            rect(location.x, location.y, size.x, size.y);  
        }
    }
    }
class Button_Class {
    
    PVector size;
    
    PVector location;
    
    int button_Color;
    
    int pressed_Color;
    
    int hovered_Color;
    
    int inactive_Color;
    
    boolean active;
    
    boolean hidden;
    
    boolean pressed; // Returns true if a button is pressed.
    
    boolean applied; // Returns true if the function of the button has been applied.
    
    boolean hovered; // Returns  true if the mouse is hovering on the button.
    
    
    public void check_Mouse() {
        
        if (active) {
            
            if (mouseX > location.x && mouseX < size.x + location.x 
                && mouseY > location.y && mouseY < size.y + location.y) {
                
                hovered = true;
                
                if (mousePressed) {
                    
                    pressed = true;
                    
                } else {pressed = false;
                }
                
            } else { 
                
                hovered = false;   
                
                pressed = false; 
            }   
        } 
    } 
    
    public void update() {
        
    }
}   

class CheckBox extends Button_Class {
    
   Category category; // An enum that sets the category to which the checkbox belongs.
   
    Option option; // An enum that sets the option that this checkbox represents.
    
    boolean checked; // returns true if the checkbox is checked
    
    int checked_Color = color(0,255,0);
    
    int checked__Hovered_Color = color(0,255,0);
    
    int checked_Inactive_Color = color(0,120,0);
    
    CheckBox(Category category, Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        this.category = category;
        
        this.option = option;
        
        size = new PVector(size_X, size_Y);
        
        location = new PVector(loc_X, loc_Y);
        
        button_Color = color(80,90,100);
        
        pressed_Color = color(70,80,90);
        
        hovered_Color = color(75,85,95);
        
        inactive_Color = color(40,50,60);
    }
    
    public void update() {
        
        if (hidden == false) { 
            
            if (active) {
                
                check_Mouse();
                
                if (hovered && pressed) {
                    
                    fill(pressed_Color);
                    
                } else if (hovered && checked) {
                    
                    fill(checked__Hovered_Color);
                    
                } else if (hovered) {
                    
                    fill(hovered_Color);
                    
                } else if (checked) {
                    
                    fill(checked_Color);
                    
                } else {
                    
                    fill(button_Color);
                }
                
            } else if (checked && started == false) {
                
                fill(inactive_Color);
                
            } else if (checked) {fill(checked_Inactive_Color);
                
            } else {
                
                fill(inactive_Color);
 
            }       
            rect(location.x, location.y, size.x, size.y);  
        }
    }
}
class Hoverbox {
    
    PVector size;
    
    PVector location;
    
    int box_Color;
    
    int hovered_Color;
    
    boolean hovered; // Returns true if the mouse is hovering on the hoverbox.
    
    boolean active;
    
    Category category; // An enum that sets the category to which the hoverbox belongs.
    
    Option option; // An enum that sets the option that this hoverbox represents.
    
    Hoverbox(Category category, Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        this.category = category;
        
        this.option = option;
        
        size = new PVector(size_X, size_Y);
        
        location = new PVector(loc_X, loc_Y);
        
        box_Color = color(80, 90, 100);
        
        hovered_Color = color(70,80,90);
    }
    
    public void check_Mouse() {
        
        if (active) {
            
            if (mouseX > location.x && mouseX < size.x + location.x 
                && mouseY > location.y && mouseY < size.y + location.y) {hovered = true;
                
            }  else {hovered = false;}   
        } 
    } 
    
    public void update() {
        
       check_Mouse();
       
       if (active) {
       
        if (hovered) {fill(hovered_Color);
            
        } else {fill(box_Color);}
        
        rect(location.x, location.y, size.x, size.y);
    
       } 
    }
}

PGraphics mapper_Canvas;

class Mapper {
    
    int mapper_Width;
    
    int mapper_Height;  
    
    // An ArrayList that stores the numbers and coordinates of all the pixels that comprise the object.
    
    ArrayList<ArrayList<Integer>> object_ArrList = new ArrayList<ArrayList<Integer>>(); 
    
    // An Arraylist that stores the values of "object_ArrList" from lowest to highest.
    
    ArrayList<ArrayList<Integer>> object_ArrList_Sorted = new ArrayList<ArrayList<Integer>>(); 
    
    Mapper(int mapper_Width, int mapper_Height) {
        
        this.mapper_Width = mapper_Width;
        
        this.mapper_Height = mapper_Height;
    }
    
    int background_Color = color(20,20,20);
    
    int number_Of_Pixels; // The total amount of pixels that comprise the object.
    
    int number_Of_Parameters = 2; // x and y.
    
    int[] center_Values_at_Origin; // the x and y values of the center of the object when it is placed at origin.
    
    PShape object_Shape;
    
    public void map_Object(int angle, float pivot_Point_X, float pivot_Point_Y) {
        
        mapper_Canvas = createGraphics(mapper_Width, mapper_Height);
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            object_ArrList.add(new ArrayList());
            
            object_ArrList_Sorted.add(new ArrayList());
        }
        
        number_Of_Pixels = 0;
        
        mapper_Canvas.beginDraw();
        
        mapper_Canvas.background(background_Color);
        
        mapper_Canvas.loadPixels();

        mapper_Canvas.pushMatrix();
        
        mapper_Canvas.translate((mapper_Canvas.width / 2), mapper_Canvas.height / 2);
        
        mapper_Canvas.rotate(radians(angle));
        
        object_Shape.setFill(false); // The mapper is set to only scan the object's outline.

        mapper_Canvas.shape(object_Shape, pivot_Point_X, pivot_Point_Y);

        mapper_Canvas.popMatrix();
        
        mapper_Canvas.endDraw();
        
        for (int height_Index = 0; height_Index < mapper_Canvas.width; height_Index++) {
            
            for (int width_Index = 0; width_Index < mapper_Canvas.height; width_Index++) {
                
                int pixel_Color = color(mapper_Canvas.get(width_Index,height_Index));
                
                if (pixel_Color != background_Color) { 
                    
                    number_Of_Pixels++;
                    
                    object_ArrList.get(x).add(width_Index);
                    
                    object_ArrList.get(y).add(height_Index);         
                    
                    object_ArrList_Sorted.get(x).add(width_Index);
                    
                    object_ArrList_Sorted.get(y).add(height_Index);
                }  
            }
        }
        
        find_Min_And_Max_Values();
        
        align_Object_To_Upper_Left_Screen();
    }
    
    int[][] value; // An array that stores the minimum and maximum x and y values of the object.
    
    int index_Of_Min_Value;
    
    int index_Of_Max_Value;
    
    public void find_Min_And_Max_Values() {
        
        index_Of_Min_Value = 0;
        
        index_Of_Max_Value = number_Of_Pixels - 1;
        
        value = new int[number_Of_Parameters][2];
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            Collections.sort(object_ArrList_Sorted.get(i)); 
            
            value[i][min] = object_ArrList_Sorted.get(i).get(index_Of_Min_Value);
            
            value[i][max] = object_ArrList_Sorted.get(i).get(index_Of_Max_Value);
        } 
    }
    
    public void align_Object_To_Upper_Left_Screen() {
        
        int[] temp_Min = new int[number_Of_Parameters];
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            for (int j = 0; j < number_Of_Pixels; j++) {
                
                int index_Value = object_ArrList.get(i).get(j);
                
                object_ArrList.get(i).set(j, index_Value - (value[i][min]));
                
                object_ArrList_Sorted.get(i).set(j, index_Value - (value[i][min]));
            }
            
            Collections.sort(object_ArrList_Sorted.get(i)); 
            
            center_Values_at_Origin = new int[2];
            
            temp_Min[i] = value[i][min];
            
            value[i][min] = object_ArrList_Sorted.get(i).get(index_Of_Min_Value);
            
            value[i][max] = object_ArrList_Sorted.get(i).get(index_Of_Max_Value);
            
            center_Values_at_Origin[x] = mapper_Canvas.width / 2 - temp_Min[x];
            
            center_Values_at_Origin[y] = mapper_Canvas.height / 2 - temp_Min[y];
        }          
    }
    
    public int[] get_Object_Array(PShape object_Shape, int angle, float pivot_Point_X, float pivot_Point_Y) {
        
        this.object_Shape = object_Shape;
        
        map_Object(angle, pivot_Point_X, pivot_Point_Y);
        
        int[] final_Object_Array = new int[(number_Of_Pixels * 2) + 7];
        
        final_Object_Array[0] = number_Of_Pixels;
        
        final_Object_Array[1] = center_Values_at_Origin[x];
        
        final_Object_Array[2] = center_Values_at_Origin[y];
        
        final_Object_Array[3] = value[x][min];
        
        final_Object_Array[4] = value[x][max];
        
        final_Object_Array[5] = value[y][min];
        
        final_Object_Array[6] = value[y][max];
        
        int i; 
        
        for (i = 7; i < number_Of_Pixels + 7; i++) {
            
            final_Object_Array[i] = object_ArrList.get(x).get(i - 7);
        }
        
        i = 7;
        
        for (int j = number_Of_Pixels + 7; j < (number_Of_Pixels * 2) + 7; j++) {
            
            i++;
            
            final_Object_Array[j] = object_ArrList.get(y).get(i - 8);
        }
        
        object_ArrList.clear();
        
        object_ArrList_Sorted.clear();
        
        return final_Object_Array;  
    }
}

class Object_Manager {
    
    ArrayList<Shape_Object> shapes = new ArrayList<Shape_Object>(); // an Arraylist that stores all the shape objects.
    
    public void create_Triangle(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(3, radius, loc_x, loc_y, speed_x, speed_y, angle)); 
        
        shapes.get(shapes.size() - 1).shape_Color = color(70,110,150);
    }
    
    public void create_Square(int size_x, int size_y, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Rectangle(size_x, size_y, loc_x, loc_y, speed_x, speed_y, angle));      
    }
    
    public void create_Pentagon(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(5, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(120,0,0);
    }
    
    public void create_Octagon(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(8, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(0,80,80);
    }
    
    public void create_Polygon(int n_points, float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(n_points, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(120,0,0);
    }
    
    public void create_Star(int n_points, float radius1, float radius2, float loc_x, float loc_y
        , float speed_x, float speed_y, int angle) {
        
        shapes.add(new Star(n_points, radius1, radius2, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(80,90,100);
    }
    
    ArrayList<String> collided_Pairs = new ArrayList<String>();

    public void collision_Detection(Shape_Object shape_A) {
        
        for (Shape_Object shape_B : shapes) {
            
            if (shapes.indexOf(shape_A) < shapes.indexOf(shape_B))  {
                
                String pair = shapes.indexOf(shape_A) + " and " + shapes.indexOf(shape_B);
                
                boolean colliding  = !Collections.disjoint(Arrays.asList(shape_A.object_Pixel_Array)
                    , Arrays.asList(shape_B.object_Pixel_Array)); // returns false if the two object arrays 
                // contain a common value. The exclamation mark inverts it for the sake of readabilty.
                
                if (colliding && collided_Pairs.contains(pair) 
                    && shape_A.speed.x != 0 && shape_A.speed.y != 0 && shape_B.speed.x != 0  & shape_B.speed.x != 0) {
                    
                    shape_A.object_Collision();  // 
                    
                }  if (colliding && collided_Pairs.contains(pair) == false) {
                    
                    collided_Pairs.add(pair);
                    
                    shape_A.object_Collision();
                    
                    shape_B.object_Collision();
                    
                } else if (colliding && collided_Pairs.contains(pair)) {
                    
                }  else {
                    
                    collided_Pairs.remove(pair);
                }      
            }                 
        }
    }
    
    public void update_Shapes() {
        
        for (Shape_Object shape : shapes) {
            
            if (paused == false) {
                
                collision_Detection(shape); 
                
                shape.add_Speed();
                
                shape.set_Angle();
                
                shape.edge_Detection();  
            }       
           
            shape.display(); 
        }
    }  
    
    boolean initialized = false;

    public void run() {
        
        if (initialized == false) { // The code here runs once when the program is launched or the demo is started.
            
            shapes.clear();
            
            create_Triangle(20, 456, 300, -1.2f, 2, 1); 
            
            shapes.get(0).rotation_Speed = -8; shapes.get(0).center_Of_Rotation.y -= 20;
            
            create_Triangle(20, 320, 200, 2.4f, -3, 123); 
            
            shapes.get(1).rotation_Speed = 3;
            
            create_Square(24, 24, 600, 200, -1.4f, -2.8f, 56);
            
            shapes.get(2).rotation_Speed = -4;
            
            create_Square(48, 76, 370, 80, 3.2f, -2.4f, 236);
            
            shapes.get(3).rotation_Speed = 2;
            
            create_Square(64, 12, 276, 270, 0.8f, 2.3f, 0);
            
            shapes.get(4).rotation_Speed = 6; shapes.get(4).center_Of_Rotation.x -= 10;
            
            create_Pentagon(24,300,350,1.2f,0.8f,128);
            
            shapes.get(5).rotation_Speed = 3;
            
            create_Octagon(18,550,450,2,1.7f,76);
            
            shapes.get(6).rotation_Speed = -2;
            
            create_Star(3,32, 8,450,370,0,0,14);
            
            shapes.get(7).rotation_Speed = -4;
            
            create_Star(8,24,8,370,322,0.7f,0.7f,14);
            
            shapes.get(8).rotation_Speed = 2;
            
            initialized = true; 
        }
        
        update_Shapes();   
    }      
}
class Polygon extends Shape_Object {
    
    public PShape polygon(float x, float y, float radius, int n_points) {
        
        float angle = TWO_PI / n_points;
        
        PShape polygon = createShape();
        
        polygon.beginShape();
        
        for (float a = 0; a < TWO_PI; a += angle) {
            
            float sx = x + cos(a) * radius;
            
            float sy = y + sin(a) * radius;
            
            polygon.vertex(sx, sy);
        }
        polygon.endShape(CLOSE);
        
        return polygon;
    }
    
    Polygon(int n_points, float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        location = new PVector(loc_x, loc_y);
        
        speed = new PVector(speed_x, speed_y);
        
        center_Of_Rotation = new PVector(0,0);
        
        this.angle = angle;
       ;   
        shape_Outline = polygon(0,0,radius, n_points);
        
        shape_Outline.setStroke(0);
        
        shape = polygon(0,0,radius, n_points);
        
        shape.setStroke(255);
        
        shape.setFill(shape_Color);       
        
        initialize_Object_Array();
        
    }
    
}
class Rectangle extends Shape_Object {
    
    Rectangle(int size_X, int size_Y, float loc_X, float loc_Y, float speed_X, float speed_Y, int angle) {

        PVector size = new PVector(size_X,size_Y);
        
        location = new PVector(loc_X, loc_Y);
        
        speed = new PVector(speed_X,speed_Y);
       
        center_Of_Rotation = new PVector(-size.x / 2, -size.y / 2);
        
        this.angle = angle;
        
        shape_Outline = createShape(RECT, 0, 0, size.x, size.y);
        
        shape_Outline.setStroke(0);
        
        shape = createShape(RECT, 0, 0, size.x, size.y);
        
        shape.setStroke(255);
        
        shape_Color = color(0,0,120);
    
        shape.setFill(shape_Color);       
       
       initialize_Object_Array();
        
    }
}

class Shape_Object {
    
    final int number_Of_Parameters = 3;
    
    Collision_Mode edge_Collision_Mode = Option_To_Collision_Mode(ui.edge_Collision_Mode);
    
    Collision_Mode object_Collision_Mode = Option_To_Collision_Mode(ui.object_Collision_Mode);
    
    PShape shape;
    
    PShape shape_Outline; // The outline of the shape without the fill.
    
    int shape_Color;
    
    int number_Of_Pixels; // The total number of pixels that comprise the object.
    
    int angle;
    
    int rotation_Speed;
    
    PVector location;
    
    PVector speed;
    
    PVector object_Center_At_Origin;
    
    PVector center_Of_Rotation;
    
    int[] template_Arr;
    
    String[] object_Pixel_Array;
    
    int[][] object_Array;
    
    int[][] value;
    
    public Collision_Mode Option_To_Collision_Mode(Option value) { // Enum conversion.
        
        return Collision_Mode.values()[value.ordinal()];
    }
    
    public void initialize_Object_Array() {
        
        template_Arr = mapper.get_Object_Array(shape_Outline, angle, center_Of_Rotation.x, center_Of_Rotation.y);
        
        number_Of_Pixels = template_Arr[0];
        
        object_Center_At_Origin = new PVector(template_Arr[1],template_Arr[2]);
        
        value = new int[number_Of_Parameters][number_Of_Parameters];
        
        value[x][min] = template_Arr[3];     
        
        value[x][max] = template_Arr[4];
        
        value[y][min] = template_Arr[5];     
        
        value[y][max] = template_Arr[6];
        
        object_Array = new int[number_Of_Pixels][number_Of_Parameters];
        
        object_Pixel_Array = new String[number_Of_Pixels];
        
        int x_Index = 7; int y_Index = number_Of_Pixels + 7;
        
        for (int i = 0; i < number_Of_Pixels; i++) {
            
            object_Array[i][x] = template_Arr[x_Index] + (int) location.x;
            
            object_Array[i][y] = template_Arr[y_Index] + (int) location.y;
            
            object_Array[i][p] = width * object_Array[i][y] + object_Array[i][x];
            
            object_Pixel_Array[i] = String.valueOf(object_Array[i][p]);
            
            x_Index++;  y_Index++; 
        } 
    }
    
    public void update_Array() {
        
        int x_Index = 7; int y_Index = number_Of_Pixels + 7;
        
        for (int i = 0; i < number_Of_Pixels; i++) {
            
            object_Array[i][x] = template_Arr[x_Index] + (int) location.x; 
            
            object_Array[i][y] = template_Arr[y_Index] + (int) location.y;  
            
            object_Array[i][p] = width * object_Array[i][y] + object_Array[i][x];
            
            object_Pixel_Array[i] = String.valueOf(object_Array[i][p]);
            
            x_Index++;  y_Index++; 
        }
    }
    
    public void add_Speed() {
        
        location.add(speed);
  
    }
    
    public void set_Angle() {
        
        if (angle > 359) angle = 0; 
        
        if (angle < 0) angle = 359;
        
        angle += rotation_Speed;
        
        PVector temp_Location = new PVector(object_Center_At_Origin.x + location.x
            ,object_Center_At_Origin.y + location.y);
        
        initialize_Object_Array();
        
        temp_Location.sub(object_Center_At_Origin);
        
        location.set(temp_Location);
        
        update_Array();
    }
    
    boolean touching_Edge;
    
    Edge edge;
    
    public void edge_Detection() {
        
        if (location.x >= width - value[x][max] || location.x <= ui.size.x 
            
            || location.y >= height - value[y][max] || location.y <= 0) {
            
            touching_Edge = true;
            
            if (location.x >= width - value[x][max]) edge = Edge.RIGHT;
            
            if (location.x <= ui.size.x) edge = Edge.LEFT;
            
            if (location.y >= height - value[y][max]) edge = Edge.BOTTOM;
            
            if (location.y <= 0) edge = Edge.TOP;
            
            edge_Collision();
            
        } else {touching_Edge = false;}
    }
    
    public void edge_Collision() {
        
        if (touching_Edge) {
            
            if (edge == Edge.RIGHT || edge == Edge.LEFT) {
                
                rotation_Speed *= -1;
                
                speed.x *=  - 1;  
                
            } else if (edge == Edge.TOP || edge == Edge.BOTTOM) {
                
                rotation_Speed *= -1;
                
                speed.y *=  - 1;
            }
            
            switch(edge) {
                
                case RIGHT : 
                    
                    location.x = width - value[x][max] - 1; 
                    break;
                
                case LEFT :
                    
                    location.x = ui.size.x + 1; 
                    break;
                
                case TOP : 
                    
                    location.y = 0;
                    break;
                
                case BOTTOM : 
                    
                    location.y = height - value[y][max] - 1; 
                    break;      
            }
        }   
        
        if (touching_Edge && edge_Collision_Mode == Collision_Mode.STOP) {
            
            speed.set(0,0);
            
            rotation_Speed = 0;
        }
    }
 
    public void object_Collision() {
        
        if (speed.x != 0 && speed.y != 0) {

            location.sub(Math.abs(speed.x) / speed.x, Math.abs(speed.y) / speed.y);
            
        }
        
        if (object_Collision_Mode == Collision_Mode.STOP) {
            
            speed.set(0,0);
            
            rotation_Speed = 0;
            
        } else if (object_Collision_Mode == Collision_Mode.BOUNCE) {
            
            rotation_Speed *= -1;
            
            speed.set(speed.x *= -1, speed.y *= -1);        
        } 
    }
    
    public void display() {
        
        pushMatrix();
        
        translate((int) location.x + object_Center_At_Origin.x,location.y + (int) object_Center_At_Origin.y);
        
        rotate(radians(angle));
        
        shape.setFill(shape_Color);
        
        shape(shape, center_Of_Rotation.x, center_Of_Rotation.y);
        
        popMatrix();  
    }
}

class Star extends Shape_Object{
    
    public PShape star(float x, float y, float radius1, float radius2, int n_points) {
        
        float angle = TWO_PI / n_points;
        
        float halfAngle = angle / 2.0f;
        
        PShape star = createShape();
        
        star.beginShape();
        
        for (float a = 0; a < TWO_PI; a += angle) {
            
            float sx = x + cos(a) * radius2;
            
            float sy = y + sin(a) * radius2;
            
            star.vertex(sx, sy);
            
            sx = x + cos(a + halfAngle) * radius1;
            
            sy = y + sin(a + halfAngle) * radius1;
            
            star.vertex(sx, sy);
        }
        star.endShape(CLOSE);
        
        return star;       
    }
    
    Star(int n_points, float radius1, float radius2, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {

        location = new PVector(loc_x, loc_y);
        
        speed = new PVector(speed_x, speed_y);
        
        center_Of_Rotation = new PVector(0,0);
        
        this.angle = angle;
        
        shape_Outline = star(0,0,radius1,radius2,n_points);
        
        shape = star(0,0,radius1,radius2,n_points);
        
        shape.setStroke(255);
        
        shape_Color = color(70,50,60);
        
        shape.setFill(shape_Color);       
        
        initialize_Object_Array();    
    }   
}

class Ui {
    
    // keys for accesing buttons in the buttons ArrayList.
    final int start_Stop = 0; final int about = 1; final int pause_Resume = 2; final int next_Prev = 3;
    
    // Keys for accesing collision mode checkboxes in the checkboxes ArrayList
    final int stop = 0; final int edge_Bounce = 1; final int object_Bounce = 3;
    
    PVector size;
    
    int text_Color = color(210,220,230);
    
    Ui() {
        
        size = new PVector(200, canvas_Height);   
    }
    
    ArrayList<Button> buttons = new ArrayList<Button>();
    
    public void create_Button(Function function, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        buttons.add(new Button(function, loc_X, loc_Y, size_X, size_Y));
    }
    
    ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
    
    public void create_Checkbox(Category category,Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        checkboxes.add(new CheckBox(category, option, loc_X, loc_Y, size_X, size_Y));
    }
    
    ArrayList<Hoverbox> hoverboxes = new ArrayList<Hoverbox>();
    
    public void create_Hoverbox(Category category,Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        hoverboxes.add(new Hoverbox(category, option, loc_X, loc_Y, size_X, size_Y));
    }
    
    public void update_Buttons() {
        
        for (Button button : buttons) {
            
            button.display();      
        }
    }
    
    Option edge_Collision_Mode;
    
    Option object_Collision_Mode;
    
    public void update_Checkboxes() {
        
        for (CheckBox checkbox : checkboxes) {
            
            if (started == false) {
                
                checkbox.active = true;
                
                if (checkbox.category == Category.EDGE && checkbox.checked && checkbox.pressed) edge_Collision_Mode = checkbox.option;
                
                if (checkbox.category == Category.EDGE && checkbox.checked && checkbox.pressed == false && edge_Collision_Mode != checkbox.option) {
                    
                    checkbox.checked = false;}
                
                if (checkbox.category == Category.EDGE  && checkbox.pressed && edge_Collision_Mode == checkbox.option) {
                    
                    checkbox.checked = true;}
                
                if (checkbox.category == Category.OBJECT && checkbox.checked && checkbox.pressed) object_Collision_Mode = checkbox.option;
                
                if (checkbox.category == Category.OBJECT && checkbox.checked && checkbox.pressed == false && object_Collision_Mode != checkbox.option) {
                    
                    checkbox.checked = false;}
                
                if (checkbox.category == Category.OBJECT  && checkbox.pressed && object_Collision_Mode == checkbox.option) {
                    
                    checkbox.checked = true;}
                
            } else {checkbox.active = false;}
            
            if (buttons.get(1).applied && started == false) {
                
                checkbox.active = false;      
            }
            checkbox.update();
        }
    } 
    
    public void update_Hoverboxes() {
        
        for (Hoverbox hoverbox : hoverboxes) {
            
            float text_X = hoverbox.location.x + 64; // the text's x position is always the same.
            
            float window_X = hoverbox.location.x + 58; // the window's x position is always the same.
            
            hoverbox.update();
            
            create_Text("?", 18, text_Color, hoverbox.location.x + 11, hoverbox.location.y + 22);
            
            if (hoverbox.hovered && hoverbox.active) {
                
                if (hoverbox.category == Category.EDGE && hoverbox.option == Option.STOP) {
                    
                    create_Rect(window_X, hoverbox.location.y - 56 + hoverbox.size.y, 258, 56, 50,60,70);
                    
                    create_Text("When colliding with an edge\nthe object will freeze."
                        , 18, text_Color, text_X, hoverbox.location.y - 56 + hoverbox.size.y + 20);
                    
                } else if (hoverbox.category == Category.EDGE && hoverbox.option == Option.BOUNCE) {
                    
                    create_Rect(window_X, hoverbox.location.y - 112 + hoverbox.size.y, 224, 112, 50,60,70);
                    
                    create_Text("When colliding with an\nedge the object will\nreverse its direction\nand rotational direction."
                        , 18, text_Color, text_X, hoverbox.location.y - 112 + hoverbox.size.y + 20);
                    
                } else if (hoverbox.category == Category.OBJECT && hoverbox.option == Option.STOP) {
                    
                    create_Rect(window_X, hoverbox.location.y - 56 + hoverbox.size.y, 190, 56, 50,60,70);
                    
                    create_Text("When objects collide\nthey will freeze."
                        , 18, text_Color, text_X, hoverbox.location.y - 56 + hoverbox.size.y + 20);
                    
                } else if (hoverbox.category == Category.OBJECT && hoverbox.option == Option.BOUNCE) {
                    
                    create_Rect(window_X, hoverbox.location.y - 112 + hoverbox.size.y, 216, 112, 50,60,70);
                    
                    create_Text("When objects collide\nthey will reverse their\ndirection and rotational\ndirection."+
                        "\nStill in development."
                        , 18, text_Color, text_X, hoverbox.location.y - 112 + hoverbox.size.y + 20);
                    
                } else if (hoverbox.category == Category.OBJECT && hoverbox.option == Option.IGNORE) {
                    
                    create_Rect(window_X, hoverbox.location.y - 84 + hoverbox.size.y, 194, 84, 50,60,70);
                    
                    create_Text("objects will not react\nto collisions with\nother objects."
                        , 18, text_Color, text_X, hoverbox.location.y - 84 + hoverbox.size.y + 20);
                }
            }
        }
    }   
    
    public void create_Text(String text, int text_Size, int text_Color, float loc_X, float loc_Y) {
        
        textSize(text_Size);
        
        fill(text_Color);
        
        text(text, loc_X, loc_Y);
    }
    
    public void create_Rect(float loc_X, float loc_Y, float size_X, float size_Y, int rect_Color) {
        
        fill(rect_Color);
        
        rect(loc_X , loc_Y, size_X, size_Y);
    }
    
    public void create_Rect(float loc_X, float loc_Y, float size_X, float size_Y, int r, int g, int b) {
        
        fill(r,g,b);
        
        rect(loc_X , loc_Y, size_X, size_Y);
    }
    
    boolean initialized;
    
    String about_Text_1 = "This program is applying per-pixel object mapping.\nEach object has an array that stores"+
    " the numbers of all\nthe pixels that comprise it. When an object moves,\nrotates, or changes appearance"+
    " the array is updated\nwith the changes as well. The object mapping is done\nby drawing the object"+
    " to an invisible empty canvas\nwhich has a background color that is not used in the\nobject. This canvas is" + 
    " then scanned pixel-by-pixel, \nand the color of each pixel is compared to the canvas\nbackground color. "+
    "If the colors are not the same, The\npixel must therefore be occupied by the object, and its\nnumber is stored"+
    " in the object's array. At the end of\nthe process, a complete map of the object is obtained.\nThe object map is"+
    " utilized in the collision detection\nsystem, which examines the arrays of two objects at a\ntime, and checks "+
    "if they contain a common\npixel number. If they do, it determines that";
    
    String about_Text_2 = "they are colliding, and sends an alert to both objects.\nCollisions are divided into two categories:"+
    " collisions\nwith the edges, and collisions between objects. Each\ncategory has different modes that you can choose" + 
    "\nfrom that change the way objects react to a collision.";
    
    public void run() {
        
        if (initialized == false) { // The code here runs once when the program is launched or the demo is started.
            
            create_Button(Function.START_STOP, 0, 0, size.x, 100); 
            
            create_Button(Function.ABOUT, 0, 100, size.x, 100); 
            
            create_Button(Function.PAUSE_RESUME, 0, 100, size.x, 100); 
            
            create_Button(Function.NEXT_PREV, canvas_Width - 84, canvas_Height - 54, 64, 34); 
            
            create_Checkbox(Category.EDGE, Option.STOP, 20, 264, 28, 28);
            
            create_Hoverbox(Category.EDGE, Option.STOP, 152, 264, 28, 28);
            
            create_Checkbox(Category.EDGE, Option.BOUNCE, 20, 306, 28, 28);
            
            create_Hoverbox(Category.EDGE, Option.BOUNCE, 152, 306, 28, 28);
            
            create_Checkbox(Category.OBJECT, Option.STOP, 20, 414, 28, 28);
            
            create_Hoverbox(Category.OBJECT, Option.STOP, 152, 414, 28, 28);
            
            create_Checkbox(Category.OBJECT, Option.BOUNCE, 20, 454, 28, 28);
            
            create_Hoverbox(Category.OBJECT, Option.BOUNCE, 152, 454, 28, 28);
            
            create_Checkbox(Category.OBJECT, Option.IGNORE, 20, 494, 28, 28);
            
            create_Hoverbox(Category.OBJECT, Option.IGNORE, 152, 494, 28, 28);
            
            for (Button button : buttons) {
                
                button.active = true;
            }
            
            for (CheckBox checkbox : checkboxes) {
                
                checkbox.active = true;
            }
            
            for (Hoverbox hoverbox : hoverboxes) {
                
                hoverbox.active = true;
            }
            
            edge_Collision_Mode = Option.BOUNCE;
            
            checkboxes.get(edge_Bounce).checked = true; 
            
            object_Collision_Mode = Option.BOUNCE;
            
            checkboxes.get(object_Bounce).checked = true; 
            
            initialized = true;    
        }   
        
        create_Rect(0, 100, size.x, size.y, 50,60,70);
        
        create_Rect(0, 200, size.x, 45, 60, 70, 80);
        
        create_Rect(0, 350, size.x, 45, 60, 70, 80);
        
        create_Text("EDGE COLLISION MODE", 16, text_Color, 10, 230);
        
        create_Text("STOP", 18, text_Color,  60, 286);
        
        create_Text("BOUNCE", 18, text_Color, 60, 328);
        
        create_Text("OBJECT COLLISION MODE", 16, text_Color, 2, 380);
        
        create_Text("STOP", 18, text_Color, 60, 436);
        
        create_Text("BOUNCE", 18, text_Color, 60, 476);
        
        create_Text("IGNORE", 18, text_Color, 60, 516);
        
        update_Buttons();
        
        update_Checkboxes();
        
        if (buttons.get(start_Stop).applied) {
            
            started = true;
            
            buttons.get(about).active = false;
            
            buttons.get(about).hidden = true;
            
            object_Manager.run();
            
            create_Text("STOP", 58, red,  28, 72);
            
        }  else if (buttons.get(about).applied == false) {
            
            started = false;
            
            create_Text("START", 58, green, 9, 72);
            
            background_Color = color(25,35,45);
            
            create_Text("  Object Mapping And\nCollision System Demo", 42, text_Color, 236, 60);
            
            if (started) {
                
                buttons.get(about).active = false;
                
                buttons.get(about).hidden = true;
                
            }  else {
                
                buttons.get(about).active = true;
                
                buttons.get(about).hidden = false;
            } 
            
            if (buttons.get(next_Prev).applied) buttons.get(next_Prev).applied = false;
        }
        
        if (started && paused == false) {
            
            create_Text("PAUSE", 56, yellow, 14, 172);
            
        } else if (started && paused) {
            
            create_Text("RESUME", 48, green, 8, 172);   
        }  
        
        if (started == false && buttons.get(about).applied == false) {
            
            create_Text("ABOUT", 56, text_Color,  6, 172);
        }
        
        if (started) buttons.get(about).applied = false;
        
        if (buttons.get(about).applied && started == false) {
            
            buttons.get(start_Stop).active = false;
            
            buttons.get(next_Prev).active = true;
            
            buttons.get(next_Prev).hidden = false;
            
            create_Text("CLOSE", 56, text_Color, 14, 172);
            
            create_Text(" Created by\nYiftah Arbeli" , 22, yellow, 36, 40);
            
            buttons.get(next_Prev).display();
            
            if (buttons.get(next_Prev).applied) {
                
                create_Text(about_Text_2, 20, text_Color, 204, 30);
                
                create_Text("PREV", 22, text_Color, canvas_Width - 78, canvas_Height - 28);
                
            } else {
                
                create_Text(about_Text_1, 20, text_Color, 204, 30);
                
                create_Text("NEXT", 22, text_Color, canvas_Width - 80, canvas_Height - 28);     
            }    
            
        } else {
            
            buttons.get(start_Stop).active = true;
            
            buttons.get(next_Prev).active = false;
            
            buttons.get(next_Prev).hidden = true;
        }
        
        if (started && paused == false && buttons.get(start_Stop).pressed) {
            
            paused = false;
            
            buttons.get(pause_Resume).applied = false;
            
        } else if (started && buttons.get(pause_Resume).applied) {paused = true;  
            
        } else {
            
            paused = false;
        }  
        
        update_Hoverboxes();
    }
}   
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Object_Mapping_And_Collision_System_Demo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
