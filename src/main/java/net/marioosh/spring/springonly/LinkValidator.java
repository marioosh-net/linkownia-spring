package net.marioosh.spring.springonly;

import net.marioosh.spring.springonly.model.entities.Link;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Przykładowy walidator
 * @author marioosh
 *
 */
public class LinkValidator implements Validator {

	private Logger log = Logger.getLogger(getClass());
	
	public boolean supports(Class clazz) {
		return Link.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {
		log.debug("VALIDATE");
		ValidationUtils.rejectIfEmpty(e, "name", "name.address.empty");
		Link l = (Link)obj;
		/**
		 * description musi miec przynajmniej 10 znakow dlugosci
		 */
		if(l.getDescription().isEmpty() || l.getDescription().length() < 10) {
			e.rejectValue("description", "error.description.empty");
		}
	}

}
