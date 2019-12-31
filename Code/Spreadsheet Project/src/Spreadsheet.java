import java.util.LinkedList;

public class Spreadsheet {
    LinkedList data = new LinkedList();

    public Spreadsheet() {
        this.addRow();
        this.addColumn();
    }
}
