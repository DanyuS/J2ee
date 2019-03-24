package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ApproveService;
import serviceImpl.ApproveServiceImpl;

/**
 * Servlet implementation class ManagerAllPermitApprovalServlet
 */
@WebServlet("/ManagerAllPermitApprovalServlet")
public class ManagerAllPermitApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ApproveService approveService=new ApproveServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerAllPermitApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("all permit----");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");
		boolean b = false;
		try {
			b=approveService.allPermitApproval();
			
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
