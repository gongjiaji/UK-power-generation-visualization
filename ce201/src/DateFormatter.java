package ce201.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateFormatter extends AbstractFormatter {

    private String format = "yyyy-MM-dd";
    private SimpleDateFormat model = new SimpleDateFormat(format);

    @Override
    public Object stringToValue(String z) throws ParseException {
        return model.parseObject(z);
    }

    @Override
    public String valueToString(Object y) throws ParseException {
        if (y != null) {
            Calendar x = (Calendar) y;
            return model.format(x.getTime());
        }
        return "";
    }

}