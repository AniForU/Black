package restservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class CrazyCricketServices {

	CrazyCricketDeo deo=new CrazyCricketDeo();

	@GET
	@Path("/leaderboard")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrazyCricket> getLeaderboard(){
		return deo.leaderBoard();
	}

	@GET
	@Path("/leaderboard?start={startDate}&end={endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrazyCricket> getLeaderboardWithDate(@PathParam("startDate") long startDate,@PathParam("endDate") long endDate){
		return deo.leaderBoardWithDate(startDate,endDate);
	}

	@GET
	@Path("/national_leaderboard")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrazyCricket> getNationalLeaderBoard(){
		return deo.nationaBoard();
	}

	@GET
	@Path("/national_leaderboard?start={startDat}&end={endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrazyCricket> getNationalLeaderBoardWithDates(){
		return deo.nationaBoardwithDate();
	}


}
