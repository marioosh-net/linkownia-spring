package net.marioosh.spring.springonly;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.dao.SearchDAO;
import net.marioosh.spring.springonly.model.dao.TagDAO;
import net.marioosh.spring.springonly.model.dao.UserDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.entities.User;
import net.marioosh.spring.springonly.model.entities.User.ListMode;
import net.marioosh.spring.springonly.model.entities.User.UserRole;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.Range;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;
import net.marioosh.spring.springonly.model.helpers.TagBrowseParams;
import net.marioosh.spring.springonly.utils.WebUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class AjaxFormsController {

	private Logger log = Logger.getLogger(AjaxFormsController.class);

	@Autowired
	private UserDAO userDAO;
	
	/**
	 * zarejestrowanie innego validatora
	 * @param binder
	 */

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
    	dateFormat.setLenient(false);
    	// true passed to CustomDateEditor constructor means convert empty String to null
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	@RequestMapping(value = "/register.html")
	public String register(@Valid @ModelAttribute("user1") User user, BindingResult result, HttpServletRequest req, Model model) {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			User u = new User();
			model.addAttribute("user1", new User());
		} else {
			log.info(result.hasErrors());
			if(!result.hasErrors()) {
				log.info(user);
				userDAO.add(user);
				return "redirect:/register.html?done=1";
			} else {
				model.addAttribute("errors", result.getAllErrors());
				model.addAttribute("someRegisterErrors", true);
			}
		}
		return "registerform";
	}

	@RequestMapping(value = "/login.html")
	public String loginHandler(Model model) {
		return "login";
	}
	
	/**
	 * @param ex
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex, HttpServletResponse response) throws IOException {
		response.getWriter().print("Error: "+ex.getMessage());
		log.error(ex.getMessage(), ex);
	}
	
}
