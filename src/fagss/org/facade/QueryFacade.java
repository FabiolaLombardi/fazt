package fagss.org.facade;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.db.Media;
import fagss.org.db.UserDAO;

public class QueryFacade {
	
	private UserDAO user;
	private Media media;
	
	public QueryFacade() {
		this.user = new UserDAO();
		this.media = new Media();
	}
	
	public JSONObject addUser(JSONObject json) {
		return this.user.insert(json);
	}
	
	public JSONObject checkUser(JSONObject json) {
		return this.user.check(json);
	}
	
	public JSONObject upload(JSONObject json) {
		return this.media.setMedia(json);
	}
	
	public JSONArray getUserMedia(JSONObject json) {
		return this.media.getUserMedia(json);
	}
	
	public JSONObject getMedia(JSONObject json) {
		return this.media.getMedia(json);
	}

}
