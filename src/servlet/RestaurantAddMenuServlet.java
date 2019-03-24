package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.OrderService;
import serviceImpl.OrderServiceImpl;

/**
 * Servlet implementation class RestaurantAddMenuServlet
 */
@WebServlet("/RestaurantAddMenuServlet")
public class RestaurantAddMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	OrderService orderService=new OrderServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantAddMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Add menuing....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String rid = request.getParameter("rid");
		String name = request.getParameter("name");
		String remarks = request.getParameter("remarks");
		String price=request.getParameter("price");
		String currentPrice = request.getParameter("currentPrice");
		String stock = request.getParameter("stock");
		String type = request.getParameter("type");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		
		try {
			boolean result=orderService.addMenu(rid, name, remarks, Double.parseDouble(price), Double.parseDouble(currentPrice), Integer.parseInt(stock), type, startTime, endTime);
			
			PrintWriter write=response.getWriter();
			write.write("{\"data\":\""+result+"\"}");
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
