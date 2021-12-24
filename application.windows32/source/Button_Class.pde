class Button_Class {
    
    PVector size;
    
    PVector location;
    
    color button_Color;
    
    color pressed_Color;
    
    color hovered_Color;
    
    color inactive_Color;
    
    boolean active;
    
    boolean hidden;
    
    boolean pressed; // Returns true if a button is pressed.
    
    boolean applied; // Returns true if the function of the button has been applied.
    
    boolean hovered; // Returns  true if the mouse is hovering on the button.
    
    
    void check_Mouse() {
        
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
    
    void update() {
        
    }
}   
