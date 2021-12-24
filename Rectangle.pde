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
