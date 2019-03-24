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
 * Servlet implementation class RestaurantAddCouponServlet
 */
@WebServlet("/RestaurantAddCouponServlet")
public class RestaurantAddCouponServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	OrderService orderService=new OrderServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantAddCouponServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Restaurant Add couponing....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String id = request.getParameter("id");
		String couponName = request.getParameter("couponName");
		String minPayment = request.getParameter("minPayment");
		String discount = request.getParameter("id");
		String level = request.getParameter("level");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		
		try {
			boolean result=orderService.restaurantAddCoupon(Integer.parseInt(id), couponName, Double.parseDouble(minPayment), Double.parseDouble(discount), Integer.parseInt(level), startTime, endTime);
			
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
