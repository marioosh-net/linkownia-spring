package net.marioosh.spring.springonly;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.Range;
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

@Controller
public class LinksController {

	private Logger log = Logger.getLogger(LinksController.class);

	@Autowired
	private LinkDAO linkDAO;
	
	@Autowired
	private Validator validator;
	
	/**
	 * zarejestrowanie innego validatora
	 * @param binder
	 */
	/*
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new LinkValidator());
    }
    */
    
	/**
	 * @ModelAttribute - ustawia wartosc w modelu
	 * 
	 * metoda adnotowana przez @ModelAttribute 
	 * wywoływana jest PRZED metoda handlera (adnotowaną przez @RequestMapping) 
	 */
	/*
	@ModelAttribute("links")
	public List<Link> populateLinks() {
		BrowseParams b = new BrowseParams();
		b.setRange(new Range(0,20));
		return linkDAO.findAll(b);
	}
	*/
	
	@ModelAttribute("toplinks")
	public List<Link> populateTopLinks() {
		BrowseParams b = new BrowseParams();
		b.setRange(new Range(0,10));
		b.setSort("clicks desc");
		return linkDAO.findAll(b);
	}

	@RequestMapping(value = "/index.html")
	public String welcomeHandler(HttpSession session, Model model, @RequestParam(value="p", required=false, defaultValue="1") int p, @CookieValue(value="JSESSIONID", required=false) String cookie) {
		log.debug("JSESSIONID: "+ cookie);
		log.debug("PAGE: "+p);
		log.error("q: "+session.getAttribute("q"));
		
		BrowseParams b = new BrowseParams();
		b.setSearch((String) session.getAttribute("q"));
		b.setRange(new Range(p-1,20));		
		model.addAttribute("links", linkDAO.findAll(b));
		int count = linkDAO.countAll(b);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages(count));
		model.addAttribute("page", p);
		return "links";
	}
	
	@RequestMapping(value="/search.html")
	public String searchByForm(HttpSession session, @RequestParam(value="q", required=false, defaultValue="") String search, @RequestParam(value="p", required=false, defaultValue="1") int p, Model model) {
		model.addAttribute("q", search);
		model.addAttribute("links", linkDAO.findAll(search));
		int count = linkDAO.countAll(search);
		model.addAttribute("count", linkDAO.countAll(search));
		model.addAttribute("pages", pages(count));
		model.addAttribute("page", p);
		session.setAttribute("q", search);
		return "links";		
	}
	
	@RequestMapping(value="/list.html")
	public String list(@RequestParam(value="q", required=false, defaultValue="") String search, @RequestParam(value="p", required=false, defaultValue="1") int p, Model model) {
		model.addAttribute("links", linkDAO.findAll(search));
		int count = linkDAO.countAll(search);
		model.addAttribute("count", linkDAO.countAll(search));
		model.addAttribute("pages", pages(count));
		model.addAttribute("page", p);
		return "list";		
	}
	
	/*
	@RequestMapping(value="/{search}", method = RequestMethod.GET)
	public String search(@PathVariable String search, Model model) {
		// tutaj dostep do atrybutu modelu ustawionego wczesniej w metodzie populateLinks
		// log.debug("XXXXXX"+model.asMap().get("links"));
		
		model.addAttribute("links", linkDAO.findAll(search));
		return "links";
	}
	*/

	@RequestMapping(value="/add.html", method = RequestMethod.POST)
	public String processSubmit(@Valid @ModelAttribute("link") Link link, BindingResult result, SessionStatus status, Model model) {
		if(!result.hasErrors()) {
			link.setLdate(new Date());
			link.setAddress((link.getAddress().startsWith("http://") || link.getAddress().startsWith("https://")) ? link.getAddress() : "http://"+link.getAddress());
			linkDAO.add(link);
			return "redirect:/index.html";
		} else {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("someErrors", true);
			return "links";
		}
	}
	
	// @RequestMapping("/delete/{id}")
	// public String delete(@PathVariable Integer id) {
	@RequestMapping("/delete.html")
	public String delete(@RequestParam(value="id", required=false, defaultValue="-1") Integer id) {
		linkDAO.delete(id);
		return "redirect:/index.html"; 
	}

	@RequestMapping(value = "/open.html")
	public void open(@RequestParam(value="id") Integer id, HttpServletResponse response) {
		Link link = linkDAO.get(id);
		int i = link.getClicks();
		log.debug(link.getAddress());
		linkDAO.click(id);
		// return "redirect:"+link.getAddress();
		// return "links";
		try {
			response.getWriter().print(++i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ha
	 * @param ex
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex, HttpServletResponse response) throws IOException {
		// return ClassUtils.getShortName(ex.getClass());
		response.getWriter().print("Error: "+ex.getMessage());
	}
	
	private int[][] pages(int count) {
		int perPage = 20;
		int pages = count / perPage + (count % perPage == 0 ? 0 : 1);
		int[][] p = new int[pages][3];
		for(int i = 0; i < p.length; i++) {
			p[i][0] = i + 1; 
			p[i][1] = i * perPage; 
			p[i][2] = ((i+1) * perPage) - 1;
		}
		return p;
	}
	
}
