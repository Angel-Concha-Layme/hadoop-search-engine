package com.angel.hadoopsearch;

import com.angel.hadoopsearch.mapper.TokenizerMapper;
import com.angel.hadoopsearch.reducer.FileListReducer;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopSearchDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // Agrega la ruta del archivo cacheado en tu configuración

        conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);
        conf.set("mapreduce.map.output.compress", "true");
        conf.set("mapreduce.map.output.compress.codec", "org.apache.hadoop.io.compress.SnappyCodec");

        Job job = Job.getInstance(conf, "hadoop search optimized");
        // Agrega la ruta del archivo cacheado en tu configuración
        job.addCacheFile(new URI("/user/angel/DirIdMapping/part-r-00000"));

        job.setJarByClass(HadoopSearchDriver.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(FileListReducer.class);
        job.setReducerClass(FileListReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
