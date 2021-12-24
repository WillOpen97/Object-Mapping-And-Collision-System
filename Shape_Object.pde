
class Shape_Object {
    
    final int number_Of_Parameters = 3;

    PShape shape;
    
    PShape shape_Outline; // The outline of the shape without the fill.
    
    color shape_Color;
    
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

    void initialize_Object_Array() {
        
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
    
    void update_Array() {
        
        int x_Index = 7; int y_Index = number_Of_Pixels + 7;
        
        for (int i = 0; i < number_Of_Pixels; i++) {
            
            object_Array[i][x] = template_Arr[x_Index] + (int) location.x; 
            
            object_Array[i][y] = template_Arr[y_Index] + (int) location.y;  
            
            object_Array[i][p] = width * object_Array[i][y] + object_Array[i][x];
            
            object_Pixel_Array[i] = String.valueOf(object_Array[i][p]);
            
            x_Index++;  y_Index++; 
        }
    }
    
    void add_Speed() {
        
        location.add(speed);
  
    }
    
    void set_Angle() {
        
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
    
    void edge_Detection() {
        
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
    
    void edge_Collision() {
        
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
        
        if (touching_Edge && object_Manager.edge_Collision_Mode == Collision_Mode.STOP) {
            
            speed.set(0,0);
            
            rotation_Speed = 0;
        }
    }
 
    void object_Collision() {
        
        if (speed.x != 0 && speed.y != 0) {

            location.sub(Math.abs(speed.x) / speed.x, Math.abs(speed.y) / speed.y);
            
        }
        
        if (object_Manager.object_Collision_Mode == Collision_Mode.STOP) {
            
            speed.set(0,0);
            
            rotation_Speed = 0;
            
        } else if (object_Manager.object_Collision_Mode == Collision_Mode.BOUNCE) {
            
            rotation_Speed *= -1;
            
            speed.set(speed.x *= -1, speed.y *= -1);        
        } 
    }
    
    void display() {
        
        pushMatrix();
        
        translate((int) location.x + object_Center_At_Origin.x,location.y + (int) object_Center_At_Origin.y);
        
        rotate(radians(angle));
        
        shape.setFill(shape_Color);
        
        shape(shape, center_Of_Rotation.x, center_Of_Rotation.y);
        
        popMatrix();  
    }
}

