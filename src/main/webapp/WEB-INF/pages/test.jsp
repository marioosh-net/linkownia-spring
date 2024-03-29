<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
 
<html xmlns="http://www.w3.org/1999/xhtml"> 
 
<head> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
<title>CSS Centering: Auto-width Margins</title> 
<style type="text/css" media="screen"> 
@import "general.css"; /* Mostly just text styling. */
 
body {
	margin:50px 0px; padding:0px; /* Need to set body margin and padding to get consistency between browsers. */
	text-align:center; /* Hack for IE5/Win */
	}
	
#Content {
	width:500px;
	margin:0px auto; /* Right and left margin widths set to "auto" */
	text-align:left; /* Counteract to IE5/Win Hack */
	padding:15px;
	border:1px dashed #333;
	background-color:#eee;
	}
</style> 
</head> 
 
<body> 
 
<div id="Content"> 
	<h1>Centering: Auto-width Margins</h1> 
	<p>This box is horizontally centered by setting its right and left margin widths to "auto". This is the preferred way to accomplish horizontal centering with CSS, and works very well in most browsers with CSS2 support. Unfortunately, IE5/Win does not respond to this method - a shortcoming of that browser, not the technique.</p> 
	<p>There is a simple <em>workaround</em>. (A pause while you fight back the nausea induced by that word.) Ready? IE5/Win incorrectly applies the CSS "text-align" attribute to block-level elements. Declaring "text-align:center" for the containing block-level element (often the BODY element) horizontally centers the box in IE5/Win. There is a side effect of this workaround: the CSS "text-align" attribute is inherited, centering inline content. It is often necessary to explicitly set the "text-align" attribute for the centered box, counteracting the effects of the IE5/Win workaround. The relevant CSS follows.</p> 
<pre> 
body {
	margin:50px 0px; padding:0px;
	text-align:center;
	}
	
#Content {
	width:500px;
	margin:0px auto;
	text-align:left;
	padding:15px;
	border:1px dashed #333;
	background-color:#eee;
	}
</pre> 
</div> 
 
<!-- BlueRobot was here. --> 
 
</body> 
</html>