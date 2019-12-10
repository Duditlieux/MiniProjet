package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Client;
import model.DAO;
import model.DataSourceFactory;

public class LoginController extends HttpServlet {
    
    private DAO dao;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
                dao = new DAO(DataSourceFactory.getDataSource());
            
            
		// Quelle action a appelé cette servlet ?
		String action = request.getParameter("action");
		if (null != action) {
			switch (action) {
				case "login":
					checkLogin(request);
					break;
				case "logout":
					doLogout(request);
					break;
			}
		}

		// Est-ce que l'utilisateur est connecté ?
		// On cherche l'attribut userName dans la session
		String userName = findUserInSession(request);
                String code = findCodeInSession(request);
		String jspView;
		if (null == userName) { // L'utilisateur n'est pas connecté
			// On choisit la page de login
			jspView = "index.jsp";
                        //jspView = "index.html";

		} else if (userName.equals("Administrateur")){ // L'admin est connecté
			// On choisit la page d'affichage
			jspView = "admin.jsp";
                        //jspView = "admin.html";
		} else {
                    // L'utilisateur est connecté
                    // On choisit la page d'affichage
                    jspView = "client.jsp";
                    //jspView = "client.html";
                }
		// On va vers la page choisie
		request.getRequestDispatcher(jspView).forward(request, response);

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	private void checkLogin(HttpServletRequest request) {
		// Les paramètres transmis dans la requête
		String loginParam = request.getParameter("loginParam");
		String passwordParam = request.getParameter("passwordParam");

		// Le login/password défini dans web.xml
		String login = getInitParameter("login");
		String password = getInitParameter("password");
		String userName = getInitParameter("userName");

		if ((login.equals(loginParam) && (password.equals(passwordParam)))) {
			// On a trouvé la combinaison login / password de l'admin
			// On stocke l'information dans la session
			HttpSession session = request.getSession(true); // démarre la session
			session.setAttribute("userName", userName);
		} else {
                        Client c = null;
                    try {
                        c = dao.getClient(loginParam,passwordParam);
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        if (c!=null){
                            // On a trouvé la combinaison login / password d'un client
                            // On stocke l'information dans la session
                            HttpSession session = request.getSession(true); // démarre la session
                            System.out.println(c.getContact());
                            session.setAttribute("userName", c.getContact());
                            session.setAttribute("code", c.getCode());
                            //session.setAttribute("client", c);
                        }else { // On positionne un message d'erreur pour l'afficher dans la JSP
			request.setAttribute("errorMessage", "Login/Password incorrect");
		}
                    
                }
                
	}

	private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
	}
        
        private String findCodeInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("code");
	}

}
