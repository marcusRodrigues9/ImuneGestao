package com.imunegestao.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Este é o nosso "tradutor" customizado que ensina o Gson a converter
 * objetos LocalDate para texto (e vice-versa).
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    // Define o formato padrão para salvar as datas como texto: "AAAA-MM-DD"
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
        if (localDate == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(formatter.format(localDate));
        }
    }

    @Override
    public LocalDate read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return LocalDate.parse(jsonReader.nextString(), formatter);
        }
    }}
