<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample Application JSP Page</title>
</head>
<body>
    <p>
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "No message available." %>
    </p>
</body>
</html>