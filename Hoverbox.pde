class Hoverbox {
    
    PVector size;
    
    PVector location;
    
    color box_Color;
    
    color hovered_Color;
    
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
    
    void check_Mouse() {
        
        if (active) {
            
            if (mouseX > location.x && mouseX < size.x + location.x 
                && mouseY > location.y && mouseY < size.y + location.y) {hovered = true;
                
            }  else {hovered = false;}   
        } 
    } 
    
    void update() {
        
       check_Mouse();
       
       if (active) {
       
        if (hovered) {fill(hovered_Color);
            
        } else {fill(box_Color);}
        
        rect(location.x, location.y, size.x, size.y);
    
       } 
    }
}
