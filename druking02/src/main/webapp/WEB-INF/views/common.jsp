<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<div class="sidebar" data-color="purple"
	data-image="resources/assets/img/sidebar-5.jpg">
	<div class="sidebar-wrapper">
		

		<ul class="nav" style="border-bottom:1px solid rgba(255, 255, 255, 0.2);margin-top:0px">
			<li><a href="/">
					<p>사역 입력 시스템</p>
			</a></li>
		</ul>

		<ul class="nav">
		<c:choose>
            <c:when test ="${roles.equals('ROLE_ADMIN')}">
                	<li><a href="/"> <i class="pe-7s-user"></i>
					<p>사역 입력/수정</p>
					</a></li>
					<li><a href="/viewteam"> <i class="pe-7s-note2"></i>
							<p>사역 조회</p>
					</a></li>
            </c:when>
            <c:when test ="${roles.equals('ROLE_INPUT')}">
                	<li><a href="/"> <i class="pe-7s-user"></i>
					<p>사역 입력/수정</p>
					</a></li>
					<li><a href="/viewteam"> <i class="pe-7s-note2"></i>
							<p>사역 조회</p>
					</a></li>
            </c:when>
            <c:when test ="${roles.equals('ROLE_WATCH')}">
                	<li><a href="/viewteam"> <i class="pe-7s-note2"></i>
							<p>사역 조회</p>
					</a></li>
            </c:when>
            <c:otherwise>
                  <li><a href="/"> <i class="pe-7s-user"></i>
					<p>사역 입력/수정</p>
				  </a></li>
            </c:otherwise>
        </c:choose>
			
		</ul>
	</div>
</div>

		





