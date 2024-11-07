<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login</title>
        <link rel="icon" type="image/svg+xml" href="../favicon.svg" />
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="h-full flex flex-col items-center justify-center">
        <form
            action="login"
            method="POST"
            class="flex flex-col p-4 rounded-md border w-2/7 [&>:not(:last-child)]:mb-1"
        >
            <h1 class="text-2xl font-bold mb-1 text-center">Login in system</h1>
            <label>
                <span>Username</span>
                <br>
                <input
                    type="text"
                    name="username"
                    class="w-full border-b focus:outline-none py-1"
                    required
                >
            </label>
            <br>
            <label>
                <span>Password</span>
                <br>
                <input
                    type="password"
                    name="password"
                    class="w-full border-b focus:outline-none py-1"
                    required
                >
            </label>
            <br>
            <input
                type="submit"
                value="Login"
                class="w-full bg-slate-300 color-white rounded hover:bg-slate-400"
            >
            <div style="color: crimson"><c:out value="${requestScope.error}"/></div>
        </form>
    </body>
</html>


