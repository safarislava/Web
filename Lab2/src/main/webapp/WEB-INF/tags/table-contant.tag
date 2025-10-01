<%@ attribute name="scope" required="true" %>
<%@ tag import="com.google.gson.Gson" %>
<%@ tag import="ru.ifmo.se.webapp.dto.PointResponse" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.google.gson.reflect.TypeToken" %>
<%
    if (session.getAttribute(scope) != null) {
        Gson gson = new Gson();
        List<PointResponse> points = gson.fromJson((String) session.getAttribute(scope),
                new TypeToken<List<PointResponse>>(){}.getType());

        for (int i = 0; i < points.size(); i++) {
            PointResponse point = points.get(i);
%>
<tr>
    <td><%= i %></td>
    <td><%= point.x %></td>
    <td><%= point.y %></td>
    <td><%= point.r %></td>
    <td><%= point.isPointInArea %></td>
    <td><%= point.deltaTime %></td>
    <td><%= point.time %></td>
</tr>
<%
        }
    }
%>