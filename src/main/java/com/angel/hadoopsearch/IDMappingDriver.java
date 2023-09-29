package com.angel.hadoopsearch;

import com.angel.hadoopsearch.mapper.IDMapper;
import com.angel.hadoopsearch.reducer.IDReducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IDMappingDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ID Mapping");
        job.setJarByClass(IDMappingDriver.class);
        job.setMapperClass(IDMapper.class);
        job.setReducerClass(IDReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class); // La salida final sigue siendo IntWritable
        job.setMapOutputKeyClass(Text.class); // Esto es necesario para definir la salida del Mapper
        job.setMapOutputValueClass(Text.class); // Esto es necesario para definir la salida del Mapper
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
