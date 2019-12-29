public class test {

    public static void main(String[] args) {
        Spreadsheet sheet = new Spreadsheet();
        System.out.println(sheet.toString());
        System.out.print(sheet.getWidth());
        System.out.print(" x ");
        System.out.println(sheet.getHeight());
        sheet.addRow();
        System.out.print(sheet.getWidth());
        System.out.print(" x ");
        System.out.println(sheet.getHeight());
        sheet.setCell(0,0,"1");
        sheet.setCell(0,1,"2");
        sheet.setCell(1,0,"3");
        sheet.setCell(1,1,"4");
        System.out.println(sheet.toString());
        sheet.addColumn(1);
        sheet.setCell(1,0, "TEMP");
        sheet.setCell(1,1, "TEMP");
        System.out.println(sheet.toString());
        sheet.removeRow(1);
        System.out.println(sheet.toString());
        sheet.removeColumn(1);
        System.out.println(sheet.toString()); }
}
