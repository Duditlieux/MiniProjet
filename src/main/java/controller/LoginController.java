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
import model.Commande;
import model.DAO;
import model.DataSourceFactory;
import model.Panier;
import model.Product;

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
                                case "update":
                                    updateClient(request);
                                    break;
                                case "accueil":
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                    break;
                                case "profil":
                                    request.getRequestDispatcher("client.jsp").forward(request, response);
                                    break;
                                case "ajouter":
                                    ajouterPanier(request);
                                    request.getRequestDispatcher("panier.jsp").forward(request, response);
                                    break;
                                case "retrait":
                                    retraitPanier(request);
                                    request.getRequestDispatcher("panier.jsp").forward(request, response);
                                    break;
                                case "panier":
                                    request.getRequestDispatcher("panier.jsp").forward(request, response);
                                    break;
                                case "commander":
                                    commande(request);
                                    request.getRequestDispatcher("commandes.jsp").forward(request, response);
                                    break;
                                case "commandes":
                                    request.getRequestDispatcher("commandes.jsp").forward(request, response);
                                    break;
                                case "Supprimer":
                                    prodSuppr(request);
                                    request.getRequestDispatcher("admin.jsp").forward(request,response);
                                    break;
                                case "Editer":
                                    HttpSession session = request.getSession(false);
                                    if (session != null) {
                                            session.setAttribute("refEdit", request.getParameter("ref"));
                                    }
                                    request.getRequestDispatcher("produit.jsp").forward(request,response);
                                    break;
                                case "Modifier":
                                    prodEdit(request);
                                    request.getRequestDispatcher("admin.jsp").forward(request,response);
                                    break;
                                case "Nouveau":
                                    request.getRequestDispatcher("addProduit.jsp").forward(request,response);
                                    break;
                                case "CreerP":
                                    creerP(request);
                                    request.getRequestDispatcher("admin.jsp").forward(request,response);
                                    break;
			}
		}
                //request.getRequestDispatcher("index.jsp").forward(request, response);


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
                            session.setAttribute("panier", new Panier());
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
        
        private void updateClient(HttpServletRequest request){
            String code = request.getParameter("code");
            String societe = request.getParameter("m_societe");
            String contact = request.getParameter("m_contact");
            String fonction = request.getParameter("m_fonction");
            String adresse = request.getParameter("m_adresse");
            String ville = request.getParameter("m_ville");
            String region = request.getParameter("m_region");
            String codePostal = request.getParameter("m_codePostal");
            String pays = request.getParameter("m_pays");
            String telephone = request.getParameter("m_telephone");
            String fax = request.getParameter("m_fax");
            Client c = new Client(code, societe, contact, fonction, adresse, ville, region, codePostal, pays, telephone, fax);
            try {
                dao.updateClient(c);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void ajouterPanier(HttpServletRequest request){
            String ref = request.getParameter("idP");
            Panier panier = null;
            HttpSession session = request.getSession(false);
		if (session != null) {
			panier = (Panier) session.getAttribute("panier");
		}
            Product pr;
        try {
            Integer refId = Integer.parseInt(ref);
            pr = dao.getProduct(refId);
            if (panier!=null){
                panier.ajout(pr);
            }
            if (session!=null){
                session.setAttribute("panier", panier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
        private void retraitPanier(HttpServletRequest request){
            String ref = request.getParameter("idP");
            Panier panier = null;
            HttpSession session = request.getSession(false);
		if (session != null) {
			panier = (Panier) session.getAttribute("panier");
		}
            Product pr;
        try {
            Integer refId = Integer.parseInt(ref);
            pr = dao.getProduct(refId);
            if (panier!=null){
                panier.reduireQte(pr);
            }
            if (session!=null){
                session.setAttribute("panier", panier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
        private void commande(HttpServletRequest request){
            HttpSession session = request.getSession(false);
		if (session != null) {
                    try {
                        Panier panier = (Panier) session.getAttribute("panier");
                        Client client = dao.getClient(findCodeInSession(request));
                        Commande commande = new Commande(client, 0, 0);
                        dao.addCommande(commande, panier);
                        session.setAttribute("panier", new Panier());
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}    
        }

    private void prodSuppr(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        Integer ref = Integer.parseInt(request.getParameter("ref"));
        if (session != null){
            try {
                dao.delProduct(ref);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }
    
    private void prodEdit(HttpServletRequest request) {
        HttpSession session = request.getSession(false);    
        Integer ref = Integer.parseInt(request.getParameter("code"));
        String nom = (String) request.getParameter("m_nom");
        Integer fournisseur = Integer.parseInt(request.getParameter("m_fournisseur"));
        Integer cat = Integer.parseInt(request.getParameter("m_categorie"));
        String qtePU = request.getParameter("m_quantiteParUnite");
        float pu = Float.parseFloat(request.getParameter("m_prixUnitaire"));
        Integer ustk = Integer.parseInt(request.getParameter("m_uniteEnStock"));
        Integer ucom = Integer.parseInt(request.getParameter("m_uniteCommandees"));
        Integer nivreap = Integer.parseInt(request.getParameter("m_niveauDeReapprovisionnement"));
        boolean indisp = request.getParameter("m_indisponible").equals("true");
        if (session != null){
            try {
                Product pr = new Product(ref, nom, fournisseur, cat, qtePU, pu, ustk, ucom, nivreap, indisp);
                dao.updateProduct(pr);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }
    
    private void creerP(HttpServletRequest request) {
        HttpSession session = request.getSession(false);    
        Integer ref = Integer.parseInt(request.getParameter("code"));
        String nom = (String) request.getParameter("m_nom");
        Integer fournisseur = Integer.parseInt(request.getParameter("m_fournisseur"));
        Integer cat = Integer.parseInt(request.getParameter("m_categorie"));
        String qtePU = request.getParameter("m_quantiteParUnite");
        float pu = Float.parseFloat(request.getParameter("m_prixUnitaire"));
        Integer ustk = Integer.parseInt(request.getParameter("m_uniteEnStock"));
        Integer ucom = Integer.parseInt(request.getParameter("m_uniteCommandees"));
        Integer nivreap = Integer.parseInt(request.getParameter("m_niveauDeReapprovisionnement"));
        boolean indisp = request.getParameter("m_indisponible").equals("true");
        if (session != null){
            try {
                Product pr = new Product(ref, nom, fournisseur, cat, qtePU, pu, ustk, ucom, nivreap, indisp);
                dao.addProduct(pr);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }

}
