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
    
    void display() {
        
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
