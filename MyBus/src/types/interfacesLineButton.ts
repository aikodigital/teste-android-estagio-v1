export interface Line {
    c: string;
    lt0: string;
    lt1: string;
  }
  
 export interface LineButtonProps {
    line: Line;
    onPress: (line: Line) => void;
  }