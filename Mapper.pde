
PGraphics mapper_Canvas;

class Mapper {
    
    int mapper_Width;
    
    int mapper_Height;  
    
    // An ArrayList that stores the numbers and coordinates of all the pixels that comprise the object.
    
    ArrayList<ArrayList<Integer>> object_ArrList = new ArrayList<ArrayList<Integer>>(); 
    
    // An Arraylist that stores the values of "object_ArrList" from lowest to highest.
    
    ArrayList<ArrayList<Integer>> object_ArrList_Sorted = new ArrayList<ArrayList<Integer>>(); 
    
    Mapper(int mapper_Width, int mapper_Height) {
        
        this.mapper_Width = mapper_Width;
        
        this.mapper_Height = mapper_Height;
    }
    
    color background_Color = color(20,20,20);
    
    int number_Of_Pixels; // The total amount of pixels that comprise the object.
    
    int number_Of_Parameters = 2; // x and y.
    
    int[] center_Values_at_Origin; // the x and y values of the center of the object when it is placed at origin.
    
    PShape object_Shape;
    
    void map_Object(int angle, float pivot_Point_X, float pivot_Point_Y) {
        
        mapper_Canvas = createGraphics(mapper_Width, mapper_Height);
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            object_ArrList.add(new ArrayList());
            
            object_ArrList_Sorted.add(new ArrayList());
        }
        
        number_Of_Pixels = 0;
        
        mapper_Canvas.beginDraw();
        
        mapper_Canvas.background(background_Color);
        
        mapper_Canvas.loadPixels();

        mapper_Canvas.pushMatrix();
        
        mapper_Canvas.translate((mapper_Canvas.width / 2), mapper_Canvas.height / 2);
        
        mapper_Canvas.rotate(radians(angle));
        
        object_Shape.setFill(false); // The mapper is set to only scan the object's outline.

        mapper_Canvas.shape(object_Shape, pivot_Point_X, pivot_Point_Y);

        mapper_Canvas.popMatrix();
        
        mapper_Canvas.endDraw();
        
        for (int height_Index = 0; height_Index < mapper_Canvas.width; height_Index++) {
            
            for (int width_Index = 0; width_Index < mapper_Canvas.height; width_Index++) {
                
                color pixel_Color = color(mapper_Canvas.get(width_Index,height_Index));
                
                if (pixel_Color != background_Color) { 
                    
                    number_Of_Pixels++;
                    
                    object_ArrList.get(x).add(width_Index);
                    
                    object_ArrList.get(y).add(height_Index);         
                    
                    object_ArrList_Sorted.get(x).add(width_Index);
                    
                    object_ArrList_Sorted.get(y).add(height_Index);
                }  
            }
        }
        
        find_Min_And_Max_Values();
        
        align_Object_To_Upper_Left_Screen();
    }
    
    int[][] value; // An array that stores the minimum and maximum x and y values of the object.
    
    int index_Of_Min_Value;
    
    int index_Of_Max_Value;
    
    void find_Min_And_Max_Values() {
        
        index_Of_Min_Value = 0;
        
        index_Of_Max_Value = number_Of_Pixels - 1;
        
        value = new int[number_Of_Parameters][2];
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            Collections.sort(object_ArrList_Sorted.get(i)); 
            
            value[i][min] = object_ArrList_Sorted.get(i).get(index_Of_Min_Value);
            
            value[i][max] = object_ArrList_Sorted.get(i).get(index_Of_Max_Value);
        } 
    }
    
    void align_Object_To_Upper_Left_Screen() {
        
        int[] temp_Min = new int[number_Of_Parameters];
        
        for (int i = 0; i < number_Of_Parameters; i++) {
            
            for (int j = 0; j < number_Of_Pixels; j++) {
                
                int index_Value = object_ArrList.get(i).get(j);
                
                object_ArrList.get(i).set(j, index_Value - (value[i][min]));
                
                object_ArrList_Sorted.get(i).set(j, index_Value - (value[i][min]));
            }
            
            Collections.sort(object_ArrList_Sorted.get(i)); 
            
            center_Values_at_Origin = new int[2];
            
            temp_Min[i] = value[i][min];
            
            value[i][min] = object_ArrList_Sorted.get(i).get(index_Of_Min_Value);
            
            value[i][max] = object_ArrList_Sorted.get(i).get(index_Of_Max_Value);
            
            center_Values_at_Origin[x] = mapper_Canvas.width / 2 - temp_Min[x];
            
            center_Values_at_Origin[y] = mapper_Canvas.height / 2 - temp_Min[y];
        }          
    }
    
    int[] get_Object_Array(PShape object_Shape, int angle, float pivot_Point_X, float pivot_Point_Y) {
        
        this.object_Shape = object_Shape;
        
        map_Object(angle, pivot_Point_X, pivot_Point_Y);
        
        int[] final_Object_Array = new int[(number_Of_Pixels * 2) + 7];
        
        final_Object_Array[0] = number_Of_Pixels;
        
        final_Object_Array[1] = center_Values_at_Origin[x];
        
        final_Object_Array[2] = center_Values_at_Origin[y];
        
        final_Object_Array[3] = value[x][min];
        
        final_Object_Array[4] = value[x][max];
        
        final_Object_Array[5] = value[y][min];
        
        final_Object_Array[6] = value[y][max];
        
        int i; 
        
        for (i = 7; i < number_Of_Pixels + 7; i++) {
            
            final_Object_Array[i] = object_ArrList.get(x).get(i - 7);
        }
        
        i = 7;
        
        for (int j = number_Of_Pixels + 7; j < (number_Of_Pixels * 2) + 7; j++) {
            
            i++;
            
            final_Object_Array[j] = object_ArrList.get(y).get(i - 8);
        }
        
        object_ArrList.clear();
        
        object_ArrList_Sorted.clear();
        
        return final_Object_Array;  
    }
}
