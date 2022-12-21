import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFile {
    public static void main(String args[]) throws IOException {
        try{
            boolean virgula = false;
            File novo = new File("processado_lista.txt");
            if(novo.createNewFile())
                System.out.println("Ficheiro criado.");
            else
                System.out.println("Ficheiro já existe");
            FileWriter writeTo = new FileWriter("processado_lista.txt");

            File file = new File("/home/ruizinho/Desktop/lista.xlsx");   /* Ficheiro cujos dados serão importados */
            FileInputStream fis = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            int num = wb.getNumberOfSheets();
            int id = 1;
            for(int inc = 0; inc < num; inc++) {
                XSSFSheet sheet = wb.getSheetAt(inc);   /* Página do excel que está a ser analisada */
                Iterator<Row> itr = sheet.iterator();    /* Iteração das linhas */
                Row row = itr.next(); /* Para não ser captada a primeira linha */
                while (itr.hasNext()) {
                    row = itr.next();
                    Iterator<Cell> cellIterator = row.cellIterator();   /* Iteração das colunas */
                    System.out.print("paragem(" + id + ",");
                    writeTo.write("paragem(" + (id++) + ",");
                    while (cellIterator.hasNext()) {
                        if (virgula) {
                            writeTo.write(",");
                            System.out.print(",");
                        }
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:    /* Campo que representa o uma célula do tipo int */
                                System.out.print(cell.getNumericCellValue());
                                writeTo.write(cell.getNumericCellValue() + "");
                                break;
                            case Cell.CELL_TYPE_STRING:    /* Campo que representa o uma célula do tipo string */
                                String s = cell.getStringCellValue().replaceAll("\\s+", "_");
                                String a = s.toLowerCase();
                                if (a.charAt(0) != '-')
                                    a = a.replaceAll("-", "_");
                                a = a.replaceAll(",", "_");
                                a = a.replaceAll("r\\.", "r");
                                System.out.print(a);
                                writeTo.write(a);
                                break;
                            default:
                        }
                        virgula = true;
                    }
                    writeTo.write(").\n");
                    System.out.println(").");
                    virgula = false;
                }
            }
            writeTo.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
}
}
