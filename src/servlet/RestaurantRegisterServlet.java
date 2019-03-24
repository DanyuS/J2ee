package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.CodeUtil;
import service.RegisterService;
import serviceImpl.RegisterServiceImpl;

/**
 * Servlet implementation class RestaurantRegisterServlet
 */
@WebServlet("/RestaurantRegisterServlet")
public class RestaurantRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RegisterService registerService=new RegisterServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("RegisterIng....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String username = request.getParameter("username");
		System.out.println("username------" + username);
		String password = request.getParameter("password");
		System.out.println("password------" + password);
		
		String code=CodeUtil.generateUniqueCode();
		
		try {
			String result=registerService.registerByRestaurant(username, password, code.substring(0,7));
			System.out.println("--------------------"+code.substring(0,7));
			PrintWriter write=response.getWriter();
			write.write("{\"data\":\""+code.substring(0,7)+"\"}");
			write.close();
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
