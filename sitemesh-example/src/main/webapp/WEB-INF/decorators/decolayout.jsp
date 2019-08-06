<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<html>
	<head>
		<title><decorator:title default="Mysterious page..." /></title>
		<link href="<%= request.getContextPath() %>/decorators/main.css" rel="stylesheet" type="text/css">
		<decorator:head />
	</head>

	<body>
		<table width="100%" height="100%">
			<tr>
				<td valign="top">
				</td>
				<td width="100%">
					<table width="100%" height="100%">
						<tr>
							<td id="pageTitle">
								<decorator:title />
							</td>
						</tr>
						<tr>
							<td valign="top" height="100%">
							<page:applyDecorator name="top" encoding="utf-8" />
							<page:applyDecorator name="left" encoding="utf-8" />
							<page:applyDecorator name="bottom" encoding="utf-8" />
								<decorator:body />
							</td>
						</tr>
						<tr>
							<td id="footer">
								<b>Disclaimer:</b>
								This site is an example site to demonstrate SiteMesh. It serves no other purpose.
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<%-- Construct URL from page request and append 'printable=true' param --%>
		<decorator:usePage id="p" />
		<%
			StringBuffer printUrl = new StringBuffer();
			printUrl.append(request.getRequestURI());
			printUrl.append("?printable=true");
			if (request.getQueryString()!=null) {
				printUrl.append('&');
				printUrl.append(request.getQueryString());
			}
		%>
		<p align="right">[ <a href="<%= printUrl %>">printable version</a> ]</p>

	</body>
</html>