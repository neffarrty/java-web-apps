<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Hospital departments</title>
        <style>
            table {
                width: calc(100% - 1px);
            }
            table tr th,
            table tr td  {
                outline: #e5e7eb solid 1px;
            }
        </style>
        <link rel="icon" type="image/svg+xml" href="../favicon.svg" />
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="flex flex-col h-screen">
        <%@include file="/header.jsp" %>
        <section class="mx-6 mt-4 flex justify-between h-8">
            <a
                class="bg-slate-400 text-white w-fit rounded p-1 hover:bg-slate-500"
                href="${pageContext.request.contextPath}/hospital/create"
            >
                CREATE HOSPITAL DEPARTMENT
            </a>
            <form
                action="${pageContext.request.contextPath}/hospital/list"
                method="GET"
                onreset="clearSearch(this)"
                onsubmit="cleanForm(this)"
                class="inline *:pr-1"
            >
                <label>
                    <span>Sort by</span>
                    <select name="sort" class="border-b py-1 focus:outline-none">
                        <option value="">-</option>
                        <option
                            value="floor"
                            <c:if test="${param.sort == 'floor'}">selected</c:if>
                        >
                            Floor
                        </option>
                        <option
                            value="box-count"
                            <c:if test="${param.sort == 'box-count'}">selected</c:if>
                        >
                            Box count
                        </option>
                    </select>
                </label>
                <label class="mr-4">
                    <span>DESC</span>
                    <input
                        type="checkbox"
                        name="desc"
                        <c:if test="${param.desc == 'on'}">checked</c:if>
                    >
                </label>
                <label>
                    <input
                        type="text"
                        name="name"
                        value="${param.name}"
                        placeholder="Name"
                        class="border-b py-1 focus:outline-none"
                    >
                </label>
                <label>
                    <input
                        type="text"
                        name="shortname"
                        value="${param.shortname}"
                        placeholder="Short name"
                        class="border-b py-1 focus:outline-none"
                    >
                </label>
                <label>
                    <select name="building" class="border-b py-1 focus:outline-none">
                        <option value="">Code building</option>
                        <c:forEach var="code" items="${requestScope.codes}">
                            <option
                                value="${code}"
                                <c:if test="${code == param.building}">selected</c:if>
                            >
                                <c:out value="${code}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <input
                    type="submit"
                    value="Search"
                    class="bg-slate-400 rounded text-white p-1 hover:bg-slate-500"
                >
                <input
                    type="reset"
                    value="Clear"
                    onclick=""
                    class="bg-slate-400 rounded text-white p-1 hover:bg-slate-500"
                >
            </form>
        </section>
        <main class="m-6 flex flex-col flex-grow overflow-y-auto">
            <table class="w-full border-collapse">
                <tr class="border *:border *:p-2 sticky top-0 bg-white z-10">
                    <th scope="col">Name</th>
                    <th scope="col">Short name</th>
                    <th scope="col">Code building</th>
                    <th scope="col">Floor</th>
                    <th scope="col">Box count</th>
                    <th scope="col" colspan="2">Actions</th>
                </tr>
                <c:forEach var="e" items="${requestScope.hospitals}">
                    <tr class="hover:bg-slate-200 *:border *:p-2">
                        <td>
                            <c:out value="${e.getName()}"/>
                        </td>
                        <td>
                            <c:out value="${e.getShortName()}"/>
                        </td>
                        <td>
                            <c:out value="${e.getCodeBuilding()}"/>
                        </td>
                        <td>
                            <c:out value="${e.getFloor()}"/>
                        </td>
                        <td>
                            <c:out value="${e.getBoxCount()}"/>
                        </td>
                        <td class="text-center">
                            <form
                                action="${pageContext.request.contextPath}/hospital/update"
                                method="GET"
                                class="inline"
                            >
                                <input
                                    type="hidden"
                                    name="id"
                                    value="${e.getId()}"
                                >
                                <input
                                    type="hidden"
                                    name="name"
                                    value="${e.getName()}"
                                >
                                <input
                                    type="hidden"
                                    name="shortname"
                                    value="${e.getShortName()}"
                                >
                                <input
                                    type="hidden"
                                    name="building"
                                    value="${e.getCodeBuilding()}"
                                >
                                <input
                                    type="hidden"
                                    name="floor"
                                    value="${e.getFloor()}"
                                >
                                <input
                                    type="hidden"
                                    name="count"
                                    value="${e.getBoxCount()}"
                                >
                                <input
                                    type="submit"
                                    class="bg-green-500 text-white rounded p-1 w-full hover:bg-green-600"
                                    value="UPDATE"
                                >
                            </form>
                        </td>
                        <td class="text-center">
                            <form
                                action="${pageContext.request.contextPath}/hospital/delete"
                                method="POST"
                                class="inline delete-form"
                            >
                                <input
                                    type="hidden"
                                    name="id"
                                    value="${e.getId()}"
                                >
                                <input
                                    type="submit"
                                    class="bg-red-500 text-white rounded p-1 w-full hover:bg-red-600"
                                    onclick="return confirm('Confirm deleting ${e.getName()}')"
                                    value="DELETE"
                                >
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </main>
        <%@include file="/footer.jsp" %>
        <script>
            const cleanForm = (form) => {
                const inputs = form.querySelectorAll('input, select');
                inputs.forEach((input) => {
                    input.name = input.value ? input.name : '';
                });
            }

            const clearSearch = (form) => {
                form.reset();

                const inputs = form.querySelectorAll('input, select');
                inputs.forEach((input) => {
                    input.name = '';
                });

                form.submit();
            }
        </script>
    </body>
</html>






