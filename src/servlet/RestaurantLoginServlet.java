package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Restaurant;
import net.sf.json.JSONObject;
import service.LoginService;
import serviceImpl.LoginServiceImpl;

/**
 * Servlet implementation class RestaurantLoginServlet
 */
@WebServlet("/RestaurantLoginServlet")
public class RestaurantLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	LoginService loginService=new LoginServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("loginING....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Restaurant restaurant;
		
		try {
			restaurant = loginService.restaurantLogin(username, password);
			PrintWriter write = response.getWriter();
			if (restaurant != null) {

				JSONObject json = JSONObject.fromObject(restaurant);
				String strJson = json.toString();
				System.out.println("member------------"+strJson);
				write.write(strJson);
			} else {
				write.write("{\"data\":\"false\"}");
			}
			write.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
