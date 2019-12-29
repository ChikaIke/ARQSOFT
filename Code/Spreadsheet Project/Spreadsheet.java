import java.util.LinkedList;

public class Spreadsheet {

    LinkedList data = new LinkedList();

    public Spreadsheet(){
        addRow();
        addColumn();
    }
    public void addRow(){   //this will add the row at the end of the spreadsheet
        data.addLast(new LinkedList<>());
        for (int x=0; x<getHeight();x++){
            data.getLast();
        }
    }
    public void addRow(int index){
        data.add(index, new LinkedList<>());
        for (int x=0; x<getHeight(); x++){
           // data.get(index).add(new String);
            data.get(index);
            data.add(index,new String());
        }
    }
    public void removeRow(int index){
        data.remove(index);
    }

    public void addColumn() {
      //  LinkedList l= new LinkedList();
        for (int i=0; i<getWidth(); i++) {
            data.addLast(new String()); }
    }

    public void addColumn(int index){
        LinkedList l= data;
        for (int i=0; i<getWidth();i++){
            l.add(index, new String());
        }
    }
    public void removeColumn(int index){
        data.remove(index);
        //for(LinkedList l: data){l.remove(index);}
    }
    public void setCell(int x, int y, String newData){
        data.get(x);
        data.set(y, newData);
    }
    public int getWidth(){
        data.getLast();
        data.size();
        return 0;
        //How to get the width? It should be the index of get last
    }
    public int getHeight(){
        return data.size();
    }
    public String toString(){
        String temp="";
        for (int num=0; num<data.size(); num++){
            String s = "";
            if (s.equals("")){
                s="";
                temp= temp+ s + "";
            }
            temp +="\n";
        }
        return temp;}
}
