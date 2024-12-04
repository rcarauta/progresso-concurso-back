package br.co.progresso.concurso.convert;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataConverter {

    public Date converterStringParaDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return formatter.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String converterDateParaString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

}
