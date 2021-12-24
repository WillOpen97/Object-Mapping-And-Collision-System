class CheckBox extends Button_Class {
    
   Category category; // An enum that sets the category to which the checkbox belongs.
   
    Option option; // An enum that sets the option that this checkbox represents.
    
    boolean checked; // returns true if the checkbox is checked
    
    color checked_Color = color(0,255,0);
    
    color checked__Hovered_Color = color(0,255,0);
    
    color checked_Inactive_Color = color(0,120,0);
    
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
    
    void update() {
        
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
