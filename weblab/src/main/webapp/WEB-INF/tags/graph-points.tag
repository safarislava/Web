<%@ attribute name="scope" required="true" %>
<%@ tag import="java.math.MathContext" %>
<%@ tag import="java.math.BigDecimal" %>
<%@ tag import="com.google.gson.Gson" %>
<%@ tag import="ru.ifmo.se.webapp.dto.PointResponse" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.google.gson.reflect.TypeToken" %>

<%!
    private final MathContext mathContext = MathContext.DECIMAL128;
    private final BigDecimal HUNDRED = new BigDecimal("150");

    public BigDecimal translateX(String x, String r) {
        return new BigDecimal(x).divide(new BigDecimal(r), mathContext).multiply(HUNDRED);
    }

    public BigDecimal translateY(String y, String r) {
        return new BigDecimal(y).divide(new BigDecimal(r), mathContext).multiply(HUNDRED).negate();
    }
%>
<%
    if (session.getAttribute(scope) != null) {
        Gson gson = new Gson();
        List<PointResponse> points = gson.fromJson((String) session.getAttribute(scope),
                new TypeToken<List<PointResponse>>(){}.getType());

        for (PointResponse point : points) {
            String cx = translateX(point.x, point.r).toPlainString();
            String cy = translateY(point.y, point.r).toPlainString();
%>
<circle cx="<%= cx %>" cy="<%= cy %>" r=3 class="graph-item"></circle>
<%
        }
    }
%>