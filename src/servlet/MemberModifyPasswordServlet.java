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
 * Servlet implementation class MemberModifyPasswordServlet
 */
@WebServlet("/MemberModifyPasswordServlet")
public class MemberModifyPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	ModifyService modifyService=new ModifyServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberModifyPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String mid = request.getParameter("mid");
		String password = request.getParameter("password");
		boolean b = false;
		try {
			b=modifyService.changeMemberPassword(Integer.parseInt(mid), password);
			
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
