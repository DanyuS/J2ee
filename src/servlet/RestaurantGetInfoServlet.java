package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Restaurant;
import service.ModifyService;
import serviceImpl.ModifyServiceImpl;

/**
 * Servlet implementation class RestaurantGetInfoServlet
 */
@WebServlet("/RestaurantGetInfoServlet")
public class RestaurantGetInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ModifyService modifyService=new ModifyServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantGetInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Get restaurant------");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String rid=request.getParameter("rid");
		//Task task=null;
		Restaurant r=new Restaurant();
		try {
			r=modifyService.findRestaurantByRid(rid);
			
			PrintWriter write=response.getWriter();
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String gs="";
			try{
				gs = gson.toJson(r);
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
