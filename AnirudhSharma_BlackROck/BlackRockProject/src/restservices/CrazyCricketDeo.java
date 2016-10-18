package restservices;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import consumer.CrazyCricketObject;
import consumer.CrazyCricketProtos;

public class CrazyCricketDeo {


	public List<CrazyCricket> leaderBoard() {

		List<CrazyCricket> list=new ArrayList<>();
		Properties properties=new Properties();
		String propFileName="config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try{
			properties.load(inputStream);
			String filePath=properties.getProperty("filePath");
			FileInputStream fileInputStream=new FileInputStream(filePath);
			ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
			CrazyCricketObject object=null;
			while ((object=(CrazyCricketObject)objectInputStream.readObject())!=null) {
				CrazyCricketProtos.Game game=object.getGame();
				CrazyCricketProtos.Player player= game.getWinner();

				CrazyCricket cricket=new CrazyCricket(player.getUserId(), player.getCountry());
				list.add(cricket);
			}

			objectInputStream.close();

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}	

	public List<CrazyCricket> leaderBoardWithDate(long startDate,long endDate) {
		List<CrazyCricket> list=new ArrayList<>();
		Properties properties=new Properties();
		String propFileName="config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try{
			properties.load(inputStream);
			String filePath=properties.getProperty("filePath");
			FileInputStream fileInputStream=new FileInputStream(filePath);
			ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
			CrazyCricketObject object=null;
			while ((object=(CrazyCricketObject)objectInputStream.readObject())!=null) {
				CrazyCricketProtos.Game game=object.getGame();

				if (game.getGameDate()>=startDate && game.getGameDate()<=endDate) {
					CrazyCricketProtos.Player player= game.getWinner();

					CrazyCricket cricket=new CrazyCricket(player.getUserId(), player.getCountry());
					list.add(cricket);
				}

			}

			objectInputStream.close();

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;


	}

	public List<CrazyCricket> nationaBoard() {
		List<CrazyCricket> crList=new ArrayList<>();
		Map<String, String> playerCountry=new HashMap<>();
		Set<String> countryNames=new HashSet<>();
		Map<String, Integer> playerMap=new HashMap<>();

		List<CrazyCricket> list=new ArrayList<>();
		Properties properties=new Properties();
		String propFileName="config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try{
			properties.load(inputStream);
			String filePath=properties.getProperty("filePath");
			FileInputStream fileInputStream=new FileInputStream(filePath);
			ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
			CrazyCricketObject object=null;
			while ((object=(CrazyCricketObject)objectInputStream.readObject())!=null) {
				CrazyCricketProtos.Game game=object.getGame();
				CrazyCricketProtos.Player player= game.getWinner();

				countryNames.add(player.getCountry());
				String userId=player.getUserId();
				CrazyCricket cricket=new CrazyCricket(player.getUserId(), player.getCountry());
				crList.add(cricket);
				playerCountry.put(userId, player.getCountry());
				if (!playerMap.isEmpty()) {
					if (playerMap.containsKey(userId)) {
						int val=playerMap.get(userId);
						playerMap.put(userId, val+1);
					}
					else{
						playerMap.put(userId, 1);
					}
				}				
			}






			objectInputStream.close();

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;


	}

	public List<CrazyCricket> nationaBoardwithDate() {
		return new ArrayList<CrazyCricket>();
	}
}


