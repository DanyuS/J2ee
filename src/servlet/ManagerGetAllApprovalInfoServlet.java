package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.ApproveRestaurant;
import service.ApproveService;
import serviceImpl.ApproveServiceImpl;

/**
 * Servlet implementation class ManagerGetAllApprovalInfoServlet
 */
@WebServlet("/ManagerGetAllApprovalInfoServlet")
public class ManagerGetAllApprovalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ApproveService approveService=new ApproveServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerGetAllApprovalInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("approve info------");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String mid=request.getParameter("mid");
		//Task task=null;
		List<ApproveRestaurant> ar=new ArrayList<ApproveRestaurant>();
		try {
			ar=approveService.getAllRestaurantApprovalInfo();
			
			PrintWriter write=response.getWriter();
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String gs="";
			try{
				gs = gson.toJson(ar);
				System.out.println(gs);
			}catch(Exception e){
				e.printStackTrace();
			}
			write.write(gs);
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
