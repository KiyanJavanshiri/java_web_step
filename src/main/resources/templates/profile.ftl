<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: linear-gradient(135deg, #e6f0ff, #f9fbff);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .profile-card {
            width: 380px;
            background: #ffffff;
            border-radius: 18px;
            padding: 28px 26px 32px;
            box-shadow: 0 20px 40px rgba(0, 70, 180, 0.15);
            position: relative;
            text-align: center;
        }

        .logout-btn {
            position: absolute;
            top: 18px;
            right: 18px;
            background: transparent;
            border: 1px solid #c7d7ff;
            color: #2563eb;
            padding: 6px 12px;
            border-radius: 10px;
            font-size: 13px;
            text-decoration: none;
            cursor: pointer;
            transition: background 0.2s, color 0.2s;
        }

        .logout-btn:hover {
            background: #2563eb;
            color: #ffffff;
        }

        .avatar-wrapper {
            margin-top: 16px;
            margin-bottom: 14px;
            display: flex;
            justify-content: center;
        }

        .avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #dbe7ff;
            box-shadow: 0 8px 20px rgba(37, 99, 235, 0.25);
        }

        .username {
            font-size: 20px;
            font-weight: 600;
            color: #1e3a8a;
            margin-bottom: 24px;
        }

        .actions {
            display: flex;
            gap: 14px;
            justify-content: center;
        }

        .action-btn {
            text-decoration: none;
            flex: 1;
            padding: 12px 0;
            border-radius: 14px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            background: linear-gradient(135deg, #2563eb, #3b82f6);
            color: #ffffff;
            transition: transform 0.15s, box-shadow 0.15s;
        }

        .action-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 10px 20px rgba(37, 99, 235, 0.35);
        }
    </style>
</head>
<body>

<div class="profile-card">

    <a href="${request.contextPath}/logout" class="logout-btn">Logout</a>

    <div class="avatar-wrapper">
        <img
            src="${image_url}"
            alt="Avatar"
            class="avatar"
        />
    </div>

    <div class="username">
        Welcome, ${username}!
    </div>

    <div class="actions">
        <a href="${request.contextPath}/users" class="action-btn">users</a>
        <a href="${request.contextPath}/liked" class="action-btn">liked</a>
    </div>
</div>

</body>
</html>
