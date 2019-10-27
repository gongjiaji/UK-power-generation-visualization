package ce201.src;

import com.csvreader.CsvReader;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Csv {
    private CsvReader reader;

    public Csv() throws FileNotFoundException {
        try {
            reader = new CsvReader(ButtonHandler.filepath);
        } catch (IllegalArgumentException e) {
        }
    }

    public void getRows(int n) throws IOException {
        int row = 0;

        try {
            reader.readHeaders();
            while (reader.readRecord() && row < n) {
                row++;
            }
            reader.close();
            System.out.println(row);

        } catch (NullPointerException e) {
        }
    }

    public void rowsForDataSnippet() throws IOException {
        DataSnippet.allData = new ArrayList<>();
        SearchForData.allDataSearch=new ArrayList<>();
        try {
            reader.readHeaders();
            while (reader.readRecord()) {
                DataSnippet.allData.add(reader.getRawRecord());
            }
            reader.close();
        } catch (NullPointerException e) {
        }
    }

}
