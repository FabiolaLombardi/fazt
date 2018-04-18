package fagss.org.srv;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import fagss.org.facade.QueryFacade;

/**
 * Servlet implementation class RemoveVideo
 */
@WebServlet("/RemoveVideo")
public class RemoveVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveVideo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject res = null;
		int mediaId = Integer.parseInt(request.getParameter("id"));
		
		if (!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			QueryFacade facade = new QueryFacade();
			String mediaUrl = facade.getVideo(mediaId);
			System.out.println(mediaUrl);
			if (userData.getInt("typeUser") == 3) {
				res = facade.removeVideo(mediaId);
				deleteMediaFromDirectory(mediaUrl);
				res.put("status", 200).put("msg", "VIDEO ELIMINADO");
			} else if (userData.getInt("typeUser") == 2) {
				JSONObject json = new JSONObject();
				json.put("id_user", userData.getInt("id")).put("media_id", mediaId);
				if (facade.isUserVideo(json)) {
					res = facade.removeVideo(mediaId);
					
					deleteMediaFromDirectory(mediaUrl);
					res.put("status", 200).put("msg", "VIDEO ELIMINADO");
				} else {
					res = new JSONObject();
					res.put("status", 403).put("msg", "Usuario no posee suficientes requerimientos para ejecutar �sta acci�n");
				}
			}
		} else {
			res = new JSONObject();
			res.put("status", 500).put("msg", "Debe poseer una sesi�n para �sta opci�n");
			session.invalidate();
		}
		out.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void deleteMediaFromDirectory(String url) {
		File file = new File(url);
		if (file.delete()) {
			System.out.println("Video:" + url + " ha sido eliminado");
		} else {
			System.out.println("Error al eliminar el archivo");
		}
	}

}
