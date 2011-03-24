package net.marioosh.spring.springonly;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.dao.SearchDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.Range;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopsController {

	private Logger log = Logger.getLogger(TopsController.class);

	@Autowired
	private SearchDAO searchDAO;
	
	@Autowired
	private LinkDAO linkDAO;
	
	/*
	@ModelAttribute("searches")
	public List<Search> searches() {
		SearchBrowseParams b = new SearchBrowseParams();
		b.setRange(new Range(0,30));
		b.setSort("counter desc");
		return searchDAO.findAll(b);
	}
	*/
	
	/*
	@ModelAttribute("toplinks")
	public List<Link> populateTopLinks() {
		BrowseParams b = new BrowseParams();
		b.setRange(new Range(0,10));
		b.setSort("clicks desc");
		return linkDAO.findAll(b);
	}
	*/

	@RequestMapping(value = "/searches.html")
	public ModelAndView searches() {
		SearchBrowseParams b = new SearchBrowseParams();
		b.setRange(new Range(0,30));
		b.setSort("counter desc");
		return new ModelAndView("searches", "searches", searchDAO.findAll(b));
	}

	@RequestMapping(value="/toplinks.html")
	public ModelAndView toplinks() {
		BrowseParams b = new BrowseParams();
		b.setRange(new Range(0,10));
		b.setSort("clicks desc");
		return new ModelAndView("toplinks", "toplinks", linkDAO.findAll(b));
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
