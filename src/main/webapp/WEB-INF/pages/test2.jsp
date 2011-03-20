<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
 
<html xmlns="http://www.w3.org/1999/xhtml"> 
 
<head> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
<title>CSS Centering: Auto-width Margins</title> 
<style type="text/css" media="screen"> 
@charset "utf-8";
/* CSS Document */
body
    {
        margin:0px;
        padding:0;
        background:#383738;
        font-family:Arial, Helvetica, sans-serif;
        font-size:12px;
        font-weight:normal;
    }
img
    {
        border:0px;
    }
ul,li,ol{
    list-style:none;
    margin:0;
    padding:0;
}
blockquote{margin:0;padding:0;}
a{text-decoration:none;}
a:focus
    {
        outline:0px;
    }
#wraper
    {
        float:left;
        margin:0px;
        width:100%;
    }
#headerblank
    {
        float:left;
        width:100%;
        margin:0px;
        /*padding:0 0 32px 0;*/
        background:url(images/header.png) repeat-x left top;
    }
#header
    {
        float:none;
        width:952px;
        margin:0px auto;
    }
#header ul
    {
        float:left;
        margin:0px;
        padding:0px;
        width:952px;
        display:block;
    }
#header ul li
    {
        float:left;
        margin:0px;
        padding:0px;
        display:block;
        background:url(images/fadeline.png) no-repeat right;
    }
#header ul li a
    {
        float:left;
        padding:11px 43px 0 43px;
        height:29px;
        font-family: Verdana;
        font-size:13px;
        font-weight:normal;
        color: #fff;
        line-height:14px;
        text-decoration:none;
    }
#header ul li a:hover, a.active
    {
        float:left;
        padding:11px 43px 0 43px;
        height:29px;
        font-family: Verdana;
        font-size:13px;
        font-weight:normal;
        color: #fff;
        line-height:14px;
        text-decoration:none;
        background: #206ea6;
    }
#header h1
    {
        float:left;
        width:324px;
        margin:18px 0 0 0;
        padding:0px;
    }
#header h3
    {
        float:left;
        width:628px;
        margin:32px 0 0 0;
        text-align:right;
        font-family: Georgia, "Times New Roman", Times, serif;
        font-size:13px;
        font-weight:bold;
        color:#fff;
        padding:0px;
    }
#header #slideshow
    {
        float:left;
        margin:12px 0 0 0;
        padding:0px;
        background:url(images/slideshowbg.png);
        width:950px;
        border:1px solid #c8e8f3;
        height:262px;
        }
#header #slideshow .sliderleftcontent
    {
        float:left;
        margin:29px 0 0 0;
        padding:0 0 0 40px;
        width:535px;
    }
#header #slideshow .sliderleftcontent h2
    {
        float:left;
        margin:0px;
        padding:0px;
        width:532px;
        font-family: Verdana;
        font-size:30px;
        font-weight:normal;
        color: #fff;
    }
#header #slideshow .sliderleftcontent p
    {
        float:left;
        margin:11px 0 0 0;
        padding:0px;
        width:486px;
        font-family: Verdana;
        font-size:14px;
        line-height:20px;
        font-weight:normal;
        color: #fff;
    }
#header #slideshow .sliderleftcontent a
    {
        float:left;
        margin:23px 0 0 0;
        padding:0 0 0 16px;
        background:url(images/readmoreactive.png) no-repeat;
        height:29px;
        width:97px;
        line-height:27px;
        font-family: Verdana;
        font-weight:bold;
        font-size:12px;
        color: #fff;
        text-decoration:none;
    }
#header #slideshow img
    {
        margin:11px 0 0 0;
        float:left;
    }
#bodycontentblank
    {
        float:left;
        background:#efe5d7;
        width:100%;
        margin:0px;
        padding:0px 0 29px 0;
    }
#bodycontent
    {
        float:none;
        margin:0px auto;
        width:952px;
    }
#bodyleftcontent
    {
        float:left;
        margin:20px 30px 0 0;
        padding:0px;
        width:647px;
    }
#welcomecontent
    {
        float:left;
        margin:0px;
        padding:0 0 27px 0;
        width:647px;
    }
#welcomecontent h2
    {
        float:left;
        margin:0px;
        padding:0px;
        width:647px;
        font-family: Arial;
        font-size:27px;
        font-weight:bold;
        color: #000000;
    }
#welcomecontent p
    {
        float:left;
        margin:5px 0 0 0;
        padding:0px;
        width:647px;
        font-family: Arial;
        font-size:13px;
        font-weight: normal;
        color: #000000;
        line-height:21px;
    }
#welcomecontent p a
    {
        color: #419bcb;
        text-decoration:underline;
        font-size:11px;
    }
#welcomecontent p a:hover
    {
        color: #419bcb;
        text-decoration:none;
        font-size:11px;
    }
#featuredservice
    {
        float:left;
        margin:0px;
        padding:0 0 0 20px;
        background:url(images/featuredserviceemboss.png) no-repeat #f8f2e9;
        width:627px;
    }
#featuredservice h2
    {
        float:left;
        margin:0px;
        padding:0px;
        width:627px;
        background:url(images/featuredservicesheadingbg.png) no-repeat left;
        height:48px;
        font-family: Arial;
        font-size:18px;
        font-weight:normal;
        color: #000000;
        line-height:40px;
    }
#featuredservice ul
    {
        float:left;
        margin:0px;
        padding:0px;
        width:603px;
        display:block;
    }
#featuredservice ul li
    {
        float:left;
        margin:0px;
        padding:0px;
        width:603px;
        display:block;
    }
#featuredservice ul li.featuredservices
    {
        float:left;
        margin:0px;
        padding:0 0 20px 0;
        width:603px;
        display:block;
        background:url(images/bottomlines.png) repeat-x bottom
    }
#featuredservice ul li.last
    {
        padding-bottom: 48px;
    }   
#featuredservice ul li.featuredservices img{
    float:left; margin:14px 16px 0 0;
}   
#featuredservice ul li.featuredservices h3{
    font-family: Arial, Helvetica, sans-serif;
    font-size:14px;
    color:#ff3d1a;
    padding:13px 0 0 0;
    margin:0;
}
#featuredservice ul li.featuredservices p{
    display:block;
    font-family:Arial, Helvetica, sans-serif;
    font-size:13px;
    color:#3f3f3f;
    line-height: 19px;
    margin:0;
}

#featuredservice ul li.featuredservices p a{
    font-size:11px;
    font-weight:normal;
    color:#fff;
    padding:1px 12px 3px 6px;
    line-height:16px;
    text-decoration:none;
    height:16px;
    background:url(images/readmore_btn.gif) no-repeat;
}

/* ---- bodyRightContent ---- */
#bodyRight{
    width:275px;
    float:left;
    margin:20px 0 0 0;
}
#bodyRight form{
    width:275px;
    height:50px;
    float:none;
    border:0;
    background:url(images/search_bg.gif) no-repeat;
}
#bodyRight form input{
  width: 190px;
  height:25px;
  float:left;
  border: 0;
  margin:12px 0 0 20px;
  padding-top:4px;
  font-size:12px;
  color:#8b8a89;
  background: #fdfbf9;
}
#bodyRight form input.button{
  width:23px;
  height: 23px;
  float:left;
  background: url(images/search_icon.png) no-repeat;
}

/* Latest news  */
#bodyRight div.latestNews{
  width: 265px;
  float:left;
  margin-top:17px;
  padding:5px;
  background: #f8f2e9;
}
#bodyRight div.latestNews h2 span{
    width:17px;
    height:21px;
    float:left;
    padding:0 5px 0 0;
    margin:10px 0 0 0;
    background:url(images/notes.png) no-repeat;
}
#bodyRight div.latestNews h2{
  width: 250px;
  height:41px;
  float:left;
  font-family: Arial, Helvetica, sans-serif;
  font-size:18px;
  font-weight:normal;
  color:#000;
  background: url(images/latest-news_h2.gif) repeat-x;
  line-height:42px;
  padding:0 0 0px 16px;
  margin:0;
}
#bodyRight div.latestNews ul.news{
  width: 230px;
  float:left;
  list-style:none;
  padding:10px 0 0 0;
  margin:0;
}
#bodyRight ul.news li{
  width:253px;
  float:left;
  padding:0;
  margin:0 5px;
  border-bottom:1px solid #fff;
}
#bodyRight ul.news li a{
  width:236px;
  float:left;
  font-family:Arial, sans-serif;
  font-size:12px;
  font-weight:normal;
  color: #3f3f3f;
  padding:11px 5px 10px 12px;
}
#bodyRight ul.news li a span{
    margin-right:20px;
}
#bodyRight ul.news li a:hover{
  width:236px;
  float:left;
  color: #3f3f3f;
  background: #fff;
}

#bodyRight ul.news li.nobg{
  border:none;
}
#bodyRight ul.news li.nobg a{
  background: none;
  font-size:11px;
  color:#0075ab;
  text-decoration:none;
  padding-left:8px;
  margin-left:15px;
  background:url(images/small_arrow.gif) no-repeat left;
}

/* --testimonials---- */

div.testimonial{
    width:265px;
    float:left;
    background: #f8f2e9;
    margin-top:18px;
    padding:5px;
}

#bodyRight div.testimonial h2 span{
    width:21px;
    height:33px;
    float:left;
    padding:0;
    margin:0px 5px 0 0;
    background: url(images/chat.gif) no-repeat;
}
#bodyRight div.testimonial h2{
  width: 250px;
  height:41px;
  float:left;
  font-family: Arial, Helvetica, sans-serif;
  font-size:18px;
  font-weight:normal;
  color:#000;
  background: url(images/latest-news_h2.gif) repeat-x;
  line-height:35px;
  padding:0 0 0px 15px;
  margin:0;
}

div.testimonial blockquote{
    width:229px;
    float:left;
    color:#3f3f3f;
    font-family:Arial, Helvetica, sans-serif;
    font-size:12px;
    font-weight:normal;
    margin:0 0 0 5px;
    padding:16px 0 10px 20px;
    line-height:14px;
}
div.testimonial blockquote img{
    float:left; 
    margin:2px 9px 14px 0;
}
div.testimonial p{
    display:block;
    width:188px;
    float:left;
    font-family:Arial, Helvetica, sans-serif;
    font-size:11px;
    color:#0075ab;
    padding:2px;
    margin:0 0 10px 20px;
}
div.testimonial p span{
    font-weight:bold;
}

#footer-wrap{
    width:100%;
    height:235px;
    float:left;
    background:url(images/footer_bg.gif) repeat-x;
}
#footer-wrap div.footer{
    float:none;
    margin:0px auto;
    padding:17px 0 0 0;
    width:952px;
}

div.footer h4{
    font-family:Arial, Helvetica, sans-serif;
    font-size:14px;
    font-weight:normal;
    color:#fff;
    background:#212222;
    margin:0;
    padding:8px 0 8px 6px;
}

/* -- style for inner div's starting from pages -- */
div.footer div.pages{
    width:147px;
    float:left;
    padding:0;
    margin:0 29px 0 0;
}
div.pages ul{
    width:147px;
    float:left;
    padding:6px 0 0 0;
    margin:0;
}
div.pages ul li{
    width:107px;
    float:none;
    background:url(images/small_arrow_foot.gif) 0 7px no-repeat;
    margin-left:6px;
    padding:2px 0 2px 2px;
    border-bottom:1px solid #3b3b3b;
}
div.pages ul li a{
    padding:2px 0 2px 5px;
    color:#909090;
}

/* -- stylin services div --- */
div.services{
    width:150px;
    float:left;
    margin:0 29px 0 0;
}
div.services ul{
    width:147px;
    float:left;
    padding:6px 0 0 0;
    margin:0;
}
div.services ul li{
    width:107px;
    float:none;
    background:url(images/small_arrow_foot.gif) 0 7px no-repeat;
    margin-left:6px;
    padding:2px 0 2px 2px;
    border-bottom:1px solid #3b3b3b;
}
div.services ul li a{
    padding:2px 0 2px 5px;
    color:#909090;
}

/* --- stylin miscellanious div --*/
div.various{
    width:150px;
    float:left;
    margin:0 29px 0 0;
}
div.various ul{
    width:147px;
    float:left;
    padding:6px 0 0 0;
    margin:0;
}
div.various ul li{
    width:107px;
    float:none;
    background:url(images/small_arrow_foot.gif) 0 7px no-repeat;
    margin-left:6px;
    padding:2px 0 2px 2px;
    border-bottom:1px solid #3b3b3b;
}
div.various ul li a{
    padding:2px 0 2px 5px;
    color:#909090;
}

/* --- stylin additional div --*/
div.additional{
    width:150px;
    float:left;
    margin:0 29px 0 0;
}
div.additional ul{
    width:147px;
    float:left;
    padding:6px 0 0 0;
    margin:0;
}
div.additional ul li{
    width:107px;
    float:none;
    background:url(images/small_arrow_foot.gif) 0 7px no-repeat;
    margin-left:6px;
    padding:2px 0 2px 2px;
    border-bottom:1px solid #3b3b3b;
}
div.additional ul li a{
    padding:2px 0 2px 5px;
    color:#909090;
}

/* -- stylin social bookmarking div -- */
div.socialbookmarking{
    width:239px;
    float:left;
}

div.socialbookmarking p{
    color:#909090;
    font-size:11px;
    padding-left:8px;
}
div.socialbookmarking div.bookmarkIcons{
    width:239px;
    float:left;
    padding:4px 0;
}
div.socialbookmarking div.bookmarkIcons img{
    float:left;
    margin-left:9px;
}
div.socialbookmarking p.rights{
    width:163px;
    float:left;
    font-size:10px;
}
div.socialbookmarking p.rights a{
    color:#fff;
}

/* #######################################
######### Stylin inner pages #############
######################################## */

#leftPan
    {
        float:left;
        margin:0px;
        padding:0 0 27px 0;
        width:647px;
    }
#leftPan h2
    {
        float:left;
        margin:0px;
        padding:0px;
        width:647px;
        font-family: Arial;
        font-size:27px;
        font-weight:bold;
        color: #000000;
    }
#leftPan p
    {
        float:left;
        margin:5px 0 0 0;
        padding:0px 0 16px 0;
        width:647px;
        font-family: Arial;
        font-size:13px;
        font-weight: normal;
        color: #000000;
        line-height:21px;
    }
#leftPan p a
    {
        color: #419bcb;
        text-decoration:underline;
        font-size:11px;
    }
#leftPan p a:hover
    {
        color: #419bcb;
        text-decoration:none;
        font-size:11px;
    }
    
/* --- stylin blog page --- */
#leftPan ul.blog{
    width: 647px;
    float:left;
}
#leftPan ul.blog li{
    display:block;
    width:647px;
    float:left;
    border-bottom:1px dashed #c9c0b4;
}
#leftPan ul.blog li h3{
    font-size: 22px;
    font-weight:normal;
    padding:10px 0 0 0;
    margin:0;
}
#leftPan ul.blog li p.date{
    padding-bottom:2px;
    margin:0;
}
#leftPan ul.blog li p.post{
    margin:0;
    padding:0 0 6px 0;
}

/* --- stylin portfolio page --- */
#leftPan ul.portfolio{
    width: 647px;
    float:left;
}
#leftPan ul.portfolio li{
    display:block;
    width:647px;
    float:left;
    border-bottom:1px dashed #c9c0b4;
}
#leftPan ul.portfolio li img{float:left; margin:9px 12px 0 0;}
#leftPan ul.portfolio li h3{
    font-size: 22px;
    font-weight:normal;
    padding:10px 0 0 0;
    margin:0;
}
#leftPan ul.portfolio li p.date{
    padding-bottom:2px;
    margin:0;
}
#leftPan ul.portfolio li p.post{
    width: 400px;
    margin:0;
    padding:0 0 6px 0;
    text-align:justify;
}

/* contact us page */
#leftPan img.map{
    float:left;
    margin:10px 0 0 0px;
}
#bodyRight p.address{
    width:250px;
    float:left;
    padding:0;
    padding-left:20px;
    margin:12px 0 12px 0;
}
#bodyRight h5{
    width:250px;
    float:left;
    font-size:13px;
    padding:9px 0 0 20px;
}

/* -- stylin services page -- */
#leftPan img.service{
    float:left;
    margin:10px 0 0 10px;
}

/* --- stylin usefullinks page --- */
#leftPan ul.usefullinks{
    width: 647px;
    float:left;
}
#leftPan ul.usefullinks li{
    display:block;
    width:647px;
    float:left;
    border-bottom:1px dashed #c9c0b4;
}
#leftPan ul.usefullinks li h3{
    font-size: 22px;
    font-weight:normal;
    padding:10px 0 0 0;
    margin:0;
}
#leftPan ul.usefullinks li p.post{
    margin:0;
    padding:0 0 6px 0;
}

/*s tylin site map  */
#leftPan ul.sitemap{
    width: 150px;
    float:left;
    padding:10px 0 0 0px;
}
#leftPan ul.sitemap li{
    display:block;
    padding-left:8px;
    padding-bottom:2px;
    margin-left:5px;
    background:url(images/small_arrow.gif) no-repeat left;
}
#leftPan ul.sitemap li a{
    font-size:12px;
    color:#0075ab;
    text-decoration:none;
    padding-left:8px;
}
</style> 
</head> 
 
<body id="TotalBodyId"> 
<!--Inline css is needed for the top banner/ad, top-margin should be same as the ad-block height--> 
 
 
 
 
<!--Contact starts here --> 
<div id="contactAt"> 
</div> 
<!--Contact ends here --> 
 
<!--Twitter starts here --> 
<div id="twitContent"> 
	<div id="twitable"></div> 
	<div id="twitform" class="twitContainer lastNode"> 
	</div> 
</div> 
<!--Twitter ends here --> 
	<div id="wraper"> 
    	<div id="headerblank"> 
       	  <div id="header"> 
           	<ul> 
                	<li><a href="index.html">Home</a></li> 
                    <li><a href="about.html">About</a></li> 
                    <li><a href="blog.html">Blog</a></li> 
                    <li><a href="services.html">Services</a></li> 
                    <li><a href="portfolio.html">Portfolio</a></li> 
                    <li><a href="usefullinks.html" class="active">Useful&nbsp;Links</a></li> 
                    <li><a href="contact.html">Contact</a></li> 
            </ul> 
           	  <h1><a href="index.html"><img src="images/logo.png" alt="" /></a></h1><h3>Your Nice Website slogan write here</h3> 
           	
            </div> 
        </div> 
        	<div id="bodycontentblank"> 
            	<div id="bodycontent"> 
               	  <div id="bodyleftcontent"> 
                   	<div id="leftPan"> 
                        	<h2>Useful Links</h2> 
                            	 <ul class="usefullinks"> 
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
    </ul> 
                      </div> 
                	</div> 
                    
                    <div id="bodyRight"> 
                        <form action="#"> 
                          <input type="text" class="search" value="Search the site here..." /> 
                          <input type="button" class="button" name="search"  /> 
                        </form> 
                        
                        <div class="latestNews"> 
                        <h2>Useful Content</h2> 
                            <ul class="news"> 
                                <li><a href="more.html"><span>25 DEC 09</span> Archives</a></li> 
                                <li><a href="more.html"><span>24 DEC 09</span> Recent Updates</a></li> 
                                <li><a href="more.html"><span>20 DEC 09</span> Contests</a></li> 
                                <li><a href="more.html"><span>15 DEC 09</span> Atricles</a></li> 
                                <li><a href="more.html"><span>14 DEC 09</span> Downloads</a></li> 
                                <li class="nobg"><a href="more.html" class="viewMore">View More</a></li> 
                            </ul> 
                      </div><!--end of latest news--> 
                        
                        <div class="testimonial"> 
                        	<h2>site Content</h2> 
                        	<blockquote>Posuere cubilia Curaeis sagfa ettis risusat risus feu hregiat sed feugat nuctum. Etiam euismod dabus lorem, dui </blockquote>           					
                            <br /> 
                           	<blockquote>Sosuere cubilia Curaeis sagfa ettis risusat risus feu hregiat sed feugat nuctum tiam euismod dabus lorem</blockquote> 
                        </div><!--end of testimonials--> 
                    </div> 
                    <!--end of right column --> 
                </div> 
            </div> 
 
            <div id="footer-wrap"> 
                <div class="footer"> 
                	<div class="pages"> 
	                    <h4>Pages</h4> 
                        <ul> 
                        	<li><a href="index.html">Home</a></li> 
                            <li><a href="about.html">About Us</a></li> 
                            <li><a href="blog.html">Blog</a></li> 
                            <li><a href="services.html">Services</a></li> 
                            <li><a href="portfolio.html">Portfolio</a></li> 
                            <li><a href="usefullinks.html">Useful Links</a></li> 
                            <li><a href="contact.html">Contact Us</a></li> 
                        </ul> 
                    </div> 
                    <!--end of pages div--> 
                    
                    <div class="services"> 
                    	<h4>Services</h4> 
                        <ul> 
                        	<li><a href="more.html">Services One</a></li> 
                            <li><a href="more.html">Services Two</a></li> 
                            <li><a href="more.html">Services Three</a></li> 
                            <li><a href="more.html">Services Four</a></li> 
                            <li><a href="more.html">Services Five</a></li> 
                            <li><a href="more.html">Services Six</a></li> 
                            <li><a href="more.html">Services Seven</a></li> 
                        </ul> 
                    </div> 
                    <!--end of services--> 
                    
                    <div class="various"> 
                    	<h4>Services</h4> 
                        <ul> 
                        	<li><a href="more.html">Register</a></li> 
                            <li><a href="more.html">Log in</a></li> 
                            <li><a href="tos.html">Terms of Service</a></li> 
                            <li><a href="contact.html">Contact</a></li> 
                            <li><a href="sitemap.html">Site Map</a></li> 
                        </ul> 
                    </div> 
                    <!--end of various--> 
                    
                    <div class="additional"> 
                    	<h4>Additional</h4> 
                        <ul> 
                        	<li><a href="more.html">Lorem ipsum</a></li> 
                            <li><a href="more.html">Vivamus sit mrus</a></li> 
                            <li><a href="more.html">Vestim eeros elit</a></li> 
                            <li><a href="more.html">Sit amet congue</a></li> 
                            <li><a href="more.html">Suspe ndieoncus</a></li> 
                        </ul> 
                    </div> 
                    <!--end of additional info--> 
                    
                    <div class="socialbookmarking"> 
                    	<h4>Keep in touch!</h4> 
                        <p>We will be glad to assist you if you have any problem with out products.</p> 
                        
                        <div class="bookmarkIcons"> 
                   	    <img src="images/facebook_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/linken-icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/in_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/rss_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/twitter_icon.gif" width="32" height="32" alt=" " /> 
                        </div> 
                        <p class="rights">&copy; Your Copyright Info Here. Designed by: <strong><a href="http://www.templateworld.com" title="Template World" target="_blank">Template World</a></strong></p> 
                    </div> 
                    <!--end of additional info--> 
 
                </div> 
            </div><!--end of footer wrapper--> 
    </div> 
</body>  
</html>