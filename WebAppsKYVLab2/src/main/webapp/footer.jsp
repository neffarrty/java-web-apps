<%@ page import="java.time.LocalDateTime" %>
<footer class="bg-slate-300 flex items-center justify-center justify-between py-4 px-6">
    <img
        class="max-w-6"
        src="https://img.icons8.com/?size=100&id=2572&format=png&color=000000"
        alt="java-icon"
    >
    <div>&copy; Yehor Kovtun, <%= LocalDateTime.now().getYear()%></div>
    <div>SEMIT, KhPI</div>
</footer>
