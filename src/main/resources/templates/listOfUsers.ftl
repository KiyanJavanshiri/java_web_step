<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Choice</title>

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

        .choice-card {
            width: 360px;
            background: #ffffff;
            border-radius: 18px;
            padding: 28px 26px 30px;
            box-shadow: 0 20px 40px rgba(0, 70, 180, 0.15);
            text-align: center;
        }

        .avatar {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #dbe7ff;
            box-shadow: 0 8px 20px rgba(37, 99, 235, 0.25);
            margin-bottom: 16px;
        }

        .username {
            font-size: 20px;
            font-weight: 600;
            color: #1e3a8a;
            margin-bottom: 24px;
        }

        .actions {
            display: flex;
            justify-content: center;
            gap: 16px;
        }

        .action-link {
            flex: 1;
            padding: 12px 0;
            border-radius: 14px;
            text-decoration: none;
            font-size: 15px;
            font-weight: 500;
            color: #ffffff;
            transition: transform 0.15s, box-shadow 0.15s;
        }

        .yes {
            background: linear-gradient(135deg, #2563eb, #3b82f6);
        }

        .no {
            background: linear-gradient(135deg, #94a3b8, #64748b);
        }

        .action-link:hover {
            transform: translateY(-1px);
            box-shadow: 0 10px 20px rgba(37, 99, 235, 0.35);
        }

        .back-btn {
            position: absolute;
            top: 18px;
            left: 18px;
            padding: 6px 14px;
            border-radius: 10px;
            font-size: 13px;
            text-decoration: none;
            border: 1px solid #c7d7ff;
            color: #2563eb;
            background: transparent;
            transition: background 0.2s, color 0.2s;
        }

        .back-btn:hover {
            background: #2563eb;
            color: #ffffff;
        }
    </style>
</head>
<body>

<div class="choice-card">

    <a href="${request.contextPath}/profile" class="back-btn">
        ← Profile
    </a>

    <img
        src="${avatar}"
        alt="Avatar"
        class="avatar"
    />

    <div class="username">
        ${username}
    </div>

    <form method="post" action="${request.contextPath}/users" class="actions">
            <input type="hidden" name="userId" value="${id}" />

            <button class="action-link yes" type="submit" name="action" value="yes">
                Yes
            </button>

            <button class="action-link no" type="submit" name="action" value="no">
                No
            </button>
    </form>

</div>

</body>
</html>
