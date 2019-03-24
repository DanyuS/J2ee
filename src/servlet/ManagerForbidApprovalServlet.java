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
 * Servlet implementation class ManagerForbidApprovalServlet
 */
@WebServlet("/ManagerForbidApprovalServlet")
public class ManagerForbidApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ApproveService approveService=new ApproveServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerForbidApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("forbiding----");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String rid = request.getParameter("rid");
		System.out.println("deleteaddress:  "+rid);
		boolean b = false;
		try {
			b=approveService.forbidApproval(rid);
			
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
