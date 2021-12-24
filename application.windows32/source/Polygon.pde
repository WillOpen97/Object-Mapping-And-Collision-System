class Polygon extends Shape_Object {
    
    PShape polygon(float x, float y, float radius, int n_points) {
        
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
