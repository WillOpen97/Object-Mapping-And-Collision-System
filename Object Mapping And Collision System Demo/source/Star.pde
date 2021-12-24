class Star extends Shape_Object{
    
    PShape star(float x, float y, float radius1, float radius2, int n_points) {
        
        float angle = TWO_PI / n_points;
        
        float halfAngle = angle / 2.0;
        
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
