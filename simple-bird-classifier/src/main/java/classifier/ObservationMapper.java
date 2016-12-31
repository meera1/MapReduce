package classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import com.opencsv.CSVParser;


public class ObservationMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

	HashMap<String, Double> region = new HashMap<String, Double>();
	String fileName = "";
	MultipleOutputs<Text, NullWritable> multiOut;
	

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		
		multiOut = new MultipleOutputs<Text, NullWritable>(context);
		fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
		String regionPath = context.getConfiguration().get("region");
		URI uri = URI.create(regionPath);
		FileSystem fs = FileSystem.get(uri,context.getConfiguration());
		FileStatus[] files = fs.listStatus(new Path(regionPath));
		for(int i = 0; i < files.length; i++) {
			BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(files[i].getPath())));
			String line;
			line=br.readLine();
			while (line != null){
				if(!line.trim().equals(""))
					region.put(line.trim(), (double) region.size());
				line=br.readLine();
			}

			br.close();
		}
		
		System.err.println(fileName);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		if(key.get() > 0) {
			CSVParser csvParser = new CSVParser(',');
			String[] values;
			try {
				values = csvParser.parseLine(value.toString());
				Observation observation = new Observation(values);
				observation.setRegion(String.valueOf(region.get(observation.getState()+"_"+observation.getCounty())));
				String s = observation.toString();
				if(fileName.startsWith("un"))
					multiOut.write("test",new Text(observation.getSamplingID()+","+s.trim()),NullWritable.get(), "testingData/part");
				else
					multiOut.write("train",new Text(s),NullWritable.get(), "trainingData/part");
			} catch (IOException  e){		
				System.err.println("Error while parsing");				
			}
		}
	}
	
	@Override
	protected void cleanup(Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {	
		multiOut.close();
	}

}