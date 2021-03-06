package fagss.org.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import fagss.org.db.DBHelper;
import fagss.org.util.PropertiesMap;

/**
 * Servlet implementation class SetDislike
 */
@WebServlet("/SetDislike")
public class SetDislike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetDislike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute("session");
		JSONObject data = new JSONObject();
		data.put("id_user", json.getInt("id")).put("media_id", Integer.parseInt(request.getParameter("id")));
		JSONObject res = new JSONObject();
		int queryRes;
		PropertiesMap prop = PropertiesMap.getInstance();
		
		try {
			DBHelper db = new DBHelper(prop.getValue("DB", "driver"), prop.getValue("DB", "url"), prop.getValue("DB", "user"), prop.getValue("DB", "password"));
			queryRes = db.update(prop.getValue("Queries", "Q13"), data.getInt("media_id"), data.getInt("id_user"));
			if (queryRes == 1) {
				res.put("message", "has hecho dislike");
			} else {
				res.put("message", "no se ha podido insertar");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
