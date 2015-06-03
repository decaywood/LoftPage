<!DOCTYPE HTML>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="<%=path%>/js/jquery.min.js"></script>
</head>
<body>
<div class="content" id="home">
	<div class="wrap">
		<div class="header_style1">
			<!-- start h_logo -->
			<div class="logo">
				<h1><a href="mainPage.jsp">Resume</a></h1>
			</div>
			<!-- start h_menu -->
			<div class="h_menu">
				<ul class="flexy-menu thick orange">
					<li> <a href="#home" class="scroll">主页</a></li>
					<li><a href="#Portfolio" class="scroll">Github</a></li>
					<li><a href="#skills" class="scroll">技能</a> </li>
					<li><a class="scroll" href="#Experience">项目经验</a></li>
					<li><a href="#contact" class="scroll">联系我</a></li>
				</ul>
			</div>
			<!-- end h_menu -->
			<div class="clear"> </div>
		</div>
		<!---------menu-script-------------> 
		<!-- start manu --> 
		<script type="text/javascript" src="<%=path%>/js/flexy-menu.js"></script>
		<link href="<%=path%>/css/header_style1.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript">$(document).ready(function(){$(".flexy-menu").flexymenu({speed: 400,type: "horizontal",align: "right"});});</script> 
		<!---------end-script-menu------------->
		<span class="slider">
			<!---start-da-slider----->
			<div id="da-slider" class="da-slider">
				<div class="da-slide">
					<h2>Designer & maker</h2>
					<p>Lorem ipsum is simply dummy text of the printing</p>
					<a href="#" class="da-link"><span> READ NOW1</span></a> </div>
				<div class="da-slide">
					<h2>Designer & maker</h2>
					<p>Lorem ipsum is simply dummy text of the printing</p>
					<a href="#" class="da-link"><span> READ NOW2</span></a> </div>
				<div class="da-slide">
					<h2>Designer & maker</h2>
					<p>Lorem ipsum is simply dummy text of the printing</p>
					<a href="#" class="da-link"><span> READ NOW3</span></a> </div>

				<nav class="da-arrows">
					<span class="da-arrows-prev"></span>
					<span class="da-arrows-next"></span>
				</nav>
			</div>
			<div class="down-arrow"> <a href="#group_1" class="scroll" ><span> </span></a> </div>
			<link rel="stylesheet" type="text/css" href="<%=path%>/css/slider.css" />
			<script type="text/javascript" src="<%=path%>/js/modernizr.custom.28468.js"></script>
			<script type="text/javascript" src="<%=path%>/js/jquery.cslider.js"></script>
			<script type="text/javascript">
				$(function() {

					$('#da-slider').cslider({
						autoplay	: true,
						bgincrement	: 0
					});

				});
			</script>
			<!---//End-da-slider----->
		</span>
	</div>
</div>
<!----start-team--------->
<div class="group_1" id="group_1">
	<div class="group_1-items">
		<div class="wrap">
			<div id="owl-demo" class="owl-carousel">
				<div class="item" >
					<div class="carousel">
						<div class="group_1_img">
							<h3 class="ui">UI</h3>
							<h4 class="ux">UX</h4>
							<div class="clear"> </div>
						</div>
						<div class="group_1_text">
							<h3>I create simple interfaces for humans and enjoy pixel-perfection.</h3>
							<p>I’m an experienced and passionate user interface designer with interaction design background.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class="group_1_img">
							<h3 class="ui fontend">FRONTEND</h3>
							<h4 class="ux ui1">UI</h4>
							<div class="clear"> </div>
						</div>
						<div class="group_1_text">
							<h3>I create simple interfaces for humans and enjoy pixel-perfection.</h3>
							<p>I’m an experienced and passionate user interface designer with interaction design background.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class="group_1_img ">
							<h3 class="ui core">CORE</h3>
							<h4 class="ux ui1">UI</h4>
							<div class="clear"> </div>
						</div>
						<div class="group_1_text">
							<h3>I create simple interfaces for humans and enjoy pixel-perfection.</h3>
							<p>I’m an experienced and passionate user interface designer with interaction design background.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="group_2" id="Portfolio">
	<h3 class="heading">Portfolio</h3>
	<div class="group_2_items">
		<div class="wrap">
			<div id="owl-demo1" class="owl-carousel">
				<div class="item" >
					<div class="carousel">
						<div class="group_2_img1"> <img src="<%=path%>/images/img1.png" alt=""> </div>
						<div class="group_2_text2">
							<div class="desc">
								<h3>client</h3>
								<h4>Apple</h4>
							</div>
							<div class="desc">
								<h3>Role in project</h3>
								<h4>Designer Lead</h4>
							</div>
							<div class="desc">
								<h3>project included</h3>
								<h4>Mobile and web interface</h4>
							</div>
							<div class="button_2"> <a href="#">Browser</a> </div>
						</div>
						<div class="clear"> </div>
						<div class="brows_button_3"> <a href="#">Browser portfolio</a> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class="group_2_img1"> <img src="<%=path%>/images/img2.jpg" alt=""> </div>
						<div class="group_2_text2">
							<div class="desc">
								<h3>client</h3>
								<h4>Lorem ipsum dolor</h4>
							</div>
							<div class="desc">
								<h3>Role in project</h3>
								<h4>Designer Lead</h4>
							</div>
							<div class="desc">
								<h3>project included</h3>
								<h4>Responsive Mobile Website Templates </h4>
							</div>
							<div class="button_2"> <a href="#">Browser</a> </div>
						</div>
						<div class="clear"> </div>
						<div class="brows_button_3"> <a href="#">Browser portfolio</a> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class="group_2_img1"> <img src="<%=path%>/images/img3.jpg" alt=""> </div>
						<div class="group_2_text2">
							<div class="desc">
								<h3>client</h3>
								<h4>Apple</h4>
							</div>
							<div class="desc">
								<h3>Role in project</h3>
								<h4>Designer Lead</h4>
							</div>
							<div class="desc">
								<h3>project included</h3>
								<h4>Developing games in Android Mobiles</h4>
							</div>
							<div class="button_2"> <a href="#">Browser</a> </div>
						</div>
						<div class="clear"> </div>
						<div class="brows_button_3"> <a href="#">Browser portfolio</a> </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Owl Carousel Assets -->
<link href="<%=path%>/css/owl.carousel.css" rel="stylesheet">
<!-- Owl Carousel Assets --> 
<!-- Prettify --> 
<script src="<%=path%>/js/owl.carousel.js"></script>
<script>
		    $(document).ready(function() {
		
		      $("#owl-demo").owlCarousel({
		        items : 1,
		        lazyLoad : true,
		        autoPlay : true,
		        navigation : true,
			    navigationText : ["",""],
			    rewindNav :true,
			    scrollPerPage :true,
			    pagination : true,
    			paginationNumbers: false,
		      });
		
		    });
		    $(document).ready(function() {
		
		      $("#owl-demo1").owlCarousel({
		        items : 1,
		        lazyLoad : true,
		        autoPlay : true,
		        navigation : true,
			    navigationText : ["",""],
			    rewindNav : true,
			    scrollPerPage :true,
			    pagination : false,
    			paginationNumbers: false,
		      });
		
		    });
		    $(document).ready(function() {
		
		      $("#owl-demo2").owlCarousel({
		        items : 1,
		        lazyLoad : true,
		        autoPlay : true,
		        navigation : false,
			    navigationText : ["",""],
			    rewindNav : false,
			    scrollPerPage :true,
			    pagination : false,
    			paginationNumbers: false,
		      });
		
		    });
		    </script> 
<!-- //Owl Carousel Assets -->
<div class="skills" id="skills">
	<section class="text-light">
		<div  class="wrap">
			<div class="row-content1">
				<h3 class="heading">Skills</h3>
				<p class="para">Iam an experienced and passionate user interface designer with interaction design background.<br>
					My goal is to make the world wide web a better place by designing beautiful user experiences, one site at a time.</p>
				<div class="chart" data-percent="73" data-bar-color="#35AFBA" data-animate="4000">
					<div class="chart-content">
						<div class="percent"></div>
						<div class="chart-title">User Interface</div>
					</div>
					<!-- chart-content --> 
				</div>
				<!-- chart -->
				<div class="chart" data-percent="85" data-bar-color="#FF6060" data-animate="2500">
					<div class="chart-content">
						<div class="percent"></div>
						<div class="chart-title">Front-end</div>
					</div>
					<!-- chart-content --> 
				</div>
				<!-- chart -->
				<div class="chart" data-percent="70" data-bar-color="#3AD079" data-animate="3000">
					<div class="chart-content">
						<div class="percent"></div>
						<div class="chart-title">User Experience</div>
					</div>
					<!-- chart-content --> 
				</div>
				<!-- chart -->
				<div class="chart" data-percent="40" data-bar-color="#58C0E3" data-animate="3500">
					<div class="chart-content">
						<div class="percent"></div>
						<div class="chart-title">User Experience</div>
					</div>
					<!-- chart-content --> 
				</div>
				<!-- chart -->
				<div class="clear"> </div>
			</div>
		</div>
	</section>
	<script src="<%=path%>/js/plugins.js"></script>
	<script src="<%=path%>/js/script.js"></script>
</div>
<div class="exper" id="Experience">
	<div class="wrap">
		<h3 class="heading">Experience</h3>
		<section class="row section">
			<div class="row-content2">
				<div class="timeline-label column six">
					<h4>Work experience</h4>
					<p>Specialties : User experience design, user interface design, brand identity, typography, print and packaging design, web standards, grid based layout.</p>
					<div class="brows_button_4"> <a href="#">View on Linkedin</a> </div>
				</div>
				<!-- timeline-label -->
				<div class="timeline column six last">
					<div class="year">
						<time datetime="2013">2013</time>
						<div class="experience"> <span class="circle"></span>
							<div class="experience-img"><img src="<%=path%>/images/asset01.jpg" alt=""></div>
							<div class="experience-info clear-after">
								<h5>Facebook</h5>
								<div class="role">UI/UX Designer</div>
								<p>October 2013 – November 2013 (2 months)Menlo Park, CA, United States of America</p>
							</div>
							<!-- experience-info --> 
						</div>
						<!-- experience -->
						<div class="experience"> <span class="circle"></span>
							<div class="experience-img"><img src="<%=path%>/images/asset02.jpg" alt=""></div>
							<div class="experience-info clear-after">
								<h5>Amazon</h5>
								<div class="role">UI/UX Designer</div>
								<p>October 2013 – November 2013 (2 months)Menlo Park, CA, United States of America</p>
							</div>
							<!-- experience-info --> 
						</div>
						<!-- experience --> 
					</div>
					<!-- year -->
					<div class="year year1">
						<time datetime="2012">2012</time>
						<div class="experience"> <span class="circle"></span>
							<div class="experience-img"><img src="<%=path%>/images/asset03.jpg" alt=""></div>
							<div class="experience-info clear-after">
								<h5>Apple</h5>
								<div class="role">UI/UX Designer</div>
								<p>October 2013 – November 2013 (2 months)Menlo Park, CA, United States of America</p>
							</div>
							<!-- experience-info --> 
						</div>
						<!-- experience -->
						<div class="experience"> <span class="circle"></span>
							<div class="experience-img"><img src="<%=path%>/images/asset04.jpg" alt=""></div>
							<div class="experience-info clear-after">
								<h5>IBM</h5>
								<div class="role">UI/UX Designer</div>
								<p>October 2013 – November 2013 (2 months)Menlo Park, CA, United States of America</p>
							</div>
							<!-- experience-info --> 
						</div>
						<!-- experience --> 
					</div>
					<!-- year --> 
				</div>
				<!-- timeline -->
				<div class="clear"> </div>
			</div>
		</section>
	</div>
</div>
<div class="group_3">
	<div class="group_3-items">
		<div class="wrap">
			<div id="owl-demo2" class="owl-carousel">
				<div class="item" >
					<div class="carousel">
						<div class="group_3_img"> <img src="<%=path%>/images/img.png" alt=""> </div>
						<div class="group_1_text group_3_text">
							<h3> Adedayo Saheed</h3>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class="group_3_img"> <img src="<%=path%>/images/img.png" alt=""> </div>
						<div class="group_1_text group_3_text">
							<h3>Narate Ketram</h3>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
				<div class="item">
					<div class="carousel">
						<div class=" group_3_img"> <img src="<%=path%>/images/img.png" alt=""> </div>
						<div class="group_1_text group_3_text">
							<h3>Adrian Thomas</h3>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.</p>
						</div>
						<div class="clear"> </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="contact" id="contact">
	<div class="wrap">
		<h3 class="heading">Contact</h3>
		<form>
			<div class="left-form">
				<ul>
					<li class="name"> <a href="#" class="icon user"> </a>
						<input type="text" placeholder="Username" required>
						<div class="clear"> </div>
					</li>
					<li class="email"> <a href="#" class="icon mail"> </a>
						<input type="email" placeholder="Email" required>
						<div class="clear"> </div>
					</li>
					<div class="clear"> </div>
					<li>
						<input type="text" placeholder="Cost of your project" required>
						<div class="clear"> </div>
					</li>
					<li>
						<input type="text" placeholder="Timeline" required>
						<div class="clear"> </div>
					</li>
					<li>
						<textarea class="plain buffer" placeholder="Don't forget that kindness is all!"> Don't forget that kindness is all!</textarea>
						<div class="clear"> </div>
					</li>
					<div class="send"> <a href="#">SEND</a> </div>
				</ul>
			</div>
			<div class="right-form">
				<h4>LOCATION</h4>
				<p>122 Lorem ipsum dolor, sit amet, 678 consectetur</p>
				<div class="soc_icons">
					<h4>I AM SOCIAL</h4>
					<ul>
						<li><a class="icon1" href="#"> <span>Facebook</span> </a> </li>
						<li><a class="icon2" href="#"><span>Twitter</span></a></li>
						<li><a class="icon3" href="#"><span>Google+</span></a></li>
						<li><a class="icon4" href="#"><span>Linkedin</span></a></li>
						<li><a class="icon5" href="#"><span>Flickr</span></a></li>
						<div class="clear"> </div>
					</ul>
				</div>
			</div>
			<div class="clear"> </div>
		</form>
	</div>
</div>
<div class="contact-map">
	<iframe width="100%" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.co.in/?ie=UTF8&amp;ll=22.593726,79.453125&amp;spn=3.803105,6.696167&amp;t=m&amp;z=8&amp;output=embed"></iframe>
</div>
<div class="footer">
	<div class="wrap">
		<div class="row-content buffer clear-after">
			<section id="top-footer">
				<div class="widget column three"><!-- la class="widget" è forse generata utomaticamente da wp -->
					<h4>Menu</h4>
					<ul class="plain">
						<li> <a href="#home" class="scroll">Home</a> </li>
						<li><a href="#Portfolio" class="scroll">Portfolio</a></li>
						<li><a href="#skills" class="scroll">Skills</a> </li>
						<li><a class="scroll" href="#Experience">Experience</a></li>
						<li><a href="#contact" class="scroll">Contact</a></li>
						<div class="clear"> </div>
					</ul>
				</div>
				<div class="widget column three">
					<h4>Archives</h4>
					<ul class="plain">
						<li><a href="#">March 2014</a></li>
						<li><a href="#">April 2014</a></li>
						<li><a href="#">May 2014</a></li>
						<li><a href="#">June 2014</a></li>
						<li><a href="#">July 2014</a></li>
						<div class="clear"> </div>
					</ul>
				</div>
				<div class="widget column three">
					<h4>Widget</h4>
					<p>Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
				</div>
				<div class="widget meta-social column three last">
					<div class="soc_icons soc_icons1">
						<h4>Follows</h4>
						<ul>
							<li><a class="icon1" href="#"> </a> </li>
							<li><a class="icon2" href="#"> </a></li>
							<li><a class="icon3" href="#"> </a></li>
						</ul>
						<div class="clear"> </div>
					</div>
				</div>
				<div class="clear"> </div>
			</section>
			<!-- top-footer --> 
			
		</div>
		<!-- row-content --> 
	</div>
	<!-- row -->
	<div class="copy">
		<p>© 2014 Template </p>
	</div>
	<!-- scroll_top_btn --> 
	<script type="text/javascript" src="<%=path%>/js/move-top.js"></script>
	<script type="text/javascript" src="<%=path%>/js/easing.js"></script>
	<script type="text/javascript">
			$(document).ready(function() {
			
				var defaults = {
		  			containerID: 'toTop', // fading element id
					containerHoverID: 'toTopHover', // fading element hover id
					scrollSpeed: 1200,
					easingType: 'linear' 
		 		};
				
				
				$().UItoTop({ easingType: 'easeOutQuart' });
				
			});
		</script> 
	<script type="text/javascript">
		jQuery(document).ready(function($) {
			$(".scroll").click(function(event){		
				event.preventDefault();
				$('html,body').animate({scrollTop:$(this.hash).offset().top},1200);
			});
		});
	</script> 
	<a href="#" id="toTop" style="display: block;"><span id="toTopHover" style="opacity: 1;"></span></a> </div>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>