package consumer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;





public class ProtoConsumer {

	public static String address;

	public static void main(String[] argv)throws Exception{
		address=argv[0];

		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter topic name");
		String topicName=bufferedReader.readLine();

		System.out.println("Enter group ID:");

		String groupId = bufferedReader.readLine();

		ConsumerThread consumerRunnable = new ConsumerThread(topicName,groupId);
		consumerRunnable.start();

		System.out.println("Stopping consumer .....");
		consumerRunnable.join();
	}

	private static class ConsumerThread extends Thread{
		private String topicName;
		private String groupId;
		private KafkaConsumer<String,byte[]> kafkaConsumer;

		public ConsumerThread(String topicName, String groupId){
			this.topicName = topicName;
			this.groupId = groupId;
		}
		public void run() {
			Properties configProperties = new Properties();
			configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
			configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
			configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.bytearraydeserializer");
			configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
			configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

			//Figure out where to start processing messages from
			kafkaConsumer = new KafkaConsumer<String, byte[]>(configProperties);
			kafkaConsumer.subscribe(Arrays.asList(topicName));

			ObjectOutputStream objectOutputStream=null;

			Properties properties=new Properties();
			String propFileName="config.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			try {
				properties.load(inputStream);
				String filePath=properties.getProperty("filePath");

				FileOutputStream outputStream=new FileOutputStream(filePath);
				objectOutputStream=new ObjectOutputStream(outputStream);

				//Start processing messages

				ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(100);
				for (ConsumerRecord<String, byte[]> record : records){

					CrazyCricketProtos.Game crazyCricketGame=CrazyCricketProtos.Game.parseFrom(record.value());
					CrazyCricketProtos.Player crazyCricketPlayer=CrazyCricketProtos.Player.parseFrom(record.value());

					CrazyCricketObject crazyCricketObject=new CrazyCricketObject();
					crazyCricketObject.setPlayer(crazyCricketPlayer);
					crazyCricketObject.setGame(crazyCricketGame);

					objectOutputStream.writeObject(crazyCricketObject);                    	

				}
				objectOutputStream.close();
			}      
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{

				kafkaConsumer.close();
				System.out.println("After closing KafkaConsumer");
			}
		}

	}
}