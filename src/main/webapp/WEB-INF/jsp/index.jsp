<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<head>
		<meta charset="UTF-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Progma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
		<link rel="shortcut icon" type="image/png" href="static/images/favicon2.png"/>
		
		
		<title>| Fatih Çerçi | Weather Condition</title>
		
		<!-- Bootstrap core CSS -->
	    <link href="static/css/bootstrap.min.css" rel="stylesheet">
	
	    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	    <link href="static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
	
	    <!-- Custom styles for this template -->
	    <link href="navbar.css" rel="stylesheet">
	    
	    <link href="static/css/defaultstyle.css" rel="stylesheet">
	
	    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	    <script src="static/js/ie-emulation-modes-warning.js"></script>
	    
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
		
				
		<body>
			<nav class="navbar navbar-colorful">
		        <div class="container-fluid">
		          <div class="navbar-header">
		            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		              <span class="sr-only">Toggle navigation</span>
		              <span class="icon-bar"></span>
		              <span class="icon-bar"></span>
		              <span class="icon-bar"></span>
		            </button>
		            <a class="navbar-brand" href="/">WEATHER CONDITION</a>
		          </div>
		          <div id="navbar" class="navbar-collapse collapse">
		            <ul class="nav navbar-nav">
		            	<c:if test="${!empty user}"> 
		            		<c:if test="${user.userType == 'ADMIN'}">
		            			<li><a href="/menu1">Lokasyon Düzenle</a></li>
								<li><a href="/menu2">Hava Durumu</a></li>
								<li><a href="/menu3">Kullanıcı Düzenle</a></li>
								<li><a href="/menu4">Raporlar</a></li>
		            		</c:if>
		            		<c:if test="${user.userType == 'STANDART'}">
								<li><a href="/menu2">Hava Durumu</a></li>
								<li><a href="/menu4">Raporlar</a></li>
		            		</c:if>
		              </c:if>
		            </ul>
		            <c:if test="${!empty user}">
		            <ul class="nav navbar-nav navbar-right">
		            	<li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Çıkış Yap</a></li>
		            </ul>
		            </c:if>
		          </div><!--/.nav-collapse -->
		        </div>
	      	</nav>
			<div id="feedback"></div>
			<c:choose>
				<c:when test="${mode == 'MENU_HOME'}">
					<div class="container" id="menu1">
						<div class="form-group">
							<div class="jumbotron text-center" style="min-height: 570px !important; background: #f7f7f7; opacity:0.98;">
								<h3>Merhaba <b>${user.name} ${user.surName}</b></h3>
								<hr>
								<br>
								<c:if test="${!empty user}"> 
				            		<c:if test="${user.userType == 'ADMIN'}">
				            			<a href="/menu1"><button type="button" class="btn btn-primary btn-lg btn-block">Lokasyon Düzenle</button></a>
				            			<br>
				            			<a href="/menu2"><button type="button" class="btn btn-primary btn-lg btn-block">Hava Durumu</button></a>
				            			<br>
				            			<a href="/menu3"><button type="button" class="btn btn-primary btn-lg btn-block">Kullanıcı Düzenle</button></a>
				            			<br>
				            			<a href="/menu4"><button type="button" class="btn btn-primary btn-lg btn-block">Raporlar</button></a>
				            		</c:if>
				            		<c:if test="${user.userType == 'STANDART'}">
										<a href="/menu2"><button type="button" class="btn btn-primary btn-lg btn-block">Hava Durumu</button></a>
										<br>
										<a href="/menu4"><button type="button" class="btn btn-primary btn-lg btn-block">Raporlar</button></a>
				            		</c:if>
				              </c:if>
								<br>
								<br>
							</div>
						</div>
					</div>
			 	</c:when>
				 <c:when test="${mode == 'MENU_1'}">
				 	<div class="container" id="menu1">
						<div class="form-group">
							<div class="jumbotron text-center" style="min-height: 570px !important; background: #f7f7f7;">
								<h3>Lokasyon Düzenle</h3>
								<hr>
								<form class="form-horizontal" method="POST" action="searchAddress">
									<div class="form-group">
										<label class="control-label col-md-2">Lokasyon Adı</label>
										<div class="col-md-7">
											<input type="text" class="form-control" name="name" placeholder="Lokasyon adı" value="${address.name}"/>
										</div>
										<div class="form-group text-left">
											<input type="submit" class="btn btn-primary" value="Ara"/>
										</div>
									</div>
								</form>
								<c:if test="${!empty addressList}"> 
									<div class="container text-center" id="addressList">
										<div class="table-responsive">
											<table class="table table-striped table-bordered text-left">
												<thead>
													<tr>
														<th>Lokasyon Adı</th>
														<th>Ülke</th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="address" items="${addressList}">
														<tr>
															<td>${address.name}</td>
															<td>${address.country}</td>
															<td width="50px" class="text-center"><a href="saveLocation?placeId=${address.placeId}&name=${address.name}&country=${address.country}"><span class="glyphicon glyphicon-plus-sign"></span></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>
								<c:if test="${!empty locationList}"> 
									<h4>Kayıtlı Lokasyonlar</h4>
									<hr>
									<div class="container text-center" id="locationList">
										<div class="table-responsive">
											<table class="table table-striped table-bordered text-left">
												<thead>
													<tr>
														<th>Lokasyon Adı</th>
														<th>Ülke</th>
														<th>Eklenme Zamanı</th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="location" items="${locationList}">
														<tr>
															<td>${location.name}</td>
															<td>${location.country}</td>
															<td>${location.createdTimeDisplay}</td>
															<td width="50px" class="text-center"><a href="deleteLocation?placeId=${location.placeId}"><span class="glyphicon glyphicon-minus-sign"></span></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				 </c:when>
				 <c:when test="${mode == 'MENU_2'}">
					<div class="container" id="menu2">
						<div class="form-group">
							<form class="form-horizontal" method="POST" action="queryWeatherCondition">
								<div class="form-group">
									<div class="jumbotron text-center" style="min-height: 500px !important; background: #f7f7f7;">
										<h3>5 Günlük Hava Durumu</h3>
										<hr>
									
										<label class="control-label col-md-4">Lokasyon</label>
										<div class="col-md-3">
											<select class="form-control" name="placeId" id="location" value="${location.placeId}">
												<option name="placeId" value=null>-------</option>
												<c:forEach var="location" items="${locationList}">
												 	<option name="placeId" value="${location.placeId}">${location.name}, ${location.country}</option>
												</c:forEach>
										  	</select>
										</div>
										<div class="form-group text-left">
											<input type="submit" class="btn btn-primary" value="Sorgula"/>
										</div>
										<br>
										<c:if test="${!empty currentLocation}"> 
											<h3>${currentLocation.name}, ${currentLocation.country}</h3>
											<br>
										</c:if>
									
										<c:if test="${!empty weatherConditionList}"> 
											<div class="row">
												<c:forEach var="weatherCondition" items="${weatherConditionList}">
													  <div class="col-xs-6 col-md-2">
															<h4><u>${weatherCondition.day}</u></h4>													    
														    <h4>Max: ${weatherCondition.max} °C</h4>
														    <h4>Min: ${weatherCondition.min} °C</h4>  
													  </div>
												</c:forEach>
											</div>
											<hr>
										</c:if>
									</div>
								</div>
							</form>
						</div>
					</div>
				 </c:when>
				 <c:when test="${mode == 'MENU_3'}">
					<div class="container" id="menu3">
						<div class="form-group">
							<div class="jumbotron text-center" style="min-height: 570px !important; background: #f7f7f7;">
								<h3>Kullanıcı Düzenle</h3>
								<hr>
								<form class="form-horizontal" method="POST" action="saveUser">
									<input type="hidden" name="id" value="${users.id}"/> 
									<div class="form-group">
										<label class="control-label col-md-4">Adı</label>
										<div class="col-md-4">
											<input type="text" class="form-control" name="name" value="${users.name}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-4">Soyadı</label>
										<div class="col-md-4">
											<input type="text" class="form-control" name="surName" value="${users.surName}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-4">Kullanıcı Adı</label>
										<div class="col-md-4">
											<input type="text" class="form-control" name="userName" value="${users.userName}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-4">Şifre</label>
										<div class="col-md-4">
											<input type="text" class="form-control" name="password" value="${users.password}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-4">Tipi</label>
										<div class="col-md-4">
											<select class="form-control" name="userType" id="location" value="${users.userType}">
												<c:forEach var="userType" items="${userTypes}">
													<c:if test="${users.userType == userType.key}"> 
												 		<option selected name="userType" value="${userType.key}">${userType.value}</option>
												 	</c:if>
												 	<c:if test="${users.userType != userType.key}"> 
												 		<option name="userType" value="${userType.key}">${userType.value}</option>
												 	</c:if>
												</c:forEach>
										  	</select>
										</div>
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-primary" value="Kaydet"/>
									</div>
									<c:if test="${!empty userErrorMessage}"> 
										<div class="alert alert-danger" role="alert">
										  	${userErrorMessage}
										</div>
									</c:if>
								</form>
								<br>
								<c:if test="${!empty userList}"> 
									<h4>Kayıtlı Kullanıcılar</h4>
									<hr>
									<div class="container text-center" id="userList">
										<div class="table-responsive">
											<table class="table table-striped table-bordered text-left">
												<thead>
													<tr>
														<th>Adı</th>
														<th>Soyadı</th>
														<th>Eklenme Zamanı</th>
														<th>Güncellenme Zamanı</th>
														<th>Kullanıcı Adı</th>
														<th>Şifre</th>
														<th>Tipi</th>
														<th></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="users" items="${userList}">
														<tr>
															<td>${users.name}</td>
															<td>${users.surName}</td>
															<td>${users.createdTimeDisplay}</td>
															<td>${users.updatedTimeDisplay}</td>
															<td>${users.userName}</td>
															<td>${users.password}</td>
															<td>${users.userType}</td>
															<c:if test="${users.userName != 'root'}"> 
																<td width="50px" class="text-center"><a href="updateUser?id=${users.id}"><span class="glyphicon glyphicon-pencil"></span></a></td>
																<td width="50px" class="text-center"><a href="deleteUser?id=${users.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
															</c:if>
															<c:if test="${users.userName == 'root'}"> 
																<td width="50px" class="text-center"></td>
																<td width="50px" class="text-center"></td>
															</c:if>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>
							</div>
							</div>
						</div>
					</div>
				 </c:when>
				 <c:when test="${mode == 'MENU_4'}">
					<div class="container-fluid" id="menu4">
						<div class="form-group"> 
							<div class="jumbotron text-center" style="min-height: 570px !important; background:#f7f7f7;">
								<h3>Raporlar</h3>
								<hr>
								<form class="form-horizontal" method="POST" action="searchQueryHistory">
									<div class="form-group">
										<label class="control-label col-md-5">Kullanıcı</label>
										<div class="col-md-2">
											<select class="form-control" name="userId" id="userId" value="${queryHistory.userId}">
												<option name="userId" value=null>---</option>
												<c:forEach var="users" items="${users}">
												 		<option name="userId" value="${users.userId}">${users.userName}</option>
												</c:forEach>
										  	</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-5">Sorgulama Zamanı</label>
										<div class="col-md-2" >
											<input class="form-control" type="datetime-local" value="${queryHistory.timeStart}" name="timeStart" id="example-datetime-local-input">
											/
											<input class="form-control" type="datetime-local" value="${queryHistory.timeEnd}" name="timeEnd" id="example-datetime-local-input">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-5">Lokasyon Adı</label>
										<div class="col-md-2">
											<input type="text" class="form-control" name="locationName" value="${queryHistory.locationName}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-5">Sorgulama Durumu</label>
										<div class="col-md-2">
											<select class="form-control" name="queryStatus" id="queryStatus" value="${queryHistory.queryStatus}">
												<option name="queryStatus" value=null>---</option>
												<c:forEach var="queryStatuses" items="${queryStatuses}">
												 		<option name="queryStatus" value="${queryStatuses.key}">${queryStatuses.value}</option>
												</c:forEach>
										  	</select>
										</div>
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-primary" value="Sorgula"/>
									</div>
								</form>
								<!-- <c:if test="${!empty queryHistoryList}"> -->
									<div class="container-fluid text-center" id="queryHistoryList">
										<div class="table-reponsive">
											<table class="table table-striped table-bordered text-left">
												<thead>
													<tr>
														<th>Kullanıcı ID</th>
														<th>Kullanıcı Adı</th>
														<th>Lokasyon ID</th>
														<th>Lokasyon Adı</th>
														<th>IP Adresi</th>
														<th>Sorgulama Zamanı</th>
														<th>Sorgulama Sonucu</th>
														<th>Geçen Süre (ms)</th>
														<th>Sorgulama Durumu</th>					
													</tr>
												</thead>
												<tbody>
													<c:forEach var="queryHistory" items="${queryHistoryList}">
														<tr>
															<td data-toggle="tooltip">${queryHistory.userId}</td>
															<td>${queryHistory.userName}</td>
															<td>${queryHistory.locationId}</td>
															<td>${queryHistory.locationName}</td>
															<td>${queryHistory.userIpAdress}</td>
															<td>${queryHistory.timeDisplay}</td>
															<td data-toggle="tooltip">${queryHistory.result}</td>
															<td>${queryHistory.elapsedTime}</td>
															<td>${queryHistory.queryStatus}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								<!-- </c:if>  -->
							</div>
						</div>
					</div>
				 </c:when>
				 <c:when test="${mode == 'MENU_LOGIN'}">
					 <div class="login_container"> 
					 	<div class="panel panel-default">
				            <div class="panel-heading">
				              <h3 class="panel-title"><span class="glyphicon glyphicon-user"></span> Giriş Yap</h3>
				            </div>
				            <div class="panel-body text-center">
				            	<form class="form-horizontal" method="POST" action="/login">
									<div class="form-group">
										<label class="control-label col-md-3">Kullanıcı adı</label>
										<div class="col-md-7">
											<input type="text" class="form-control" name="username" placeholder="Kullanıcı adı" value="${user.userName}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">Şifre</label>
										<div class="col-md-7">
											<input type="password" class="form-control" name="password" placeholder="Şifre" value="${user.password}"/>
										</div>
									</div>
									<div class="form-group text-center">
										<input type="submit" class="btn btn-primary" value="Giriş Yap"/>
									</div>
									<c:if test="${!empty errorMessage}"> 
										<div class="alert alert-danger" role="alert">
											<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										  	${errorMessage}
										</div>
									</c:if>
								</form>
				            </div>
				         </div>
					</div>
				 </c:when>
			</c:choose>
			<!-- Bootstrap core JavaScript
		    ================================================== -->
		    <!-- Placed at the end of the document so the pages load faster -->
		    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		    <script>window.jQuery || document.write('<script src="static/js/vendor/jquery.min.js"><\/script>')</script>
		    <script src="static/js/bootstrap.min.js"></script>
		    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		    <script src="static/js/ie10-viewport-bug-workaround.js"></script>
		</body>
	</head>
</html>