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
 * Servlet implementation class MemberAddToCurrentOrderWithoutPayServlet
 */
@WebServlet("/MemberAddToCurrentOrderWithoutPayServlet")
public class MemberAddToCurrentOrderWithoutPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	OrderService orderService=new OrderServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAddToCurrentOrderWithoutPayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Add to ordering with out pay....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String mid = request.getParameter("mid");
		String addrId = request.getParameter("addrId");
		String actualPrice = request.getParameter("actualPrice");
		
		try {
			boolean result=orderService.addToCurrentOrderWithoutPay(Integer.parseInt(mid), addrId, Double.parseDouble(actualPrice));
			System.out.println("--------------------"+result);
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
