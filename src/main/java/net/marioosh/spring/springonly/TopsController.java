package net.marioosh.spring.springonly;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import net.marioosh.spring.springonly.model.dao.LinkDAO;
import net.marioosh.spring.springonly.model.dao.SearchDAO;
import net.marioosh.spring.springonly.model.dao.TagDAO;
import net.marioosh.spring.springonly.model.entities.Link;
import net.marioosh.spring.springonly.model.entities.Search;
import net.marioosh.spring.springonly.model.entities.Tag;
import net.marioosh.spring.springonly.model.helpers.BrowseParams;
import net.marioosh.spring.springonly.model.helpers.Range;
import net.marioosh.spring.springonly.model.helpers.SearchBrowseParams;
import net.marioosh.spring.springonly.model.helpers.TagBrowseParams;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@Autowired
	private TagDAO tagDAO;
	
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

	@RequestMapping(value="/toptags.html")
	public ModelAndView tags() {
		TagBrowseParams b = new TagBrowseParams();
		//b.setRange(new Range(0,10));
		b.setSort("tag");
		return new ModelAndView("toptags", "toptags", tagDAO.findAll(b));
	}
	
	@RequestMapping(value="/alltags.html")
	public void tagsString(@RequestParam(value="query", required=false, defaultValue="") String query,  HttpServletResponse response) throws JSONException, IOException {
		TagBrowseParams b = new TagBrowseParams();
		//b.setRange(new Range(0,10));
		b.setSort("tag");
		
		if(!query.equals("")) {
			b.setQuery(query);
		}
		
		List<Tag> l = tagDAO.findAll(b);
		String[] s = new String[l.size()];
		int i = 0;
		for(Tag t: l) {
			s[i++] = t.getTag();
		}
		log.debug(s);
		
		JSONArray json = new JSONArray(s);
		json.write(new BufferedWriter(new OutputStreamWriter(System.out)));
		json.write(response.getWriter());
	}

	
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete-search.html")
	public String delete(@RequestParam(value="id", defaultValue="-1") Integer id) {
		try {
			searchDAO.delete(id);
			return "0";
		} catch (Exception ex) {
			return "-1";
		}
	}
	
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete-tag.html")
	public String deleteTag(@RequestParam(value="id", defaultValue="-1") Integer id) {
		try {
			tagDAO.delete(id);
			return "0";
		} catch (Exception ex) {
			return "-1";
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
	
}
