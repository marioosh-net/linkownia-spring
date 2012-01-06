package net.marioosh.spring.springonly;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
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
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.Range;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;
import net.marioosh.spring.springonly.model.helpers.TagBrowseParams;
import net.marioosh.spring.springonly.utils.WebUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LinksController {

	private Logger log = Logger.getLogger(LinksController.class);

	@Autowired
	private LinkDAO linkDAO;
	
	@Autowired
	private TagDAO tagDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SearchDAO searchDAO;
	
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

	private User user;
	
	@ModelAttribute("user")
	public void logged(Principal principal) {
		log.info(principal);
		if(principal != null) {
			UserDetails userDetails = (UserDetails)((Authentication)principal).getPrincipal();
			this.user = userDAO.get(userDetails.getUsername());
		} else {
			this.user = null;
		}
		log.info("----- USER: "+ user);
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @ModelAttribute - ustawia wartosc w modelu
	 * 
	 * metoda adnotowana przez @ModelAttribute 
	 * wywoływana jest PRZED metoda handlera (adnotowaną przez @RequestMapping) 
	 */
	@ModelAttribute("links")
	public List<Link> populateLinks(Model model, HttpServletRequest request, 
			@RequestParam(value="q", required=false, defaultValue="") String search, 
			@RequestParam(value="qt" +
					"", required=false, defaultValue="") String tag,
			@RequestParam(value="p", required=false, defaultValue="-1") int p, 
			@RequestParam(value="site", defaultValue="0", required=false) int site) throws UnsupportedEncodingException {
		
		log.debug("TAG: "+tag);
		log.debug("SEARCH: "+search);
		log.debug("PAGE  : "+p);
		WebUtils.logRequestInfo(request);

		if(request.getMethod().equals("POST")) {
			if(!search.isEmpty() && p == -1 && site == 0) {
				// zainicjowano wyszukiwanie - zauktualizuje searches
				searchDAO.trigger(search);
			}
		}
		if(p == -1) {
			p = 1;
		}
		
		BrowseParams b = new BrowseParams();
		b.setSearch(search);
		b.setRange(new Range((p-1)*20,20));
		b.setSort("date_mod desc");
		if(user != null) {
			b.setUserId(user.getId());
		}
		
		if(!tag.equals("")) {
			HashSet<Tag> tagi = new HashSet<Tag>();
			Tag t = new Tag();
			t.setTag(tag);
			tagi.add(t);
			b.setTags(tagi);
		}
		
		/*
		b.setPub(true);
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_XXX")) {
				b.setPub(false);
			}
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				b.setPub(null);
			}
		}
		*/
		int count = linkDAO.countAll(b);
		model.addAttribute("count", count);
		int[][] pages = pages(count, 20);
		model.addAttribute("pages", pages);
		model.addAttribute("pagesCount", pages.length);
		model.addAttribute("page", p);
		model.addAttribute("qt", tag);
		model.addAttribute("q", search);
		model.addAttribute("qencoded", URLEncoder.encode(search, "UTF-8"));
		
		List<Link> l = linkDAO.findAll(b);
		
		/** 
		 * sciagnij tagi dla linkow
		 */
		for(Link l1: l) {
			Set<Tag> tags = new HashSet<Tag>();
			TagBrowseParams bp = new TagBrowseParams();
			bp.setLinkId(l1.getId());
			tags.addAll(tagDAO.findAll(bp));
			l1.setTags(tags);
		}
		
		return l;
	}
	
	@RequestMapping(value = "/search.html")
	public String search(@RequestParam(value="q", required=false, defaultValue="") String search) {
		/*
		if(!search.isEmpty()) {
			// zainicjowano wyszukiwanie - zauktualizuje searches
			searchDAO.trigger(search);
		}
		*/
		return "links";
	}

	@RequestMapping(value = "/index.html")
	public String welcomeHandler(@CookieValue(value="JSESSIONID", required=false) String cookie) {
		log.debug("JSESSIONID: "+ cookie);
		return "links";
	}
	
	@RequestMapping(value = "/login.html")
	public String loginHandler(Model model) {
		model.addAttribute("loginInProgress", "1");
		return "links";
	}
	
	@RequestMapping(value="/list.html")
	public String list(@RequestParam(value="q", required=false, defaultValue="") String search, @RequestParam(value="p", required=false, defaultValue="1") int p, Model model, Principal principal) {		
		BrowseParams b = new BrowseParams();
		b.setSearch(search);
		b.setRange(new Range(0,20));
		b.setSort("date_mod desc");
		if(user != null) {
			b.setUserId(user.getId());
		}
		model.addAttribute("links", linkDAO.findAll(b));
		int count = linkDAO.countAll(b);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages(count, 20));
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

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value="/add.html", method = RequestMethod.POST)
	public String processSubmit(@Valid @ModelAttribute("link") LinkForm linkForm, BindingResult result, SessionStatus status, Model model) {
		Link link = linkForm.getLink();
		String[] tags = linkForm.getTags().trim().replaceAll(" ", ",").replaceAll("[\\s]{2,}", " ").split(",");
		Set<String> tags1 = new HashSet<String>();
		for(String tag: tags) {
			if(!tag.equals("") && !tag.equals(",")) {
				tags1.add(tag);
			}
		}
		if(!result.hasErrors()) {
			link.setLdate(new Date());
			link.setAddress((link.getAddress().startsWith("http://") || link.getAddress().startsWith("https://")) ? link.getAddress() : "http://"+link.getAddress());
			Integer linkId = linkDAO.addOrUpdate(link);
			tagDAO.connect(tags1, linkId);			
			return "redirect:/index.html";
		} else {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("someErrors", true);
			return "links";
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value="/save.html", method = RequestMethod.POST)
	public String processSave(@Valid @ModelAttribute("link") LinkForm linkForm, BindingResult result, SessionStatus status, Model model) {
		Link link = linkForm.getLink();
		// String[] tags = linkForm.getTags().replaceAll("[\\s]{2,}", " ").split(" ");
		String[] tags = linkForm.getTags().trim().replaceAll(" ", ",").replaceAll("[\\s]{2,}", " ").split(",");
		log.debug("TAGS:" + tags);
		Set<String> tags1 = new HashSet<String>();
		for(String tag: tags) {
			if(!tag.equals("") && !tag.equals(",")) {
				tags1.add(tag);
			}
		}
		if(!result.hasErrors()) {
			link.setDateMod(new Date());
			link.setAddress((link.getAddress().startsWith("http://") || link.getAddress().startsWith("https://")) ? link.getAddress() : "http://"+link.getAddress());
			linkDAO.update(link);// addOrUpdate(link);
			tagDAO.connect(tags1, link.getId());
			return "redirect:/index.html";
		} else {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("someSaveErrors", true);
			return "links";
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value="/quickadd.html")
	public String quickAdd(@RequestParam(value="url") String address) {
		Link link = new Link();
		link.setLdate(new Date());
		link.setAddress(address);
		linkDAO.addOrUpdate(link);
		return "redirect:/index.html";
	}
	
	// @RequestMapping("/delete/{id}")
	// public String delete(@PathVariable Integer id) {
	/*
	@RequestMapping("/delete.html")
	public String delete(@RequestParam(value="id", required=false, defaultValue="-1") Integer id) {
		linkDAO.delete(id);
		return "redirect:/index.html"; 
	}
	*/
	
	/**
	 * @ResponseBody - Spring converts the returned object to a response body by using an HttpMessageConverter.
	 * to co zwraca metoda jest wysylane jako odpowiedz serwera
	 */
	@ResponseBody
	/**
	 * wykorzystanie Spring Security. Metoda wykona sie jesli jest zalogowany admin
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete.html")
	public String delete(@RequestParam(value="id", defaultValue="-1") Integer id) {
		try {
			linkDAO.delete(id);
			return "0";
		} catch (Exception ex) {
			return "-1";
		}
	}
	
	/**
	 * @throws JSONException 
	 * @RequestBody - parametr metody bedzie zawieral request body
	 */
	@RequestMapping("/edit.html")
	@Secured("ROLE_ADMIN")
	public void edit(@RequestParam(value="id", required=false, defaultValue="-1") Integer id, HttpServletResponse response, @RequestBody String body) throws IOException, JSONException {
		Link link = linkDAO.get(id);
		
		Set<Tag> tags = new HashSet<Tag>();
		TagBrowseParams bp = new TagBrowseParams();
		bp.setLinkId(link.getId());
		tags.addAll(tagDAO.findAll(bp));
		link.setTags(tags);
		
		JSONObject json = new JSONObject(link);
		/*response.setContentType("text/x-json;charset=UTF-8");*/
		json.write(new BufferedWriter(new OutputStreamWriter(System.out)));
		json.write(response.getWriter());
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/refresh.html")
	public void refresh(@RequestParam(value="id", required=false, defaultValue="-1") Integer id, HttpServletResponse response) throws IOException, JSONException {
		Link link = linkDAO.get(id);
		Map<String,String> m = WebUtils.pageInfo(link.getAddress());
		if(m.get("title") != null) {
			link.setName(m.get("title"));
		}
		if(m.get("description") != null) {
			link.setDescription(m.get("description"));
		}
		// link.setDateMod(new Date());
		linkDAO.update(link);
		JSONObject json = new JSONObject(link);
		
		/*response.setContentType("text/x-json;charset=UTF-8");*/
		json.write(new OutputStreamWriter(System.out));
		json.write(response.getWriter());

	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/visibility.html")
	@ResponseBody
	public String visibility(@RequestParam(value="id", required=false, defaultValue="-1") Integer id) {
		Link link = linkDAO.get(id);
		link.setPub(!link.getPub().booleanValue());
		linkDAO.update(link);
		if(link.getPub().booleanValue()) {
			return "1";
		} else {
			return "0";
		}
	}	
	
	@RequestMapping(value = "/open.html")
	public void open(@RequestParam(value="id", defaultValue="-1") Integer id, HttpServletResponse response) throws IOException {
		Link link = linkDAO.get(id);
		int i = -1;
		if(link != null) {
			i = link.getClicks();
			log.debug(link.getAddress());
			linkDAO.click(id);
		}
		response.getWriter().print(++i);
	}
	
	@RequestMapping(value = "/refreshall.html")
	public void refreshAll(HttpServletResponse response) throws IOException {
		for(Link l: linkDAO.findAll(new BrowseParams())) {
			response.getWriter().print("<div>"+l.getAddress()+" ");
			// l.setClicks(l.getClicks() + 1);

			Map<String, String> m = WebUtils.pageInfo(l.getAddress());
			if(m.get("title") != null) {
				l.setName(m.get("title"));
			}
			if(m.get("description") != null) {
				l.setDescription(m.get("description"));
			}

			if(linkDAO.update(l) > 0) {
				response.getWriter().print("UPDATED</div>");
			} else {
				response.getWriter().print("<b>NOT UPDATED</b></div>");
			}
		}
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
	
	private int[][] pages(int count, int perPage) {
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
