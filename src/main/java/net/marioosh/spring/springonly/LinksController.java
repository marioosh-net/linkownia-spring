package net.marioosh.spring.springonly;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	@ModelAttribute("links")
	public List<Link> populateLinks() {
		log.debug("populatePersons()");
		return linkDAO.findAll(null);
	}

	@RequestMapping(value = "/index.html")
	public String welcomeHandler(@CookieValue(value="JSESSIONID", required=false) String cookie) {
		log.debug("JSESSIONID: "+ cookie);
		return "links";
	}
	
	@RequestMapping(value="/search.html", method = RequestMethod.POST)
	public String searchByForm(@RequestParam(value="text") String search, Model model) {
		model.addAttribute("links", linkDAO.findAll(search));
		return "links";		
	}
	
	@RequestMapping(value="/{search}", method = RequestMethod.GET)
	public String search(@PathVariable String search, Model model) {
		// tutaj dostep do atrybutu modelu ustawionego wczesniej w metodzie populateLinks
		log.debug("XXXXXX"+model.asMap().get("links"));
		
		model.addAttribute("links", linkDAO.findAll(search));
		return "links";
	}

	@RequestMapping(value="/add.html", method = RequestMethod.POST)
	public String processSubmit(@Valid @ModelAttribute("link") Link link, BindingResult result, SessionStatus status) {
		if(!result.hasErrors()) {
			link.setLdate(new Date());
			link.setAddress((link.getAddress().startsWith("http://") || link.getAddress().startsWith("https://")) ? link.getAddress() : "http://"+link.getAddress());
			if(link.getName().isEmpty()) {
				String title = pageTitle(link.getAddress());
				log.debug("TITLE:"+title);
				link.setName(title);
			}
			linkDAO.add(link);
			return "redirect:/index.html";
		} else {
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
	
	private String pageTitle(String u) {
		try {
			u = u.startsWith("http://") || u.startsWith("https://")  ? u : "http://"+u;
			// jericho
			Source source = new Source(new URL(u));
			List<Element> titles = source.getAllElements(HTMLElementName.TITLE);
			if (!titles.isEmpty()) {
				Element title = titles.get(0);
				// return new String(title.getContent().getTextExtractor().toString().getBytes(), "ISO-8859-2");
				return title.getTextExtractor().toString();
			}

		} catch (IOException e) {
			return "";
		}
		return "";
	}
	
}
