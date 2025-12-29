<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            margin: 0;
            height: 100vh;
            background: linear-gradient(135deg, #e6f0ff, #f9fbff);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-card {
            width: 360px;
            background: #ffffff;
            border-radius: 16px;
            padding: 32px;
            box-shadow: 0 20px 40px rgba(0, 70, 180, 0.15);
        }

        .login-card h2 {
            margin-bottom: 24px;
            text-align: center;
            color: #1e3a8a;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #3b4c7a;
        }

        .form-group input {
            width: 100%;
            padding: 12px 14px;
            border-radius: 10px;
            border: 1px solid #c7d7ff;
            outline: none;
            font-size: 14px;
            transition: border 0.2s, box-shadow 0.2s;
        }

        .form-group input:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
        }

        .submit-btn {
            width: 100%;
            margin-top: 12px;
            padding: 12px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(135deg, #2563eb, #3b82f6);
            color: #ffffff;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.15s, box-shadow 0.15s;
        }

        .submit-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 10px 20px rgba(37, 99, 235, 0.35);
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
            padding: 10px 12px;
            border-radius: 10px;
            font-size: 13px;
            margin-bottom: 16px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="login-card">
    <h2>Sign In</h2>

    <#if error??>
        <div class="error">${error}</div>
    </#if>

    <form method="post" action="${request.contextPath}/login">
        <div class="form-group">
            <label for="login">Login</label>
            <input
                type="text"
                id="login"
                name="login"
                placeholder="Enter your login"
                required
            />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input
                type="text"
                id="password"
                name="password"
                placeholder="Enter your password"
                required
            />
        </div>

        <button class="submit-btn" type="submit">
            Log In
        </button>
    </form>
</div>

</body>
</html>