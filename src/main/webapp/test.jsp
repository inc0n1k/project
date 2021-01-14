<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>

<script>
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/Project-1.0/test');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    xhr.send('id=1');
    xhr.responseType = 'json';
    xhr.onload = () => {
        let testClass = xhr.response;
    };
</script>
</body>
</html>
