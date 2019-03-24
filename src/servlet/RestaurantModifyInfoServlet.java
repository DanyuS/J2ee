package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ModifyService;
import serviceImpl.ModifyServiceImpl;

/**
 * Servlet implementation class RestaurantModifyInfoServlet
 */
@WebServlet("/RestaurantModifyInfoServlet")
public class RestaurantModifyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ModifyService modifyService=new ModifyServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantModifyInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("modify restaurantint----------");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String id = request.getParameter("id");
		String rid = request.getParameter("rid");
		String restaurantName = request.getParameter("restaurantName");
		String restaurantPassword = request.getParameter("newRestaurantPassword");
		String address = request.getParameter("address");
		String type = request.getParameter("type");
		String keeper = request.getParameter("keeper");
		String phone = request.getParameter("phone");
		boolean b = false;
		try {
			b=modifyService.modifyRestaurantInfo(Integer.parseInt(id), rid, restaurantName, restaurantPassword, address, type, keeper, phone);
			
			PrintWriter write = response.getWriter();
			write.write("{\"data\":\""+b+"\"}");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
