package br.com.process.uteis;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Icaro
 */
public class Datas {

    public static Date getData() throws Exception {
        try {
            Date date = Date.valueOf(LocalDate.now());
            return date;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
