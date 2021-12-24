
class Object_Manager {
    
    ArrayList<Shape_Object> shapes = new ArrayList<Shape_Object>(); // an Arraylist that stores all the shape objects.
    
    void create_Triangle(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(3, radius, loc_x, loc_y, speed_x, speed_y, angle)); 
        
        shapes.get(shapes.size() - 1).shape_Color = color(70,110,150);
    }
    
    void create_Square(int size_x, int size_y, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Rectangle(size_x, size_y, loc_x, loc_y, speed_x, speed_y, angle));      
    }
    
    void create_Pentagon(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(5, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(120,0,0);
    }
    
    void create_Octagon(float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(8, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(0,80,80);
    }
    
    void create_Polygon(int n_points, float radius, float loc_x, float loc_y, float speed_x, float speed_y, int angle) {
        
        shapes.add(new Polygon(n_points, radius, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(120,0,0);
    }
    
    void create_Star(int n_points, float radius1, float radius2, float loc_x, float loc_y
        , float speed_x, float speed_y, int angle) {
        
        shapes.add(new Star(n_points, radius1, radius2, loc_x, loc_y, speed_x, speed_y, angle));      
        
        shapes.get(shapes.size() - 1).shape_Color = color(80,90,100);
    }
    
    ArrayList<String> collided_Pairs = new ArrayList<String>();

    void collision_Detection(Shape_Object shape_A) {
        
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
    
    void update_Shapes() {
        
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

    void run() {
        
        if (initialized == false) { // The code here runs once when the program is launched or the demo is started.
            
            shapes.clear();
            
            create_Triangle(20, 456, 300, -1.2, 2, 1); 
            
            shapes.get(0).rotation_Speed = -8; shapes.get(0).center_Of_Rotation.y -= 20;
            
            create_Triangle(20, 320, 200, 2.4, -3, 123); 
            
            shapes.get(1).rotation_Speed = 3;
            
            create_Square(24, 24, 600, 200, -1.4, -2.8, 56);
            
            shapes.get(2).rotation_Speed = -4;
            
            create_Square(48, 76, 370, 80, 3.2, -2.4, 236);
            
            shapes.get(3).rotation_Speed = 2;
            
            create_Square(64, 12, 276, 270, 0.8, 2.3, 0);
            
            shapes.get(4).rotation_Speed = 6; shapes.get(4).center_Of_Rotation.x -= 10;
            
            create_Pentagon(24,300,350,1.2,0.8,128);
            
            shapes.get(5).rotation_Speed = 3;
            
            create_Octagon(18,550,450,2,1.7,76);
            
            shapes.get(6).rotation_Speed = -2;
            
            create_Star(3,32, 8,450,370,0,0,14);
            
            shapes.get(7).rotation_Speed = -4;
            
            create_Star(8,24,8,370,322,0.7,0.7,14);
            
            shapes.get(8).rotation_Speed = 2;
            
            initialized = true; 
        }
        
        update_Shapes();   
    }      
}
