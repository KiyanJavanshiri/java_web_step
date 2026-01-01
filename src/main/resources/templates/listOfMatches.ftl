<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liked Users</title>
    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #e6f0ff, #f9fbff);
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .back-btn {
            text-decoration: none;
            color: #1e3a8a;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .user-list {
            display: flex;
            flex-direction: column;
            gap: 16px;
            width: 100%;
            max-width: 400px;
        }

        .user-card {
            display: flex;
            align-items: center;
            gap: 16px;
            padding: 12px 16px;
            border-radius: 14px;
            background: #ffffff;
            box-shadow: 0 8px 20px rgba(37, 99, 235, 0.15);
            text-decoration: none;
            color: #1e3a8a;
            transition: transform 0.15s, box-shadow 0.15s;
        }

        .user-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 24px rgba(37, 99, 235, 0.25);
        }

        .avatar {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #dbe7ff;
        }

        .username-text {
            font-size: 16px;
            font-weight: 600;
        }

        .no-users {
            font-size: 16px;
            color: #64748b;
        }
    </style>
</head>
<body>

<a href="${request.contextPath}/users" class="back-btn">← Back to users</a>

<#if isEmpty>
    <p class="no-users">No liked users yet</p>
<#else>
    <div class="user-list">
        <#list likedUsers as matchId, user>
            <a href="${request.contextPath}/messages/${matchId}" class="user-card">
                <img src="${user.avatar_url}" alt="Avatar" class="avatar"/>
                <div class="username-text">Start chat with ${user.username}</div>
            </a>
        </#list>
    </div>
</#if>

</body>
</html>
