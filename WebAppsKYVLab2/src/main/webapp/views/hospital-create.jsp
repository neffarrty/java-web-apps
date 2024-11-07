<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Create hospital department</title>
        <link rel="icon" type="image/svg+xml" href="../favicon.svg" />
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="h-screen flex flex-col">
        <%@include file="/header.jsp" %>
        <main class="m-6 grid place-items-center flex-grow">
            <form
                action="${pageContext.request.contextPath}/hospital/create"
                method="POST"
                class="flex flex-col p-4 rounded-md border min-w-96 [&>:not(:last-child)]:mb-1"
            >
                <h1 class="text-2xl font-bold mb-1">Add new hospital department</h1>
                <label>
                    <span>Name</span>
                    <br>
                    <input
                        type="text"
                        name="name"
                        value="${param.name}"
                        class="w-full border-b focus:outline-none py-2"
                    >
                </label>
                <div class="text-red-600 text-xs">
                    <c:out value="${requestScope['name-error']}"/>
                </div>
                <label>
                    <span>Short name</span>
                    <br>
                    <input
                        type="text"
                        name="shortname"
                        value="${param.shortname}"
                        class="w-full border-b focus:outline-none py-2"
                    >
                </label>
                <div class="text-red-600 text-xs">
                    <c:out value="${requestScope['shortName-error']}"/>
                </div>
                <label>
                    <span>Code building</span>
                    <br>
                    <input
                        type="text"
                        name="building"
                        value="${param.building}"
                        class="w-full border-b focus:outline-none py-2"
                    >
                </label>
                <div class="text-red-600 text-xs">
                    <c:out value="${requestScope['codeBuilding-error']}"/>
                </div>
                <label>
                    <span>Floor</span>
                    <br>
                    <input
                        type="number"
                        name="floor"
                        value="${param.floor}"
                        class="w-full border-b focus:outline-none py-2"
                    >
                </label>
                <div class="text-red-600 text-xs">
                    <c:out value="${requestScope['floor-error']}"/>
                </div>
                <label>
                    <span>Box count</span>
                    <br>
                    <input
                        type="number"
                        name="count"
                        value="${param.count}"
                        class="w-full border-b focus:outline-none py-2"
                    >
                </label>
                <div class="text-red-600 text-xs">
                    <c:out value="${requestScope['boxCount-error']}"/>
                </div>
                <input
                    type="submit"
                    value="Create"
                    class="w-full bg-slate-300 color-white rounded hover:bg-slate-400"
                />
                <div class="text-red-600 text-xs"><c:out value="${requestScope.error}"/></div>
                <a href="${pageContext.request.contextPath}/hospital/list">Back</a>
            </form>
        </main>
        <%@include file="/footer.jsp" %>
    </body>
</html>
