package com.angel.hadoopsearch.mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.net.URI;

public class TokenizerMapper extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();
    private Text fileID = new Text();
    private Map<String, String> fileToID = new HashMap<>();

    private final Pattern WORD_PATTERN = Pattern
            .compile("(https?://[^\\s]+)|([\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6})|(\\b[\\w']+\\b)");

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            try {
                // Accede al archivo cacheado
                Path cachePath = new Path(cacheFiles[0].getPath());
                String cacheFileName = cachePath.getName();

                // Verifica la ubicación del archivo cachead

                // Configura un lector para el archivo cacheado
                BufferedReader reader = new BufferedReader(new FileReader(cacheFileName));

                String line;

                // Lee y procesa cada línea del archivo cacheado
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("\\t");
                    if (tokens.length == 2) {
                        // Almacena el mapeo de nombres de archivo a IDs
                        fileToID.put(tokens[0], tokens[1]);
                    }
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        Matcher matcher = WORD_PATTERN.matcher(value.toString());
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fullPath = fileSplit.getPath().toString();
        String name = new Path(fullPath).getName();
        String id = fileToID.get(name);

        if (id == null) {
            System.err.println("File ID not found for: " + name);
            throw new IOException("File ID not found for: " + name);
        }

        fileID.set(id);

        while (matcher.find()) {
            word.set(matcher.group());
            context.write(word, fileID);
        }
    }
}
