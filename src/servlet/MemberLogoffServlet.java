package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.RegisterService;
import serviceImpl.RegisterServiceImpl;

/**
 * Servlet implementation class MemberLogoffServlet
 */
@WebServlet("/MemberLogoffServlet")
public class MemberLogoffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RegisterService registerService=new RegisterServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLogoffServlet() {
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
        String mid=request.getParameter("mid");
		boolean b=false;
		try {
			b=registerService.logoff(Integer.parseInt(mid));
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
