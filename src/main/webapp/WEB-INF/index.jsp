<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link media="screen" href="style/styles.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.28.14/js/jquery.tablesorter.min.js"></script>
    <title>Parts</title>
    <script type="text/javascript">
        $(function () {
            $('#keywords').tablesorter();
        });
    </script>
</head>
<body>

<%
    String number, name, vendor, qty, shipped_after, shipped_before, received_after, received_before; // pure hell
    number = request.getParameter("number") == null ? "" : request.getParameter("number");
    name = request.getParameter("name") == null ? "" : request.getParameter("name");
    vendor = request.getParameter("vendor") == null ? "" : request.getParameter("vendor");
    qty = request.getParameter("qty") == null ? "" : request.getParameter("qty");
    shipped_after = request.getParameter("shipped_after") == null ? "" : request.getParameter("shipped_after");
    shipped_before = request.getParameter("shipped_before") == null ? "" : request.getParameter("shipped_before");
    received_after = request.getParameter("received_after") == null ? "" : request.getParameter("received_after");
    received_before = request.getParameter("received_before") == null ? "" : request.getParameter("received_before");
%>

<div id="wrapper">
    <h1>Parts</h1>

    <div class="filters">
        <form action="./" method="get">
            <table>
                <tr>
                    <td style="text-align:center;" colspan="5">Filters</td>
                </tr>
                <tr>
                    <td>PN</td>
                    <td><input value="<%=number%>"
                               type="text" name="number" size="20" placeholder="part number"/></td>
                </tr>
                <tr>
                    <td>Part name</td>
                    <td><input value="<%=name%>" type="text" name="name" value="" size="20"/>
                    </td>
                </tr>
                <tr>
                    <td>Vendor</td>
                    <td><input value="<%=vendor%>" type="text" name="vendor" value=""
                               size="20"/></td>
                </tr>
                <tr>
                    <td>Qty</td>
                    <td><input value="<%=qty%>" type="text" name="qty" value="" size="5"
                               placeholder="64"/></td>
                </tr>
                <tr>
                    <td>Shipped</td>
                    <td>after <input value="<%=shipped_after%>" type="text" name="shipped_after" size="8"
                                     placeholder="Feb 01, 2001"/> before
                        <input value="<%=shipped_before%>" type="text" name="shipped_before" size="8"
                               placeholder="Feb 01, 2001"/>
                    </td>
                </tr>
                <tr>
                    <td>Received</td>
                    <td>after <input value="<%=received_after%>" type="text" name="received_after" size="8"
                                     placeholder="Feb 01, 2001"/> before
                        <input value="<%=received_before%>" type="text" name="received_before" size="8"
                               placeholder="Feb 01, 2001"/>
                    </td>
                </tr>
            </table>
            <button onclick="" class="button">Filter</button>
        </form>
    </div>

    <table id="keywords" cellspacing="0" cellpadding="0">
        <thead>
        <tr>
            <th><span>Number (PN)</span></th>
            <th><span>Name</span></th>
            <th><span>Vendor</span></th>
            <th><span>Qty</span></th>
            <th><span>Shipped</span></th>
            <th><span>Received</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${parts}" var="part">
            <tr>
                <td><c:out value="${part.number}"/></td>
                <td><c:out value="${part.name}"/></td>
                <td><c:out value="${part.vendor}"/></td>
                <td><c:out value="${part.qty}"/></td>
                <td><c:out value="${part.shipped}"/></td>
                <td><c:out value="${part.receive}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


