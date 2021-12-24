
class Ui {
    
    // keys for accesing buttons in the buttons ArrayList.
    final int start_Stop = 0; final int about = 1; final int pause_Resume = 2; final int next_Prev = 3;
    
    // Keys for accesing collision mode checkboxes in the checkboxes ArrayList
    final int stop = 0; final int edge_Bounce = 1; final int object_Bounce = 3;
    
    PVector size;
    
    color text_Color = color(210,220,230);
    
    Ui() {
        
        size = new PVector(200, canvas_Height);   
    }
    
    ArrayList<Button> buttons = new ArrayList<Button>();
    
    void create_Button(Function function, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        buttons.add(new Button(function, loc_X, loc_Y, size_X, size_Y));
    }
    
    ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
    
    void create_Checkbox(Category category,Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        checkboxes.add(new CheckBox(category, option, loc_X, loc_Y, size_X, size_Y));
    }
    
    ArrayList<Hoverbox> hoverboxes = new ArrayList<Hoverbox>();
    
    void create_Hoverbox(Category category,Option option, float loc_X, float loc_Y, float size_X, float size_Y) {
        
        hoverboxes.add(new Hoverbox(category, option, loc_X, loc_Y, size_X, size_Y));
    }
    
    void update_Buttons() {
        
        for (Button button : buttons) {
            
            button.display();      
        }
    }
    
    Option edge_Collision_Mode;
    
    Option object_Collision_Mode;
    
    void update_Checkboxes() {
        
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
    
    void update_Hoverboxes() {
        
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
                    
                    create_Text("When objects collide\nthey will reverse their\ndirection and rotational\ndirection."
                        , 18, text_Color, text_X, hoverbox.location.y - 112 + hoverbox.size.y + 20);
                    
                } else if (hoverbox.category == Category.OBJECT && hoverbox.option == Option.IGNORE) {
                    
                    create_Rect(window_X, hoverbox.location.y - 84 + hoverbox.size.y, 194, 84, 50,60,70);
                    
                    create_Text("objects will not react\nto collisions with\nother objects."
                        , 18, text_Color, text_X, hoverbox.location.y - 84 + hoverbox.size.y + 20);
                }
            }
        }
    }   
    
    void create_Text(String text, int text_Size, color text_Color, float loc_X, float loc_Y) {
        
        textSize(text_Size);
        
        fill(text_Color);
        
        text(text, loc_X, loc_Y);
    }
    
    void create_Rect(float loc_X, float loc_Y, float size_X, float size_Y, color rect_Color) {
        
        fill(rect_Color);
        
        rect(loc_X , loc_Y, size_X, size_Y);
    }
    
    void create_Rect(float loc_X, float loc_Y, float size_X, float size_Y, int r, int g, int b) {
        
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
    
    void run() {
        
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
